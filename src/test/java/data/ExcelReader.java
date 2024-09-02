package data;

import Pages.PageBase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class ExcelReader {
    private static Logger log = Logger.getLogger(PageBase.class.getName());
  //  static FileInputStream fis = null;


    public static Object[][] getDataFormExcel_1(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = 4;

        String[][] arrayExcelData = new String[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {

            for (int j = 0; j < colCount; j++) {
                Row row = sheet.getRow(i);
                arrayExcelData[i][j] = row.getCell(j).toString();
            }
        }
        workbook.close();
        return arrayExcelData;
    }

    //  ( 3 )another function

    public static Object[][] getDataFromExcel_3(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i][j] = cell.toString();
            }
        }
        workbook.close();
        file.close();
        return data;
    }
}






