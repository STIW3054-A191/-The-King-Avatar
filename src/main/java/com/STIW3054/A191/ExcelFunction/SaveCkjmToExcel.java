package com.STIW3054.A191.ExcelFunction;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;

public class SaveCkjmToExcel implements ExcelFunctionData {

    public static void addData(String MatricNo, ArrayList<String> UnknownMatricNo, int WMC, int DIT, int NOC, int CBO, int RFC, int LCOM) {

        try {
            FileInputStream file = new FileInputStream(fileName);

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //ReadSheets
            XSSFSheet sheet = workbook.getSheet(sheetName);

            boolean foundInList = false;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                if (sheet.getRow(i).getCell(1).toString().equals(MatricNo)) {

                    foundInList = true;

                    //Insert data to selected Excel column
                    Row row = sheet.getRow(i);
                    row.createCell(3).setCellValue(WMC);
                    row.createCell(4).setCellValue(DIT);
                    row.createCell(5).setCellValue(NOC);
                    row.createCell(6).setCellValue(CBO);
                    row.createCell(7).setCellValue(RFC);
                    row.createCell(8).setCellValue(LCOM);
                    break;
                }
            }

            if (!foundInList) {
                UnknownMatricNo.add(MatricNo);
            }

            //Save the Excel file
            OutputExcel.output(workbook, sheet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
