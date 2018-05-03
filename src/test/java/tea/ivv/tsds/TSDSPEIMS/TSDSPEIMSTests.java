package tea.ivv.tsds.TSDSPEIMS;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test; 
import com.github.yev.FailTestScreenshotListener;
import us.tx.state.tea.seleniumfw.tsdsportal.*;
import us.tx.state.tea.seleniumfw.utils.*;
import us.tx.state.tea.seleniumfw.teal.*;
import us.tx.state.tea.seleniumfw.*;

@Listeners(FailTestScreenshotListener.class)
public class TSDSPEIMSTests {
	private static WebDriver driver = null;
	//public static String basePath = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
	public static String basePath = System.getProperty("systembaseDir");
	//public static final String baseUrl = "https://tealtst.tea.state.tx.us";
	public static final String Path_FileDownload = basePath + "\\resources\\downloads\\";
	public final String datepattern = "MM/dd/yyyy";
	public static final String baseUrl = System.getProperty("systemUrl");
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
	log.info("End of TSDS PEIMS testing");
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
    
    @Test(description = "Launch PEIMS")
    public void launchPEIMS() {
        TSDSPortal.openApplication(driver, "PEIMS", "TSDS: PEIMS");
    }
    
    @Test(description = "Set Collection")
    @Parameters({"acadYr", "collectionId", "submissionId"})
    public void setCollection(String acadYr, String collectionId, String submissionId) throws InterruptedException {
        TSDSPEIMSPage peimshp = new TSDSPEIMSPage(driver);
        peimshp.launchPromoteLoadedData();
        TSDSDataPromotionPage dppage = new TSDSDataPromotionPage(driver);
        dppage.setUpParams(acadYr, collectionId, submissionId);
    }
    
    @Test(description = "Promote Loaded Data")
    public void promoteCampusLoadedData() throws InterruptedException {
    	TSDSDataPromotionPage dppage = new TSDSDataPromotionPage(driver);
        Assert.assertTrue(dppage.promoteAllCategories() == 4, "Wrong Number of items in Campus Promotion");
        dppage.submitPromotion();
        TSDSConfirmDataPage confirmpage = new TSDSConfirmDataPage(driver);
        confirmpage.submitPromotionData();
        TSDSMonitorPromotionDataPage monitorpropage = new TSDSMonitorPromotionDataPage(driver);
        Assert.assertTrue(monitorpropage.getPromotionStatus().equals("COMPLETED"), "Promotion Failed");
    }
    
    @Test(description = "Promote Loaded Data")
    public void promoteLoadedData() throws InterruptedException {
    	TSDSDataPromotionPage dppage = new TSDSDataPromotionPage(driver);
        Assert.assertTrue(dppage.promoteAllCategories() == 6, "Wrong Number of items in Campus Promotion");
        dppage.submitPromotion();
        TSDSConfirmDataPage confirmpage = new TSDSConfirmDataPage(driver);
        confirmpage.submitPromotionData();
        TSDSMonitorPromotionDataPage monitorpropage = new TSDSMonitorPromotionDataPage(driver);
        Assert.assertTrue(monitorpropage.getPromotionStatus().equals("COMPLETED"), "Promotion Failed");
    }
    
    @Test(description = "Promote Specific Category and SubCategory Data")
    @Parameters({"cat", "subcat"})
    public void promoteSpecificCatSubCat(String cat, String subcat) throws InterruptedException {
    	TSDSDataPromotionPage dppage = new TSDSDataPromotionPage(driver);
        Assert.assertTrue(dppage.promotespecificCatandSubCat(cat, subcat) > 3, "Wrong Number of items in Campus Promotion");
        dppage.submitPromotion();
        TSDSConfirmDataPage confirmpage = new TSDSConfirmDataPage(driver);
        confirmpage.submitPromotionData();
        TSDSMonitorPromotionDataPage monitorpropage = new TSDSMonitorPromotionDataPage(driver);
        Assert.assertTrue(monitorpropage.getPromotionStatus().equals("COMPLETED"), "Promotion Failed");
    }
    
    @Test(description = "Validate Data")
    public void validateData() throws InterruptedException {
    	TSDSMonitorPromotionDataPage monitorpropage = new TSDSMonitorPromotionDataPage(driver);
        monitorpropage.launchValidateData();
        TSDSValidatePromotedDataPage valpage = new TSDSValidatePromotedDataPage(driver);
        valpage.selectAllCategories();
        Assert.assertTrue(valpage.getFatalFlag(), "Fatal Flag is not Selected");
        Assert.assertTrue(valpage.getSpecialWarningFlag(), "Special Warning Flag is not Selected");
        Assert.assertTrue(valpage.getWarningFlag(), "Warning Flag is not Selected");
        valpage.submitValidation();
        TSDSConfirmDataPage confirmpage = new TSDSConfirmDataPage(driver);
        confirmpage.submitValidationData();
        TSDSMonitorDataValidationPage monitorvalpage = new TSDSMonitorDataValidationPage(driver);
        Assert.assertTrue(monitorvalpage.getValidationStatus().contains("COMPLETED"), "Validation Failed");
    }
    
