package com.STIW3054.A191;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;

public class saveExcel {
    public void saveData() {
        try {
            ScrapeData scrapeLinkData = new ScrapeData();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Link");

            //Create Heading
            Row rowHeading = sheet.createRow(0);
            rowHeading.createCell(0).setCellValue("Link                                                               ");

            //1st Row Font Size
            for (int i = 0; i < 1; i++) {
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                font.setFontName(HSSFFont.FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                style.setFont(font);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                rowHeading.getCell(i).setCellStyle(style);
            }
        }

    }
}
