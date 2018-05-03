package tea.ivv.tsds.TSDSDashboards;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.github.yev.FailTestScreenshotListener;

import us.tx.state.tea.seleniumfw.tsdsportal.*;
import us.tx.state.tea.seleniumfw.teal.*;
import us.tx.state.tea.seleniumfw.*;

@Listeners(FailTestScreenshotListener.class)
public class StudentGPSDashboardsTests {
	DriverManager driverManager;
	public WebDriver driver;
	protected Logger log = Logger.getLogger(this.getClass().getName());
	//public static final String baseDir = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
	public static String baseDir = System.getProperty("systembaseDir");
	public static final String baseUrl = System.getProperty("systemUrl");
	
	@BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception{
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        driver = driverManager.getRemoteDriver(baseDir);
    }
   
    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
	log.info("End of TSDS Dashboards testing");
        driverManager.quitDriver();
    }
	
    @Test(description = "Login TEAL")
    @Parameters({"loginId", "pwd"})
    public void loginTEAL(String loginId, String pwd) {
    	System.out.println("Starting GPS Dashboards Script");
        TEAL.loginTEAL(driver, baseUrl, loginId, pwd);
    }
    
    @Test(description = "Access TSDS")
    public void accessTSDS() {
        TEAL.accessApplicationByHref(driver, "TSDS");
    }
    
    @Test(description = "Select Org")
    @Parameters({"orgId"})
    public void selectOrg(String orgId) {
        TSDSPortal.selectOrg(driver, orgId);
    }
    
    @Test(description = "Access Dashboards Home")
    public void accessDashboardsHome() {
        TSDSPortal.openApplication(driver, "studentGPS", "TSDS: studentGPS Dashboards");
    }
        
    @Test(description= "Access Dashboards")
    @Parameters({"privledge"})
    public void accessDashboards(String privledge) {
        TSDSDashboardsPage stugps = new TSDSDashboardsPage(driver);
        stugps.viewstudentGPS(privledge);
    }
    
    @Test(description = "Access LEA Admin")
    @Parameters({"lea", "orgId"})
    public void runGPSDashboards(String lea, String orgId) throws InterruptedException {
        TSDSDashboardLEAAdminPage leadmin = new TSDSDashboardLEAAdminPage(driver);
        leadmin.clicklea(lea);
        TSDSDashboardsLEAOverviewPage leaov = new TSDSDashboardsLEAOverviewPage(driver);
        leaov.accessStateAssessments();;
        TSDSDashboardsStateAssessmentsPage sap = new TSDSDashboardsStateAssessmentsPage(driver);
        sap.clickSTAARReadingCampusList();
        String campusname = sap.getCampusName();
        sap.accessCampus(campusname);
        TSDSDashboardsCampusStateAssessmentsPage campsap = new TSDSDashboardsCampusStateAssessmentsPage(driver);
        campsap.clickSTAARReadingGradeChart();
        Assert.assertTrue(campsap.verifyChartDisplayed(orgId), "Grade Chart not displayed");
        campsap.clickLEAName(lea);
        leaov.accessAttendanceandDiscipline();
        TSDSDashboardsAttendanceandDisciplinePage adpage = new TSDSDashboardsAttendanceandDisciplinePage(driver);
        adpage.campusListforAverage();
        adpage.accessCampus(campusname);
        TSDSDashboardsCampusAttendanceandDisciplinePage campadpage = new TSDSDashboardsCampusAttendanceandDisciplinePage(driver);
        campadpage.clickAttendanceHistoricalChart();
        Assert.assertTrue(campadpage.getattendanceHistoricalChartdata().contains("School Goal:"), "Historical Chart not displayed");
        campadpage.accessCampusInformation();
        TSDSDashboardsCampusInformationPage campinfo = new TSDSDashboardsCampusInformationPage(driver);
        campinfo.accessStudentsbyGrade();
        TSDSDashboardsCampusStudentPage stupage = new TSDSDashboardsCampusStudentPage(driver);
        String stuname = stupage.getStudentName();
        stupage.accessStudentOverview(stuname);
        TSDSDashboardsStudentOverviewPage stuov = new TSDSDashboardsStudentOverviewPage(driver);
        stuov.accessStudentAttendanceandDiscipline();
        TSDSDashboardsStudentAttendanceandDisciplinePage stuad = new TSDSDashboardsStudentAttendanceandDisciplinePage(driver);
        stuad.accessStudentAttendanceRateChart();
        Assert.assertTrue(stuad.verifyattendanceRateChart(), "Student Attendance Rate Chart not displayed");
        stuad.accessStudentTranscript();
        TSDSDashboardsStudentTranscriptPage stutras = new TSDSDashboardsStudentTranscriptPage(driver);
        Assert.assertTrue(stutras.getStudentTranscriptCount() > 1, "Student Transcript Failed to Load");
        stutras.accessCampusOverview(campusname);
        TSDSDashboardsCampusOverviewPage campov = new TSDSDashboardsCampusOverviewPage(driver);
        campov.accessCampusInformation();
        campinfo.accessStaffList();
        TSDSDashboardsCampusStaffPage staffpg = new TSDSDashboardsCampusStaffPage(driver);
        Assert.assertTrue(staffpg.getStaffListCount() > 1, "Staff Page Failed to Load");
        Assert.assertTrue(staffpg.checkSupportTicketLoads(), "Support Ticket Failed to Load");
        staffpg.exitDashboards();
    }
    
    @Test(description = "Check Dashboard Reports")
    @Parameters({"reportname"})
    public void runDashboardReport(String reportname){
    	TSDSDashboardsPage stugps = new TSDSDashboardsPage(driver);
        stugps.viewReports();
        TSDSDashboardsReportPage dashrpt = new TSDSDashboardsReportPage(driver);
        dashrpt.runReport(reportname);
        Assert.assertTrue(reportname.equals(dashrpt.checkReport()), "Dashboard Reports appears to be down.");
        System.out.println(dashrpt.checkReport());
    }
}
