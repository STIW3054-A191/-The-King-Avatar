package com.STIW3054.A191.ExcelFunction;

import java.awt.*;
import java.io.*;

import com.STIW3054.A191.OutputFolder.OutputFolderPath;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;

public class StackedBarChart extends ApplicationFrame implements ExcelFunctionData  {

    private StackedBarChart(String titel) throws DocumentException, IOException {
        super(titel);

        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);

        String pdfFilePath = OutputFolderPath.getOutputFolderPath()+ "BarChart.pdf";
        OutputStream fos = new FileOutputStream(new File(pdfFilePath));
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, fos);

        document.open();

        PdfContentByte pdfContentByte = writer.getDirectContent();
        int width = 750; //width of BarChart
        int height = 500; //height of BarChart
        PdfTemplate pdfTemplate = pdfContentByte.createTemplate(width, height);

        //create graphics
        Graphics2D graphics2d = pdfTemplate.createGraphics(width, height, new DefaultFontMapper());

        //create rectangle
        java.awt.geom.Rectangle2D rectangle2d = new java.awt.geom.Rectangle2D.Double(
                0, 0, width, height);

        chart.draw(graphics2d, rectangle2d);

        graphics2d.dispose();
        pdfContentByte.addTemplate(pdfTemplate, 40, 40); //0, 0 will draw BAR chart on bottom left of page

        document.close();
        System.out.println("PDF created in >> " + pdfFilePath);
    }

    private CategoryDataset createDataset() throws IOException {

        FileInputStream file = new FileInputStream(fileName);
        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet sheet = wb.getSheet(sheetName);

        String[] userName = new String[] {"WMC", "DIT", "NOC", "CBO", "RFC", "LCOM"};
        String[] MatricNo = new String[sheet.getLastRowNum()];
        double[] arrWmc = new double[sheet.getLastRowNum()];
        double[] arrDit = new double[sheet.getLastRowNum()];
        double[] arrNoc = new double[sheet.getLastRowNum()];
        double[] arrCbo = new double[sheet.getLastRowNum()];
        double[] arrRfc = new double[sheet.getLastRowNum()];
        double[] arrLcom = new double[sheet.getLastRowNum()];

        for (int i=1; i <= sheet.getLastRowNum(); i++ ) {
            MatricNo[(i-1)] = sheet.getRow(i).getCell(1).toString();

            String wmc,dit,noc,cbo,rfc,lcom ;
            if(sheet.getRow(i).getCell(3)==null){
                wmc="0";
                dit="0";
                noc="0";
                cbo="0";
                rfc="0";
                lcom="0";
            }else {
                wmc = sheet.getRow(i).getCell(3).toString();
                dit = sheet.getRow(i).getCell(4).toString();
                noc = sheet.getRow(i).getCell(5).toString();
                cbo = sheet.getRow(i).getCell(6).toString();
                rfc = sheet.getRow(i).getCell(7).toString();
                lcom = sheet.getRow(i).getCell(8).toString();
            }

            arrWmc[(i-1)] = Double.parseDouble(wmc);
            arrDit[(i-1)] = Double.parseDouble(dit);
            arrNoc[(i-1)] = Double.parseDouble(noc);
            arrCbo[(i-1)] = Double.parseDouble(cbo);
            arrRfc[(i-1)] = Double.parseDouble(rfc);
            arrLcom[(i-1)] = Double.parseDouble(lcom);
        }

        double[][] data = new double[][]{
                arrWmc,
                arrDit,
                arrNoc,
                arrCbo,
                arrRfc,
                arrLcom
        };

        return DatasetUtilities.createCategoryDataset(userName,MatricNo,data);
    }

    private JFreeChart createChart(final CategoryDataset dataset) {

        final JFreeChart chart = ChartFactory.createStackedBarChart(
                "Stacked Bar Chart ", "", "",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(new Color(249, 231, 236));



        CategoryPlot plot = chart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(128, 0, 0));
        plot.getRenderer().setSeriesPaint(1, new Color(0, 0, 255));

        CategoryAxis categoryAxis = plot.getDomainAxis();
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        return chart;
    }

    public static void create() {
        try {
            new StackedBarChart("Stacked Bar Chart");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}