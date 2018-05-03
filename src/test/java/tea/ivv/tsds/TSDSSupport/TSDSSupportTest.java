package tea.ivv.tsds.TSDSSupport;

import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.yev.FailTestScreenshotListener;
import tea.ivv.tsds.TSDSPEIMS.TSDSDisplayKnowledgeBaseResultsPage;
import us.tx.state.tea.seleniumfw.tsdsportal.*;
import us.tx.state.tea.seleniumfw.utils.*;
import us.tx.state.tea.seleniumfw.teal.*;
import us.tx.state.tea.seleniumfw.*;

@Listeners(FailTestScreenshotListener.class)
public class TSDSSupportTest {
	DriverManager driverManager;
	private static WebDriver driver = null;
	private String timssub;
	private String tsdsincidentnumber;
	//public static String baseDir = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
	public static String baseDir = System.getProperty("systembaseDir");
	public static final String Path_FileDownload = baseDir + "\\resources\\downloads\\";
	public final String datepattern = "MM/dd/yyyy";
	public static final String baseUrl = System.getProperty("systemUrl");
	//public static final String baseUrl = "http://tealprod.tea.state.tx.us";
    protected Logger log = Logger.getLogger(this.getClass().getName());
	String rundate = CreateDate.newDate(datepattern);
	
	@BeforeTest(alwaysRun = true)
    public void setUp() throws Exception{
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        driver = driverManager.getRemoteDriver(baseDir);
        
    }
   
    @AfterTest(alwaysRun = true)
    public void setupAfterSuite() {
	log.info("End of Support Regression testing");
        driverManager.quitDriver();
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
    }
    
    @Test(description = "Launch Support")
    public void launchsupport() {
        TSDSPortal.openApplication(driver, "Support", "TSDS: Support");
    }
    
    @Test(description = "Click Create Incident")
    public void clickCreateIncident() {
        TSDSSupportPage tsdssupport = new TSDSSupportPage(driver);
        tsdssupport.createIncident();
    }
    
    @Test (description = "Submit Incident")
    public void submitIncident() {
        TSDSCreateIncidentPage tsdsincident = new TSDSCreateIncidentPage(driver);
        timssub = tsdsincident.submitIncident();
        tsdsincidentnumber = tsdsincident.verifyIncident();
        tsdsincident.exitCreateIncident();
    }
    
    @Test (description = "View Incident")
    public void viewIncident() {
    	TSDSSupportPage tsdssupport = new TSDSSupportPage(driver);
        tsdssupport.viewIncidents();
        TSDSViewIncidentPage tsdsmyview = new TSDSViewIncidentPage(driver);
    	tsdsmyview.verifyIncident(tsdsincidentnumber, timssub);
    	tsdsmyview.exitViewIncident();
    }
    
    @Test (description = "Search Knowledgebase")
    @Parameters({"searchstring", "resultlink"})
    public void searchKnowledgebase(String searchstring, String resultlink) {
    	TSDSSupportPage tsdssupport = new TSDSSupportPage(driver);
    	tsdssupport.searchKnowledgeBase();
    	TSDSSearchKnowledgeBasePage tsdskb = new TSDSSearchKnowledgeBasePage(driver);
    	tsdskb.searchKnowledgeBase(searchstring);
    	Assert.assertTrue(tsdskb.verifyResultCount() >= 2, "Displayed Results Count is " + tsdskb.verifyResultCount() + " should be atleast 2 rows returned");
    	tsdskb.accessSearchResults(resultlink);
    	TSDSDisplayKnowledgeBaseResultsPage kbresults = new TSDSDisplayKnowledgeBaseResultsPage(driver);
    	Assert.assertTrue(TSDSDisplayKnowledgeBaseResultsPage.getResultsHeader(driver).contains(resultlink));
    	kbresults.exitResults();
    	tsdskb.exitKnowledgeBase();
    }
    
    @Test (description = "Access TIMS")
    public void accessTIMS() {
    	TSDSSupportPage tsdssupport = new TSDSSupportPage(driver);
    	tsdssupport.accessTIMS();
    }
    
