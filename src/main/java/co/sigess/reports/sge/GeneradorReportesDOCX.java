/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.reports.sge;

import org.apache.poi.util.Units;
import co.sigess.util.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

/**
 *
 * @author fmoreno
 */
public class GeneradorReportesDOCX {

    public static File generar(Map<String, Object> params, Map<String, ReportDataSource> datasources, File template, String nombre) throws FileNotFoundException, IOException, InvalidFormatException {

        File reporte = new File(Util.TMP_DIR + nombre + ".docx");

        FileInputStream fis = new FileInputStream(template);
        XWPFDocument doc = new XWPFDocument(fis);
        for (XWPFParagraph p : doc.getParagraphs()) {
            String text = p.getText();
            List<XWPFRun> runs = p.getRuns();
            for (int indexRun = 0; indexRun < runs.size(); indexRun++) {
                XWPFRun currentRun = runs.get(indexRun);
                if (text.contains("G{")) {
                    createChart(p, text, datasources);
                    break;
                }

                for (String keyParam : params.keySet()) {
                    if (text != null && text.contains(keyParam)) {
                        text = text.replace(keyParam, params.get(keyParam) == null ? "" : params.get(keyParam).toString());
                        currentRun.setText(text, 0);
                    }
                }
            }
        }

        Map<String, XWPFTable> tblMap = new HashMap<>();

        FOR_TBL:
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        String text = p.getText().trim();

                        for (String key : datasources.keySet()) {
                            if (text != null && text.contains(key)) {
                                tblMap.put(key, tbl);
                                continue FOR_TBL;
                            }
                        }

                        for (XWPFRun r : p.getRuns()) {
                            for (String keyParam : params.keySet()) {
                                if (text != null && text.contains(keyParam)) {
                                    text = text.replace(keyParam, params.get(keyParam) == null ? "" : params.get(keyParam).toString());
                                    r.setText(text, 0);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        for (String key : tblMap.keySet()) {
            generateRows(tblMap.get(key), datasources.get(key));
        }

        reporte.createNewFile();
        doc.write(new FileOutputStream(reporte));

        return reporte;
    }

    private static void generateRows(XWPFTable tbl, ReportDataSource ds) {
        XWPFTableRow rowRef = tbl.getRow(2);
        for (int i = 0; i < ds.getValues().length; i++) {
            XWPFTableRow row = tbl.createRow();
            int cellIndex = 0;
            for (XWPFTableCell cellRef : rowRef.getTableCells()) {
                String key = extractText(cellRef);
                Object valor = ds.getValue(i, key);

                XWPFParagraph paragraphRef = cellRef.getParagraphs().get(0);
                XWPFRun runRef = paragraphRef.getRuns().get(0);

                XWPFTableCell newCell = row.getCell(cellIndex);
                XWPFParagraph p = newCell.getParagraphs().get(0);
                p.setAlignment(paragraphRef.getAlignment());
                p.setVerticalAlignment(paragraphRef.getVerticalAlignment());

                XWPFRun run = p.createRun();
                run.setText(valor == null ? "" : valor.toString());
                run.setBold(runRef.isBold());
                run.setColor(runRef.getColor());
                run.setFontFamily(runRef.getFontFamily());
                run.setFontSize(runRef.getFontSize());
                run.setTextPosition(runRef.getTextPosition());
                cellIndex++;
            }
        }
        tbl.removeRow(1);
        // El eliminar la fila 1, la que ocupa la posición 2 pasa a ocupar la posición 1
        tbl.removeRow(1);
    }

    private static String extractText(XWPFTableCell cell) {
        for (XWPFParagraph p : cell.getParagraphs()) {
            return p.getText().trim();
        }
        return "";
    }

    private static void createChart(XWPFParagraph paragraph, String json, Map<String, ReportDataSource> datasources) throws IOException, InvalidFormatException {
        while (paragraph.getRuns().size() > 0) {
            paragraph.removeRun(0);
        }

        ChartModel chartModel = ChartModel.fromJson(json.replaceFirst("G", ""));
        ReportDataSource ds = datasources.get(chartModel.getDatasource());

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int i = 0;
        for (Object value : ds.getValues()) {
            Object[] row = (Object[]) value;
            for (String header : ds.getHeaders()) {
                String rowSeriesLabel = chartModel.getRowKey().contains("F{") ? (String) ds.getValue(i, chartModel.getRowKey()) : chartModel.getRowKey();
                String columnSeriesLabel = chartModel.getColumnKey().contains("F{") ? (String) ds.getValue(i, chartModel.getColumnKey()) : chartModel.getColumnKey();
                dataset.addValue((Number) ds.getValue(i, chartModel.getValueKey()), columnSeriesLabel, rowSeriesLabel);
            }
            i++;
        }

        JFreeChart barChart = ChartFactory.createBarChart(chartModel.getTitle(), "", "", dataset);

        CategoryPlot cp = barChart.getCategoryPlot();  // Get the Plot object for a bar graph              

        barChart.getTitle().setPaint(new Color(chartModel.getTitleColor()));
        cp.setBackgroundPaint(new Color(chartModel.getBackgroundPlotColor()));       // Set the plot background colour  
        cp.setRangeGridlinePaint(new Color(chartModel.getRangeGridlineColor()));
        cp.setOutlineVisible(false);
        cp.setOrientation(chartModel.isVerticalOrientation() ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL);

        Random random = new Random(System.currentTimeMillis());
        BarRenderer br = new CustomRenderer(
                new Paint[]{
                    new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))
                }
        );
        br.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        br.setItemLabelsVisible(true);
        br.setItemLabelFont(Font.decode(chartModel.getItemLabelFont()));
        br.setGradientPaintTransformer(null);
        br.setDrawBarOutline(false);
        br.setShadowVisible(false);
        br.setBarPainter(new StandardBarPainter());
        cp.setRenderer(br);

        SVGGraphics2D g2 = new SVGGraphics2D(chartModel.getWidth(), chartModel.getHeight());
        Rectangle r = new Rectangle(0, 0, chartModel.getWidth(), chartModel.getHeight());
        barChart.draw(g2, r);
        File imgChart = new File(Util.TMP_DIR + "barChart.svg");
        SVGUtils.writeToSVG(imgChart, g2.getSVGElement());

        XWPFRun run = paragraph.createRun();
        run.addPicture(new FileInputStream(imgChart), XWPFDocument.PICTURE_TYPE_JPEG, imgChart.getName(), Units.toEMU(chartModel.getWidth() / 2), Units.toEMU(chartModel.getHeight() / 2));

    }

}
