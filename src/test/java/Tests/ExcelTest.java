package Tests;
import data.ExcelReader;
import org.testng.annotations.DataProvider;
import java.io.IOException;



public class ExcelTest {
    @DataProvider(name = "excelData")
    public Object[][] testDataFromExcel() throws IOException {
        String Sheet = "Sheet.xlsx";
        return ExcelReader.getDataFormExcel_1(System.getProperty("user.dir") + "/files/" + Sheet, "Sheet1");
    }

   // @Test(dataProvider = "excelData")
    public void testWithExcelData(String... data) {
        for (String cellData : data) {
            System.out.print(cellData + "\t");
        }
        System.out.println();
    }
}