    @Test(description = "Access PEIMS Application Home Page")
    public void accessPEIMSApplicationPage() {
    	TSDSPortal.openApplication(driver, "PEIMS Application Home", "PEIMS Home");
    }
    
    @Test(description = "Access Collection Override")
    @Parameters({"acadYr", "collectionId", "submissionId"})
    public void accessoverrideCollection(String acadYr, String collectionId, String submissionId) throws InterruptedException{
    	TSDSPEIMSApplicationHomePage apphome = new TSDSPEIMSApplicationHomePage(driver);
    	apphome.updateSubmissionStatus();
    	TSDSPEIMSAdministrationPage adminpage = new TSDSPEIMSAdministrationPage(driver);
    	adminpage.setUpParams(acadYr, collectionId, submissionId);
    }
    
    @Test(description = "Override Collection Status")
    @Parameters({"leaid", "overridetype", "newstatus"})
    public void overrideCollection(String leaid, String overridetype, String newstatus){
    	TSDSPEIMSAdministrationPage adminpage = new TSDSPEIMSAdministrationPage(driver);
    	adminpage.overrideCollection(leaid, overridetype, newstatus);
    	TSDSConfirmStatusOverridePage confirmoverride = new TSDSConfirmStatusOverridePage(driver);
    	confirmoverride.confirmOverride();
    	Assert.assertTrue(adminpage.getConfirmationMessage().contains(newstatus));
    }
    
    @Test(description = "Exit Search Student Roster")
    public void exitStudentRoster() {
    	TSDSPortal.exitApplication(driver);
    }
    
    @Test(description = "Exit Prepare Finalize Page")
    public void exitPrepareFinalize(){
    	TSDSPortal.exitApplication(driver);
    }
    
    @Test(description = "Exit TSDS")
    public void exitTSDS() {
    	TSDSPortal.exitTSDS(driver);
    	TEAL.logout(driver);
    	TEAL.accesslogin(driver);
    }
    
    @Test(description = "Validate Data")
    @Parameters({"cat", "subcat"})
    public void validateSpecificCatSubCatData(String cat, String subcat) throws InterruptedException {
    	TSDSMonitorPromotionDataPage monitorpropage = new TSDSMonitorPromotionDataPage(driver);
        monitorpropage.launchValidateData();
        TSDSValidatePromotedDataPage valpage = new TSDSValidatePromotedDataPage(driver);
        valpage.selectSpecificCatandSubCatData(cat, subcat);
        Assert.assertTrue(valpage.getFatalFlag(), "Fatal Flag is not Selected");
        Assert.assertTrue(valpage.getSpecialWarningFlag(), "Special Warning Flag is not Selected");
        Assert.assertTrue(valpage.getWarningFlag(), "Warning Flag is not Selected");
        valpage.submitValidation();
        TSDSConfirmDataPage confirmpage = new TSDSConfirmDataPage(driver);
        confirmpage.submitValidationData();
        TSDSMonitorDataValidationPage monitorvalpage = new TSDSMonitorDataValidationPage(driver);
        Assert.assertTrue(monitorvalpage.getValidationStatus().contains("COMPLETED"), "Validation Failed");
    }
    
    @Test(description = "Checking Campus Prepare Status")
    public void checkCampusPrepareStatus() throws Exception{
    	String preparestatus;
    	TSDSMonitorDataValidationPage monitorvalpage = new TSDSMonitorDataValidationPage(driver);
        monitorvalpage.accessPrepareFinalize();
        TSDSPrepareFinalizePage pfpage = new TSDSPrepareFinalizePage(driver);
        preparestatus = pfpage.findCampusPrepareStatus();
        Assert.assertTrue(pfpage.verifyCampusStatus().equals(preparestatus), pfpage.verifyCampusStatus() + " recieved expected " + preparestatus);
    }
    
    @Test(description = "Checking LEA Prepare Status")
    public void checkLEAPrepareStatus() throws Exception{
    	String preparestatus;
    	TSDSMonitorDataValidationPage monitorvalpage = new TSDSMonitorDataValidationPage(driver);
        monitorvalpage.accessPrepareFinalize();
        TSDSPrepareFinalizePage pfpage = new TSDSPrepareFinalizePage(driver);
        preparestatus = pfpage.findPrepareStatus();
        Assert.assertTrue(pfpage.verifyStatus().equals(preparestatus), pfpage.verifyStatus() + " recieved expected " + preparestatus);
    }
    
