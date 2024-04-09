/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.reports.sge;

import co.sigess.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author fmoreno
 */
public class GeneradorPlanTrabajoXSLX {

    public static File generar(Map<String, Object> params, Map<String, ReportDataSource> datasources, File template, String nombre) throws FileNotFoundException, IOException, InvalidFormatException {

        File reporte = new File(Util.TMP_DIR + nombre + ".xlsx");

        FileInputStream fis = new FileInputStream(template);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        int fieldRowIndex = 11, fieldCellIndex = 5;

        Row modelRow = sheet.getRow(fieldRowIndex);
        ReportDataSource repDS = datasources.get("DS{planTrabajoDS}");
        for (int indexDSRow = 0; indexDSRow < repDS.getValues().length; indexDSRow++) {
            fieldRowIndex += 2;
            Row newRow = sheet.getRow(fieldRowIndex) == null ? sheet.createRow(fieldRowIndex) : sheet.getRow(fieldRowIndex);
            for (int cellIndex = 0; cellIndex <= fieldCellIndex; cellIndex++) {
                Cell cellModel = modelRow.getCell(cellIndex);
                Object fielObjValue = repDS.getValue(indexDSRow, cellModel.getStringCellValue());
                String fieldValue = fielObjValue == null ? "" : fielObjValue.toString();
                Cell newCell = newRow.createCell(cellIndex);
                newCell.setCellValue(fieldValue);
                CellStyle newCellStyle = workbook.createCellStyle();
                newCellStyle.cloneStyleFrom(cellModel.getCellStyle());
                newCell.setCellStyle(newCellStyle);
                
                CellRangeAddress cra = new CellRangeAddress(fieldRowIndex, fieldRowIndex + 1, cellModel.getColumnIndex(), cellModel.getColumnIndex());
                sheet.addMergedRegion(cra);
            }
        }
        reporte.createNewFile();
        workbook.write(new FileOutputStream(reporte));

        return reporte;
    }

//    private static void copyRow(Workbook workbook, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
//        // Get the source / new row
//        Row newRow = worksheet.getRow(destinationRowNum);
//        Row sourceRow = worksheet.getRow(sourceRowNum);
//
//        // If the row exist in destination, push down all rows by 1 else create a new row
//        if (newRow != null) {
//            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
//        } else {
//            newRow = worksheet.createRow(destinationRowNum);
//        }
//
//        // Loop through source columns to add to new row
//        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
//            // Grab a copy of the old/new cell
//            Cell oldCell = sourceRow.getCell(i);
//            Cell newCell = newRow.createCell(i);
//
//            // If the old cell is null jump to next cell
//            if (oldCell == null) {
//                newCell = null;
//                continue;
//            }
//
//            // Copy style from old cell and apply to new cell
//            CellStyle newCellStyle = workbook.createCellStyle();
//            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
//
//            newCell.setCellStyle(newCellStyle);
//
//            // If there is a cell comment, copy
//            if (oldCell.getCellComment() != null) {
//                newCell.setCellComment(oldCell.getCellComment());
//            }
//
//            // If there is a cell hyperlink, copy
//            if (oldCell.getHyperlink() != null) {
//                newCell.setHyperlink(oldCell.getHyperlink());
//            }
//
//            // Set the cell data type
//            newCell.setCellType(oldCell.getCellType());
//
//            // Set the cell data value
//            switch (oldCell.getCellType()) {
//                case Cell.CELL_TYPE_BLANK:
//                    newCell.setCellValue(oldCell.getStringCellValue());
//                    break;
//                case Cell.CELL_TYPE_BOOLEAN:
//                    newCell.setCellValue(oldCell.getBooleanCellValue());
//                    break;
//                case Cell.CELL_TYPE_ERROR:
//                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
//                    break;
//                case Cell.CELL_TYPE_FORMULA:
//                    newCell.setCellFormula(oldCell.getCellFormula());
//                    break;
//                case Cell.CELL_TYPE_NUMERIC:
//                    newCell.setCellValue(oldCell.getNumericCellValue());
//                    break;
//                case Cell.CELL_TYPE_STRING:
//                    newCell.setCellValue(oldCell.getRichStringCellValue());
//                    break;
//            }
//        }

        // If there are are any merged regions in the source row, copy to new row
//        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
//            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
//            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
//                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
//                        (newRow.getRowNum()
//                        + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
//                        cellRangeAddress.getFirstColumn(),
//                        cellRangeAddress.getLastColumn());
//                worksheet.addMergedRegion(newCellRangeAddress);
//            }
//        }
//    }

}
