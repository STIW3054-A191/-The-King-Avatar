package com.STIW3054.A191.ExcelFunction;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;

class OutputExcel implements ExcelFunctionData {

    static void output(XSSFWorkbook workbook, XSSFSheet sheet) {
        //Auto resize
        for (int i = 0; i < title.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // An output stream accepts output bytes and sends them to sink.
        try (OutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            // Close fileOut and workbook
            fileOut.close();
            workbook.close();

        } catch (Exception e) {
            System.err.println("Failed to create/save the Excel file !");
            System.exit(-1);
        }

    }
}