    @Test(description = "Complete Data")
    public void completeData() throws Exception{
        TSDSPrepareFinalizePage pfpage = new TSDSPrepareFinalizePage(driver);
        Assert.assertTrue(pfpage.isCompleteButtonEnabled(), "Complete Button is not Enabled");
        String confirmmessage = pfpage.runComplete();
        Assert.assertTrue(pfpage.getCollectionStatus().equals("LEA DATA COMPLETE"), "Collection Status is wrong, " + pfpage.getCollectionStatus() + " was displayed");
        Assert.assertFalse(pfpage.isCompleteButtonEnabled(), "Complete Button is Enabled and should not be");
        Assert.assertTrue(confirmmessage.contains("By checking this box, I acknowledge that all data included in the submission has been validated and reviewed for accuracy and authenticity."));
    }
    
    @Test(description = "Accept Data")
    public void acceptData() throws Exception{
        TSDSPrepareFinalizePage pfpage = new TSDSPrepareFinalizePage(driver);
        Assert.assertTrue(pfpage.isAcceptButtonEnabled(), "Accept Button is Disabled");
        Assert.assertTrue(pfpage.isRejectButtonEnabled(), "Reject Button is Disabled");
        Assert.assertTrue(pfpage.getCollectionStatus().equals("LEA DATA COMPLETE"), "Collection Status is wrong, " + pfpage.getCollectionStatus() + " was displayed");
        String acceptmessage = pfpage.acceptData();
        Assert.assertTrue(acceptmessage.equals("Click OK to accept this collection."), "Incorrect Accept Message recieved");
        System.out.println(pfpage.getCollectionStatus());
        Assert.assertFalse(pfpage.isAcceptButtonEnabled(), "Accept Button is Enabled and should not be");
        Assert.assertFalse(pfpage.isRejectButtonEnabled(), "Reject Button is Enabled and should not be");
    }
    
    @Test(description="Select LEA")
    @Parameters({"lea"})
    public void selectLEA(String lea){
        TSDSPEIMSPage peimshp = new TSDSPEIMSPage(driver);
        peimshp.launchPrepareFinalizeData();
    	TSDSPrepareFinalizePage pfpage = new TSDSPrepareFinalizePage(driver);
    	pfpage.selectLEA(lea);
    }
    
