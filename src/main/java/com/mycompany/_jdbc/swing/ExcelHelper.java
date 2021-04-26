/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
@SuppressWarnings("unchecked")
public class ExcelHelper {

    /**
     *
     * @param students
     * @param excelFilePath
     * @return
     */
    public boolean writeExcel(Vector<Student> students, String excelFilePath) throws IOException, IOException {
        // Create Workbook
        Workbook workbook;
        try {
            workbook = getWorkbook(excelFilePath);

            // Create sheet
            Sheet sheet = workbook.createSheet("Books"); // Create sheet with sheet name

            int rowIndex = 0;

            // Write header
            writeHeader(sheet, rowIndex);

            // Write data
            rowIndex++;
            for (Student student : students) {
                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBook(student, row);
                rowIndex++;
            }

            createOutputFile(workbook, excelFilePath);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private Workbook getWorkbook(String excelFilePath) {
        Workbook workbook = null;
        try {
            if (excelFilePath.endsWith("xlsx")) {
                workbook = new XSSFWorkbook();
            } else if (excelFilePath.endsWith("xls")) {
                workbook = new HSSFWorkbook();
            } else {
                throw new IllegalArgumentException("The specified file is not Excel file");
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
        return workbook;
    }

    private Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    private void writeHeader(Sheet sheet, int rowIndex) {
        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(0);
        cell.setCellValue("Ma hoc sinh");

        cell = row.createCell(1);
        cell.setCellValue("Ten");

        cell = row.createCell(2);
        cell.setCellValue("Diem");

        cell = row.createCell(3);
        cell.setCellValue("Anh");

        cell = row.createCell(4);
        cell.setCellValue("Dia chi");

        cell = row.createCell(5);
        cell.setCellValue("Ghi chu");
    }

    private void writeBook(Student student, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(student.mhs);
        cell = row.createCell(1);
        cell.setCellValue(student.name);
        cell = row.createCell(2);
        cell.setCellValue(student.score);
        cell = row.createCell(3);
        cell.setCellValue(student.avatar);
        cell = row.createCell(4);
        cell.setCellValue(student.address);
        cell = row.createCell(5);
        cell.setCellValue(student.note);

    }

    private void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }

    private Object getCellValue(Cell cell) {
        var cellType = cell.getCellTypeEnum();
        Object cellValue;
        cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }

    public Vector<Student> readExcel(String excelFilePath) {
        Vector<Student> listStudent = new Vector<Student>();
        try {
            // Get workbook
            try ( // Get file
                    InputStream inputStream = new FileInputStream(new File(excelFilePath)); // Get workbook
                    org.apache.poi.ss.usermodel.Workbook workbook = getWorkbook(inputStream, excelFilePath)) {

                // Get sheet
                Sheet sheet = workbook.getSheetAt(0);

                // Get all rows
                Iterator<Row> iterator = sheet.iterator();

                while (iterator.hasNext()) {
                    Row nextRow = iterator.next();
                    if (nextRow.getRowNum() == 0) {
                        // Ignore header
                        continue;
                    }
                    Student student = new Student();

                    // Get all cells
                    Iterator<Cell> cellIterator = nextRow.cellIterator();
                    // Read cells and set value for book object
                    while (cellIterator.hasNext()) {
                        //Read cell
                        Cell cell = cellIterator.next();
                        Object cellValue;
                        cellValue = getCellValue(cell);
                        if (cellValue == null || cellValue.toString().isEmpty()) {
                            continue;
                        }

                        // Set value for book object
                        int columnIndex = cell.getColumnIndex();
                        switch (columnIndex) {
                            case 0:
                                student.setMHS(new BigDecimal((double) cellValue).intValue());
                                break;
                            case 1:
                                student.setName((String) getCellValue(cell));
                                break;
                            case 2:
                                student.setScore((Double) getCellValue(cell));
                                break;
                            case 3:
                                student.setAvatar((String) getCellValue(cell));
                                break;
                            case 4:
                                student.setAddress((String) getCellValue(cell));
                                break;
                            case 5:
                                student.setNote((String) getCellValue(cell));
                                break;
                            default:
                                break;
                        }

                    }
                    listStudent.add(student);
                }

            }
        } catch (IOException | NumberFormatException e) {
        }
        return listStudent;
    }
}
