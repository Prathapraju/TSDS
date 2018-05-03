package tea.ivv.tsds.TSDSDataLoad;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.DataProvider;

import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDataPage {
	public static final String baseDir = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
    private static final String Path_TestCase = baseDir + "\\resources\\testcases\\";
	private static XSSFSheet nifasheet;
	static Object[][] excelData;
	
	@DataProvider(name="UIDData")
	public static Object[][] UIDData() throws Exception {
		return loadData(Path_TestCase + "UIDTestCases.xlsx", "Sheet1");
	}

	public static Object[][] loadData(String sheetPath, String sheetName) throws Exception {
		nifasheet = ExcelUtils.setExcelFile(sheetPath, sheetName);
		int rows = ExcelUtils.rowCount(nifasheet);
		int cols = ExcelUtils.columnCount(nifasheet);
		excelData = new Object[rows -1][cols];
		for(int i = 1; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				excelData[i-1][j] = ExcelUtils.getCellData(i, j);
			}
		}
		return excelData;
	}

}