    @Test(description = "Run Reports")
    @Parameters({"org", "reportNum", "reportTitle"})
    public void runReports(String org, String reportNum, String reportTitle) throws InterruptedException, ExecutionException, IOException {
    	TSDSPrepareFinalizePage pfpage = new TSDSPrepareFinalizePage(driver);
        pfpage.selectViewReports();
        TSDSViewReportsPage reportspage = new TSDSViewReportsPage(driver);
        if (org.length() == 9) {
        	reportspage.runReport("By Campus", reportNum);
        } else {
        	reportspage.runReport("By LEA", reportNum);
        }
        File f1 = new File(Path_FileDownload);
        if (f1.listFiles().length > 0) {
        	for(File ifile: f1.listFiles()) {
        		ifile.delete();
        	}
        }
        reportspage.downloadReport(reportNum);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new pdfTask());
        try {
            System.out.println("Started..");
            System.out.println(future.get(60, TimeUnit.SECONDS));
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated!");
        }
        PDDocument reportpdf = PDFUtils.loadDoc(Path_FileDownload + reportNum + "_" + reportTitle + "_" + org + ".pdf");
        String pdftext = PDFUtils.getText(reportpdf);
        Assert.assertTrue(pdftext.contains(rundate));
        Assert.assertTrue(pdftext.contains(org));
  	  	executor.shutdown();
        System.out.println("PEIMS Report Check Passed");
        try {
        	reportpdf.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Test (description = "Search Submission Data")
    @Parameters({"cat", "subcat", "name"})
    public void searchSubmissionData(String cat, String subcat, String name){
    	TSDSViewReportsPage reportspage = new TSDSViewReportsPage(driver);
        reportspage.accessSearchSubmissionData();
        TSDSSearchSubmissionDataPage subsearchpage = new TSDSSearchSubmissionDataPage(driver);
        subsearchpage.searchSubmissionbyFirstName(cat, subcat, name);
        //subsearchpage.searchSubmissionbyFirstName("Student", "Student Basic Information", "TAYLOR");
        //subsearchpage.searchSubmissionbyFirstName("Staff", "Staff Basic Information", "DONNA");
        Assert.assertTrue(subsearchpage.verifyDataSearch() >= 2);
    }
    
    @Test(description = "Run Data Element Summary")
    @Parameters({"elementnum", "elementname"})
    public void dataElementSummary(String elementnum, String elementname) throws InterruptedException {
    	TSDSViewReportsPage reportspage = new TSDSViewReportsPage(driver);
        reportspage.accessDataElementSummary();
        TSDSDataElementSummaryPage datasumpage = new TSDSDataElementSummaryPage(driver);
        datasumpage.searchDataElement(elementnum);
        datasumpage.verifyDataElement(elementnum, elementname);
        System.out.println("Data Element Summary Passed");
    }
    
    @Test(description = "Retrieve Submission Data")
    public void retrieveSubmissionData() throws InterruptedException, ExecutionException, IOException {
    	TSDSSearchSubmissionDataPage subsearchpage = new TSDSSearchSubmissionDataPage(driver);
        subsearchpage.accessRetrieveSubmissionData();
        System.out.println("Accessing Data Retrieval");
        TSDSRetrieveSubmissionDataPage retrievesubpage = new TSDSRetrieveSubmissionDataPage(driver);
        retrievesubpage.downloadAllCategories();
        TSDSConfirmDataPage confirmpage = new TSDSConfirmDataPage(driver);
        confirmpage.submitDataDownload();
        TSDSMonitorDataDownloadPage datadownloadpage = new TSDSMonitorDataDownloadPage(driver);
        System.out.println("Monitoring Data Retrieval");
        String status = datadownloadpage.waittillDataDowloadCompletes();
        Assert.assertTrue(status.equals("COMPLETED"));
        File datafile = new File(Path_FileDownload);
        //initialcount = datafile.listFiles().length;
        if (datafile.listFiles().length > 0) {
        	for(File ifile: datafile.listFiles()) {
        		ifile.delete();
        	}
 //     		initialcount = 0;
        }
        System.out.println("All Old Files Deleted, downloading new File");
        datadownloadpage.downloadData();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new zipTask());
        try {
        	System.out.println("Started..");
            System.out.println(future.get(10, TimeUnit.SECONDS));
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated!");
        }
      	executor.shutdown();
        File zipfile = new File(Path_FileDownload);
//      File[] ziplist = zipfile.listFiles(zipFilter);
        System.out.println("Unzipping Data Dowload File");
        unzip(Path_FileDownload + "DataDownload.zip",Path_FileDownload);
        Assert.assertTrue(zipfile.listFiles(csvFilter).length >= 10);
        System.out.println("Data Retrieval Test Passed");
        if (datafile.listFiles().length > 0) {
        	for(File ifile: datafile.listFiles()) {
        		ifile.delete();
        	}
        	//	initialcount = 0;
        }
    }
    
    @Test (description = "searchStudentRoster")
    @Parameters({"collectionId", "lname"})
    public void searchStudentRoster(String collectionId, String lname) {
        if (collectionId.equals("FALL")) {
        	TSDSMonitorDataDownloadPage datadownloadpage = new TSDSMonitorDataDownloadPage(driver);
        	datadownloadpage.accessSearchStudentRoster();
        	TSDSSearchStudentRosterPage rosterpage = new TSDSSearchStudentRosterPage(driver);
        	System.out.println("Runnding Student Roster Search");
        	rosterpage.searchByLastName(lname);
        	Assert.assertTrue(rosterpage.countTableRows() >= 2);
        }
        
    }
    
    class pdfTask implements Callable<String> {
	    @Override
	    public String call() throws Exception {
	    	File f2 = new File(Path_FileDownload);
	        File[] pdflist = f2.listFiles(pdfFilter);
	        while (pdflist.length == 0){
	            pdflist = f2.listFiles(pdfFilter);
	        }
	        return "Ready!";
	    }
	}
    
    class zipTask implements Callable<String> {
	    @Override
	    public String call() throws Exception {
	    	File f2 = new File(Path_FileDownload);
	        File[] pdflist = f2.listFiles(zipFilter);
	        while (pdflist.length == 0){
	            pdflist = f2.listFiles(zipFilter);
	        }
	        return "Ready!";
	    }
	}
    
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
        	destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
        	String filePath = destDirectory + File.separator + entry.getName();
        	if (!entry.isDirectory()) {
        		// if the entry is a file, extracts it
        		extractFile(zipIn, filePath);
        	} else {
        		// if the entry is a directory, make the directory
        		File dir = new File(filePath);
        		dir.mkdir();
        	}
        	zipIn.closeEntry();
        	entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
    	final int BUFFER_SIZE = 4096;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
        	bos.write(bytesIn, 0, read);
        }
        bos.close();
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
