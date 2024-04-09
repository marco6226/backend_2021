/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.reports.sge;

import co.sigess.util.Util;
import co.sigess.util.UtilStrings;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author fmoreno
 */
public class GeneradorReportesHTML {

    public static File generar(Map<String, Object> params, Map<String, ReportDataSource> datasources, File template, String nombre) throws FileNotFoundException, IOException, InvalidFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {

        StringBuilder strHtml = new StringBuilder(new String(Files.readAllBytes(Paths.get(template.getAbsolutePath()))));

        for (String key : params.keySet()) {
            UtilStrings.replaceAll(strHtml, key.replace("{", "\\{").replace("}", "\\}"), params.get(key) == null ? "" : params.get(key).toString());
        }
        Pattern pattern = Pattern.compile("G\\{(.*)\\}");
        Matcher m = pattern.matcher(strHtml);
        int start = 0;
        while (m.find(start)) {
            String jsonDS = strHtml.substring(m.start() + 1, m.end());
            ChartModel chartModel = ChartModel.fromJson(jsonDS);
            String strSVG = createChart(chartModel, datasources.get(chartModel.getDatasource())).replaceAll("clip-path=\"url\\(#", "clip-path=\"url(");
            strHtml.replace(m.start(), m.end(), strSVG);
            start = m.start() + strSVG.length();
        }

        File reporte = new File(Util.TMP_DIR + nombre + ".html");
        reporte.createNewFile();
        Document doc = Jsoup.parse(strHtml.toString());

        for (String dsName : datasources.keySet()) {
            Elements elementos = doc.getElementsByClass(dsName);
            ReportDataSource dataSource = datasources.get(dsName);

            for (Element tblElem : elementos) {
                Element elementModelo = tblElem.getElementsByTag("tr").get(1);
                String filaModelo = elementModelo.html();

                Pattern fieldPattern = Pattern.compile("FM\\{(.*)\\}");
                Matcher fieldMatcher = fieldPattern.matcher(filaModelo);
                int inicio = 0;
                Map<String, FieldModel> fieldMap = new HashMap<>();
                while (fieldMatcher.find(inicio)) {
                    String jsonField = filaModelo.substring(fieldMatcher.start() + 2, fieldMatcher.end());
                    FieldModel fieldModel = FieldModel.fromJson(jsonField);
                    fieldModel.setStringModel("FM" + jsonField);
                    fieldMap.put(fieldModel.getKey(), fieldModel);
                    inicio = fieldMatcher.start() + jsonField.length();
                }

                for (int i = 0; i < dataSource.getValues().length; i++) {
                    StringBuilder nuevaFila = new StringBuilder(filaModelo);
                    Object[] fila = (Object[]) dataSource.getValues()[i];
                    for (int j = 0; j < fila.length; j++) {
                        Object rawValor = fila[j];
                        String fieldKey = dataSource.getHeaders()[j];
                        FieldModel fieldModel = fieldMap.get(fieldKey);
                        String valor = fieldModel.format(rawValor);
                        UtilStrings.replaceAll(nuevaFila, fieldModel.getStringModel().replace("{", "\\{").replace("}", "\\}"), valor == null ? "" : valor);
                    }
                    tblElem.append(nuevaFila.toString());
                }
                elementModelo.remove();
            }

        }

        Files.write(Paths.get(reporte.getAbsolutePath()), doc.outerHtml().getBytes(), StandardOpenOption.WRITE);
        return reporte;
    }

    private static String createChart(ChartModel chartModel, ReportDataSource ds) throws IOException, InvalidFormatException {

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
        return g2.getSVGElement();
    }

}
