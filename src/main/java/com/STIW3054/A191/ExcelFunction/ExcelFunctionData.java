package com.STIW3054.A191.ExcelFunction;

import com.STIW3054.A191.OutputFolder.OutputFolderPath;

public interface ExcelFunctionData {

    // Excel File Name
    String fileName = OutputFolderPath.getOutputFolderPath() + "CKJM.xlsx";

    // Excel SheetName
    String sheetName = "ListOfStudents";

    // Excel Header
    String[] title = {"No.", "Matric", "Name", "WMC", "DIT", "NOC", "CBO", "RFC", "LCOM"};

}
