package tea.ivv.tsds.TSDSODS;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.yev.FailTestScreenshotListener;
import us.tx.state.tea.seleniumfw.*;
import us.tx.state.tea.seleniumfw.teal.*;
import us.tx.state.tea.seleniumfw.tsdsportal.*;
import us.tx.state.tea.seleniumfw.utils.*;

@Listeners(FailTestScreenshotListener.class)
public class TSDSODSTests {
	private static WebDriver driver = null;
	//public static String basePath = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
	public static String basePath = System.getProperty("systembaseDir");
	public static final String baseUrl = System.getProperty("systemUrl");
	//public static final String baseUrl = "https://tealtst.tea.state.tx.us";
	public static final String Path_FileDownload = basePath + "\\resources\\downloads\\";
	public final String datepattern = "MM/dd/yyyy";
    protected Logger log = Logger.getLogger(this.getClass().getName());
	String rundate = CreateDate.newDate(datepattern);
	DriverManager driverManager;
	
	
	@BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception{
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        driver = driverManager.getRemoteDriver(basePath);
    }
   
    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
	log.info("End of TSDS ODS testing");
        driverManager.quitDriver();
    }
    
    public void clearCache() throws Exception {
        driver.manage().deleteAllCookies();
    }
    
    	
    @Test(description = "Login to TEAL")
    @Parameters({"loginId", "pwd"})
    public void loginTEAL(String loginId, String pwd) {
        TEAL.loginTEAL(driver, baseUrl, loginId, pwd);
    }
    
    @Test(description = "Access TSDS")
    public void accessTSDS() {
        TEAL.accessApplicationByHref(driver, "TSDS");
    }
    
    @Test(description = "Select Org")
    @Parameters({"org"})
    public void selectOrg(String org) {
        TSDSPortal.selectOrg(driver, org);
        System.out.println("Org Selected is " + org);
    }
    
    @Test(description = "Launch ODS")
    public void launchODS() {
    	TSDSPortal.openApplication(driver, "eDM Data Loads", "TSDS: eDM Data Loads");
    	TSDSODSPage ods = new TSDSODSPage(driver);
    	ods.launchManageDataLoads();
    }
    
    @Test(description = "Delete ODS Data")
    public void deletePEIMSData() throws InterruptedException {
    	TSDSManageDataLoadsPage edm = new TSDSManageDataLoadsPage(driver);
    	edm.deleteUtility();
    	TSDSDeleteUtilityPage.clicknewDeleteRequest(driver);
    	TSDSDeleteUtilityPage.delete2018SUMR1LEA(driver);
    	TSDSDeleteUtilityPage.exitDelete(driver);
    }
    
    @Test(description = "Load ODS Data")
    @Parameters({"collection"})
    public void loadODSData(String collection) throws InterruptedException {
    	TSDSManageDataLoadsPage edm = new TSDSManageDataLoadsPage(driver);
    	edm.deleteFiles();
    	edm.interchangeUpload(collection);
    	edm.uploadFiles(collection);
    	edm.submittoBatch();
    	edm.monitorBatch();
    	Assert.assertTrue(edm.verifyBatch());
    }
    
    @Test(description = "Verify DTU Download Utility")
    public void verifyDTU() throws InterruptedException, ExecutionException {
    	TSDSManageDataLoadsPage edm = new TSDSManageDataLoadsPage(driver);
    	Assert.assertTrue(edm.downloadDTU(), "DTU Link is not working correctly");
    }

}
