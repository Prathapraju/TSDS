package tea.ivv.tsds.TSDSUniqueID;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.github.yev.FailTestScreenshotListener;
import us.tx.state.tea.seleniumfw.*;
import us.tx.state.tea.seleniumfw.teal.*;
import us.tx.state.tea.seleniumfw.tsdsportal.TSDSPortal;

@Listeners(FailTestScreenshotListener.class)
public class TSDSUniqueIDProdPatch {
	DriverManager driverManager;
	private static WebDriver driver;
	//public static String basePath = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
	public static String basePath = System.getProperty("systembaseDir");
	public static final String Path_FileDownload = basePath + "\\resources\\downloads\\";
	public final String datepattern = "MM/dd/yyyy";
	public static final String Path_FileCheck = basePath + "\\resources\\SourceFiles\\";
	public static final String baseUrl = System.getProperty("systemUrl");
    protected Logger log = Logger.getLogger(this.getClass().getName());
	
	@BeforeClass(alwaysRun = true)
    public void setUp() throws Exception{
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        driver = driverManager.getRemoteDriver(basePath);
    }
   
    @AfterClass(alwaysRun = true)
    public void setupAfterSuite() {
	log.info("End of UniqueID Prod Patch Test");
        driverManager.quitDriver();
    }
	
    @Test(description = "Login to TEAL")
    @Parameters({"userid", "pwd"})
    public void loginTEAL(String userid, String pwd) {
    	TEAL.loginTEAL(driver, baseUrl, userid, pwd);
    }
    
    @Test(description = "Web Services Test")
    public void webServicesTest() {
    	TSDSPortal.webServiceTest(driver);
    }
    
    @Test(description = "Access TSDS")
    public void accessTSDS() {
        TEAL.accessApplicationByHref(driver, "TSDS");
    }
    
    @Test(description = "Select Org")
    @Parameters({"orgid"})
    public void selectOrg(String orgid) {
        TSDSPortal.selectOrg(driver, orgid);
    }
    
	@Test(description = "Launch Unique ID")
	public void launchUniqueID() {
		TSDSPortal.openApplication(driver, "Unique ID", "TSDS: Unique ID");
		TSDSUniqueIDPage.launchManageUniqueID(driver);
	}
	
	@Test(description = "Verify UID Person Search")
	@Parameters({"fname", "lname"})
	public void verifyUIDPersonSearch(String fname, String lname) {
		TSDSManageUniqueIDPage tsdsmanage = new TSDSManageUniqueIDPage(driver);
	    tsdsmanage.searchPerson(fname, lname);
	    Assert.assertTrue(tsdsmanage.personSearchResultCount() >= 2);
	    tsdsmanage.backHome();
	}
	
	@Test(description = "Exit Unique ID")
	public void exitUID() {
		TSDSPortal.exitApplication(driver);
		TSDSUniqueIDPage.exitTSDS(driver);
		TEAL.logout(driver);
		TEAL.accesslogin(driver);
	}

}