    @Test (description = "Verify TIMS Access")
    public void verifyTIMSAccess() {
    	TSDSTIMSSupportPage tims = new TSDSTIMSSupportPage(driver);
    	tims.accessProfile();
    	TSDSTIMSUserProfilePage timsprofile = new TSDSTIMSUserProfilePage(driver);
    	Assert.assertTrue(timsprofile.verifyAccess());
    	timsprofile.returnToTIMSHome();
    	tims.exitTIMS();
    }
    
    @Test (description = "Verify Incident Loaded")
    public void verifyIncidentLoaded() {
    	TSDSCreateIncidentPage tsdsincident = new TSDSCreateIncidentPage(driver);
    	Assert.assertTrue(tsdsincident.verifyIncidentLoaded());
    	tsdsincident.cancelIncident();
    }
    
    @Test (description = "Verify My Incidents")
    public void verifyMyIncidents() {
    	TSDSSupportPage tsdssupport = new TSDSSupportPage(driver);
    	tsdssupport.viewIncidents();
    	TSDSViewIncidentPage tsdsmyview = new TSDSViewIncidentPage(driver);
    	tsdsmyview.verifyIncident(tsdsincidentnumber, timssub);
    	tsdsmyview.exitViewIncident();
    }
    	
    @Test (description = "Search TIMS")
    public void searchforTIMSIssue() {
    	TSDSTIMSSupportPage tims = new TSDSTIMSSupportPage(driver);
    	tims.accessMyIssues();
    	TSDSTIMSIssueNavigator issuenav = new TSDSTIMSIssueNavigator(driver);
    	issuenav.issuesByAssignee();
    	issuenav.verifyIssueDisplayed(timssub);
    	issuenav.returnToTIMSHome();
    	tims.exitTIMS();
    }
    
    @Test(description = "Launch Utilities")
    public void launchUtilities() {
        TSDSPortal.openApplication(driver, "Utilities", "TSDS: Utilities");
    }
    
    @Test (description = "Verify Validation Tool")
    public void verifyValdiationTool() throws InterruptedException, ExecutionException {
        TSDSUtilitiesPage tsdsutil = new TSDSUtilitiesPage(driver);
        Assert.assertTrue(tsdsutil.verifyValidationTool(), "Validation Tool did not display");
        Assert.assertTrue(tsdsutil.downloadValidationTool(), "Validation Tool did not download correctly");
    }
    
    @Test (description = "Verify DTU")
    public void verifyDTU() throws InterruptedException, ExecutionException {
        TSDSUtilitiesPage tsdsutil = new TSDSUtilitiesPage(driver);
        Assert.assertTrue(tsdsutil.downloadDTU(), "Data Transfer Utility did not download correctly");
    	Assert.assertTrue(tsdsutil.verifyDTU(), "Data Transfer Utility did not display");
    }
    
    @Test (description = "Verify DTU does not exist")
    public void verifyNODTU() {
    	TSDSUtilitiesPage tsdsutil = new TSDSUtilitiesPage(driver);
    	Assert.assertFalse(tsdsutil.verifyDTU(), "DTU displayed and should not have");
    }
    
    
    @Test(description = "Verify TIMS Database")
    public void verifyTIMSDatabase() {
    	TSDSTIMSSupportPage tims = new TSDSTIMSSupportPage(driver);
    	tims.searchforIssues();
    	TSDSTIMSIssueNavigator issuenav = new TSDSTIMSIssueNavigator(driver);
    	String expecttitle = issuenav.clickFirstIssue().trim();
    	String actualtitle = issuenav.getIssueTitle().trim();
    	Assert.assertTrue(expecttitle.endsWith(actualtitle));
    	issuenav.returnToTIMSHome();
    	tims.exitTIMS();
    	}
    
    FilenameFilter pdfFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
        	String lowercaseName = name.toLowerCase();
        	if (lowercaseName.endsWith(".pdf")) {
        		return true;
        	} else {
        		return false;
        	}
        }
    };
    
    FilenameFilter csvFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
        	String lowercaseName = name.toLowerCase();
        	if (lowercaseName.endsWith(".csv")) {
        		return true;
        	} else {
        		return false;
        	}
        }
    };
    
    FilenameFilter zipFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
        	String lowercaseName = name.toLowerCase();
        	if (lowercaseName.endsWith(".zip")) {
        		return true;
        	} else {
        		return false;
        	}
        }
    };

}
