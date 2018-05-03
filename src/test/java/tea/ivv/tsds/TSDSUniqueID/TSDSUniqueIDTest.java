package tea.ivv.tsds.TSDSUniqueID;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.yev.FailTestScreenshotListener;
import tea.ivv.tsds.TSDSSupport.TSDSSupportPage;
import us.tx.state.tea.seleniumfw.*;
import us.tx.state.tea.seleniumfw.teal.*;
import us.tx.state.tea.seleniumfw.tsdsportal.TSDSPortal;

@Listeners(FailTestScreenshotListener.class)
public class TSDSUniqueIDTest {
	DriverManager driverManager;
	private static WebDriver driver;
	//public static String basePath = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
	public static String basePath = System.getProperty("systembaseDir");
	public static final String Path_FileDownload = basePath + "\\resources\\downloads\\";
	public final String datepattern = "MM/dd/yyyy";
	public static final String Path_FileCheck = basePath + "\\resources\\SourceFiles\\";
	public static final String baseUrl = System.getProperty("systemUrl");
    protected Logger log = Logger.getLogger(this.getClass().getName());
    HashMap<String, String> uiddatamap;
    
    @Factory(dataProviderClass = tea.ivv.tsds.TSDSDataLoad.TSDSDataPage.class, dataProvider="UIDData")
    public TSDSUniqueIDTest(String userid, String orgid, String accessfile, String leaid, String campusid, String fname, String lname) {
    	uiddatamap = new HashMap<String, String>();
    	uiddatamap.put("userid", userid);
    	uiddatamap.put("orgid", orgid);
    	uiddatamap.put("accessfile", accessfile);
    	uiddatamap.put("leaid", leaid);
    	uiddatamap.put("campusid", campusid);
    	uiddatamap.put("fname", fname);
    	uiddatamap.put("lname", lname);
    }
	
	@BeforeClass(alwaysRun = true)
    public void setUp() throws Exception{
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        driver = driverManager.getRemoteDriver(basePath);
    }
   
    @AfterClass(alwaysRun = true)
    public void setupAfterSuite() {
	log.info("End of UniqueID Regression testing");
        driverManager.quitDriver();
    }
	
    @Test(description = "Login to TEAL")
    @Parameters({"pwd"})
    public void loginTEAL(String pwd) {
        TEAL.loginTEAL(driver, baseUrl, uiddatamap.get("userid"), pwd);
    }
    
    @Test(description = "Access TSDS")
    public void accessTSDS() {
    	TEAL.accessApplicationByHref(driver, "TSDS");
    }
    
    @Test(description = "Select Org")
    public void selectOrg() {
        TSDSPortal.selectOrg(driver, uiddatamap.get("orgid"));
    }
    
	@Test(description = "Launch Unique ID")
	public void launchUniqueID() {
		TSDSPortal.openApplication(driver, "Unique ID", "TSDS: Unique ID");
		TSDSUniqueIDPage.launchManageUniqueID(driver);
	}
	
	@Test(description = "Verify Unique ID Access")
	public void verifyUIDAccess() throws IOException {
		String accesspath = Path_FileCheck + uiddatamap.get("accessfile");
		TSDSManageUniqueIDPage tsdsmanage = new TSDSManageUniqueIDPage(driver);
		tsdsmanage.VerifyAccess(accesspath);
	}
	
	@Test(description = "Verify Unique ID Access")
	public void writeNewUIDAccess() throws IOException {
		String accesspath = uiddatamap.get("accessfile");
		TSDSManageUniqueIDPage tsdsmanage = new TSDSManageUniqueIDPage(driver);
		tsdsmanage.writeAccess(accesspath);
	}
	
	@Test(description = "Verify UID Person Search")
	public void verifyUIDPersonSearch() {
		TSDSManageUniqueIDPage tsdsmanage = new TSDSManageUniqueIDPage(driver);
		String leaid = uiddatamap.get("leaid");
		if (leaid.length() > 0) {
			tsdsmanage.changeDistrict(leaid);
			tsdsmanage.enterOnline(uiddatamap.get("campusid"), leaid);
		}
	    tsdsmanage.searchPerson(uiddatamap.get("fname"), uiddatamap.get("lname"));
	    Assert.assertTrue(tsdsmanage.personSearchResultCount() >= 2);
	    tsdsmanage.backHome();
	}
	
	@Test(description = "Verify UID Reports")
	public void verifyUIDReports() {
		if (uiddatamap.get("userid").contains("Admin")) {
			TSDSManageUniqueIDPage tsdsmanage = new TSDSManageUniqueIDPage(driver);
		    tsdsmanage.accessReports();
		    tsdsmanage.runAliasIDReport();
		    Assert.assertTrue(tsdsmanage.verifyAliasReportCount() >= 2);
		}
	}
	
	@Test(description = "Verify Support Access")
	public void verifySupport() {
		TSDSPortal.openApplication(driver, "Support", "TSDS: Support");
        TSDSSupportPage tsdssupport = new TSDSSupportPage(driver);
        tsdssupport.verifyAccess(uiddatamap.get("userid"));
        TSDSPortal.exitApplication(driver);
	}
	
	@Test(description = "Exit Unique ID")
	public void exitUID() {
		TSDSPortal.exitApplication(driver);
		TSDSUniqueIDPage.exitTSDS(driver);
		TEAL.logout(driver);
		TEAL.accesslogin(driver);
	}

}
