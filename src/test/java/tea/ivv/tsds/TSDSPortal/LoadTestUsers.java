package tea.ivv.tsds.TSDSPortal;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.yev.FailTestScreenshotListener;

import us.tx.state.tea.seleniumfw.utils.*;
import us.tx.state.tea.seleniumfw.*;
import us.tx.state.tea.seleniumfw.teal.*;
import us.tx.state.tea.seleniumfw.tsdsportal.*;

@Listeners(FailTestScreenshotListener.class)
public class LoadTestUsers {
	DriverManager driverManager;
	private static WebDriver driver = null;
	public static String basePath = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
	public static final String Path_FileDownload = basePath + "\\resources\\";
	public static final String File_TestCase = "CompleteLoadTestUsers.xlsx";
	public final String datepattern = "MM/dd/yyyy";
	public static final String baseUrl = "https://tealtst.tea.state.tx.us";
    protected Logger log = Logger.getLogger(this.getClass().getName());
	String rundate = CreateDate.newDate(datepattern);
	public XSSFSheet loadtestsheet;
	
    public void setUp() throws Exception{
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        driver = driverManager.getRemoteDriver(basePath);
    }
   
    public void setupAfterSuite() {
	log.info("End of TSDS testing");
        driverManager.quitDriver();
    }
    
    public void clearCache() throws Exception {
        driver.manage().deleteAllCookies();
    }
    
    @BeforeSuite(alwaysRun = true)
    public void setexexcelFile() throws Exception {
    	loadtestsheet = ExcelUtils.setExcelFile(Path_FileDownload + File_TestCase, "Sheet1");
    }
    
    @AfterSuite(alwaysRun = true)
    public void savexexcelFile() throws Exception {
    	ExcelUtils.saveWorkbook(Path_FileDownload + File_TestCase);
    }
    
    @Test (description = "Get Load Test User Info")
    public void getLoadTestUsers() throws Exception {
        for (int i=1; i<= 1250; i++) {
        	setUp();
        	String orgid = "";
        	String orgname = "";
        	String userid = "load.test" + String.valueOf(i);
        	try {
        		System.out.print(userid);
        		TEAL.loginTEAL(driver, baseUrl, userid, "UGVyZiEyMzQ1QA==");
        		TEAL.accessApplicationByHref(driver, "TSDS");
        		orgid = TSDSPortal.getSelectedOrgID(driver);
        		orgname = TSDSPortal.getSelectedOrgName(driver);
        		ExcelUtils.setCellData(userid, i, 0);
            	ExcelUtils.setCellData(orgname, i, 1);
            	ExcelUtils.setCellData(orgid, i, 2);
        	} catch (Exception e) {
        	}
        	setupAfterSuite();
        	//clearCache();
        }
        savexexcelFile();
    }
    
    /*
    @Test (description = "Set Access")
    public void setAccess() throws Exception {
    	for (int i = 0; i<21; i++) {
    		setUp();
    		String username = ExcelUtils.getCellData(i, 0);
    		String orgname = ExcelUtils.getCellData(i,  1);
        	String orgid = ExcelUtils.getCellData(i,  2);
        	try {
        	if (orgname.length() == 0) {
            	TEAL.loginTEAL(driver, baseUrl, username, "UGVyZiEyMzQ1QA==");
            	if (TEAL.addModifyAccessByHref(driver, "TSDS")) {
            		TEAL.removeAllAccess(driver);
            	}
            	TEAL.accessMyApplicationAccounts(driver);
            	TEAL.addAccess(driver, "TSDSPortal");
            	TEAL.addOrgId(driver, orgid);
            	TEAL.selectRole(driver, "2");
            	TEAL.enterRoleOrg(driver, "ECDSApprover", orgid);
            	TEAL.selectRole(driver, "8");
            	TEAL.enterRoleOrg(driver, "ODSDataLoad", orgid);
            	TEAL.selectRole(driver, "14");
            	TEAL.enterRoleOrg(driver, "PEIMSDataCompleter", orgid);
            	TEAL.addAccessList(driver);
            	TEAL.saveChanges(driver);
        	}
        	} catch (Exception e) {
        		System.out.println("Error with userid" + username);
        	}
        	setupAfterSuite();
    	}
    }
    */

}
