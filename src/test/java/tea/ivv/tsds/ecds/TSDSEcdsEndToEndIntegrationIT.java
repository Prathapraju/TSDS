/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tea.ivv.tsds.ecds;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import tea.ivv.tsds.TSDSPEIMS.TSDSConfirmDataPage;


/**
 *
 * @author jtran
 */
@Listeners(FailTestScreenshotListener.class)
public class TSDSEcdsEndToEndIntegrationIT {
    
    DriverManager driverManager;
    private static WebDriver driver ;
    //public static String basePath = "\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS";
    public static String basePath = System.getProperty("systembaseDir");
    private static String Path_FileDownload = basePath + "\\resources\\downloads\\";
    private final String datepattern = "MM/dd/yyyy";
    String rundate = CreateDate.newDate(datepattern);
    protected Logger log = Logger.getLogger(this.getClass().getName());
    public static final String baseUrl = System.getProperty("systemUrl");

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception{
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        driver = driverManager.getRemoteDriver(basePath);
    }
   
    @AfterSuite(alwaysRun = true)
    public void setupAfterSuite() {
	log.info("End of TSDS ECDS testing");
        driverManager.quitDriver();
    }

     
    @Test(description="Test 1.0 - Login to TEAL")
    @Parameters({"loginId","pwd"})
    public void testLogin(String loginId, String pwd) throws Exception {
        TEAL.loginTEAL(driver, baseUrl, loginId, pwd);
    }
    
    @Test(description="Test 2.0 - Launch TSDS Portal Link")
    public void launchTSDSPortal() throws Exception {  
        log.info("Launch TSDS Portal...");
        Assert.assertEquals(driver.getTitle(),"Texas Education Agency");
        TEAL.accessApplicationByHref(driver, "TSDS");
    }
    
    @Test(description="Test 3.0 - Selecting organization id ")
    @Parameters("orgId")
    public void testSelectOrgID(String orgId) throws Exception {
        log.info("Select Organization Id");
        Assert.assertEquals(driver.getTitle(),"TSDS: Home");
        TSDSPortal.selectOrg(driver, orgId);

    }
    
    @Test(description="Test 4.0 - Launch Core Collection")
    public void testLaunchCoreCollection() throws Exception {
        log.info("Select Core Collection tab");
                //Verify Core Collection tab is enabled 
        Assert.assertEquals(driver.findElement(By.linkText("Core Collection")).getText(),"Core Collection");
        Assert.assertTrue(driver.findElement(By.linkText("Core Collection")).isEnabled());
        Assert.assertTrue(driver.findElement(By.linkText("Core Collection")).isDisplayed()); 
        TSDSPortal.openApplication(driver, "Core Collection", "TSDS: Core Collection");
        String role = driver.findElement(By.id("selectLink")).getAttribute("value");
        switch(role) {
            case "ECDSApprover":
                Assert.assertEquals(driver.findElement(By.id("EasyBtnFinalSub")).getAttribute("title"), "Prepare/Finalize Data");
                Assert.assertEquals(driver.findElement(By.id("EasyBtnReport")).getAttribute("title"),"View Reports");
                Assert.assertEquals(driver.findElement(By.id("EasyBtnFinalSub")).isEnabled(),true);
                Assert.assertEquals(driver.findElement(By.id("EasyBtnReport")).isEnabled(),true);
                Assert.assertEquals(driver.findElement(By.id("EasyBtnReport")).isDisplayed(),true);
                Assert.assertEquals(driver.findElement(By.id("EasyBtnFinalSub")).isDisplayed(),true);
                break;
            case "SysSupport":
                Assert.assertEquals(driver.findElement(By.id("EasyBtnReport")).getAttribute("title"),"View Reports");
                Assert.assertEquals(driver.findElement(By.id("EasyBtnReport")).isEnabled(),true);
                Assert.assertEquals(driver.findElement(By.id("EasyBtnReport")).isDisplayed(),true);
                Assert.assertEquals(driver.findElement(By.id("EasyBtnEcds")).getAttribute("title"),"Configure Data Set");
                Assert.assertTrue(driver.findElement(By.id("EasyBtnEcds")).isEnabled());
                Assert.assertTrue(driver.findElement(By.id("EasyBtnEcds")).isEnabled());
                  
        }
        
    } 
    @Test(description="Test 5.0 - Launch ECDS Application Home")
    public void testLaunchECDSApplicationHome() throws Exception {
        log.info("Launch ECDS Application Home link...");
        Assert.assertEquals(driver.getTitle(), "TSDS: Core Collection");
        TSDSCoreCollectionPage ccp = new TSDSCoreCollectionPage(driver);
        ccp.launchECDSApplicationHome();
        log.info(driver.getTitle()); 
    }

    @Test(description="Test 6.0 - Select Parameters")
    @Parameters({"acadYr","collectionId"})
    public void testSetUpParams(String acadYr, String collectionId ) throws Exception {
        log.info("Setting up params...");
        log.info("Current url in Main: " + driver.getCurrentUrl())  ;
        log.info("Page title in Main:"  + driver.getTitle());
        log.info("Number of windows in Main: " + driver.getWindowHandles().size());
        TSDSCoreCollectionHomePage cchp = new TSDSCoreCollectionHomePage(driver);
        cchp.setUpParams(acadYr, collectionId);
        //Assert.assertEquals(driver.getTitle(),"Core Collection Home");
        
    }
  
    @Test (description="Test 7.0 - Launch Prepare / Finalize Submission tab")
    public void testLaunchPrepareFinalizeSubmission() throws Exception {
        log.info("Launching Prepare/Finalize Submission...");
        TSDSCoreCollectionHomePage cchp = new TSDSCoreCollectionHomePage(driver);
        Assert.assertEquals(driver.findElement(By.cssSelector("div#content>h1")).getText(),"Welcome to Core Collection");
        cchp.launchPrepareFinalizeSubmission();
       
    }
   
    @Test (description="Test 9.0 - Run Prepare")
    public void testRunPrepare() throws Exception {
        log.info("Run Prepare...");
        TSDSPrepareFinalizePage pfp = new TSDSPrepareFinalizePage(driver);
        pfp.runPrepare();
        if (pfp.getPrepare()) {
            new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOfElementLocated(By.id("resetButtonId")));
            //log.info("LEA status = " + driver.findElement(By.xpath("//form[@id='searchForm']/table/tbody/tr/td/a")).getText());
            //Assert.assertEquals(driver.findElement(By.xpath("//form[@id='searchForm']/table/tbody/tr/td/a")).getText(),"PREPARED");
            log.info("LEA status = " + driver.findElement(By.xpath("//a[contains(@href, 'view-collection-history.htm')]")).getText());
            Assert.assertEquals(driver.findElement(By.xpath("//a[contains(@href, 'view-collection-history.htm')]")).getText(),"PREPARED");
            Assert.assertEquals(driver.findElement(By.id("resetButtonId")).isEnabled(),false);   //Verify Reset button is disabled.
            Assert.assertEquals(driver.findElement(By.id("prepareButtonId")).isEnabled(),true);
            Assert.assertEquals(driver.findElement(By.id("completeButtonId")).isEnabled(),true); 
            /*Verify Campus subcategory - NOTE: These are for KG data only
            Assert.assertEquals(driver.findElement(By.xpath("//table[@id='dtTaskSummary']/tbody/tr/td[5]")).getText(), "2");
            //Verify Staff Basic Information subcategory
            Assert.assertEquals(driver.findElement(By.xpath("//table[@id='dtTaskSummary']/tbody/tr[3]/td[5]")).getText(), "29");
            //Verify Student Enrollment subcategory
            Assert.assertEquals(driver.findElement(By.xpath("//table[@id='dtTaskSummary']/tbody/tr[4]/td[5]")).getText(), "28");
            //Verify Student Basic Information subcategory
            Assert.assertEquals(driver.findElement(By.xpath("//table[@id='dtTaskSummary']/tbody/tr[5]/td[5]")).getText(), "27");
            //Verify Student Section subcategory
            Assert.assertEquals(driver.findElement(By.xpath("//table[@id='dtTaskSummary']/tbody/tr[6]/td[5]")).getText(), "33");
            
            //Verify Assessment Metadata
            Assert.assertEquals(driver.findElement(By.xpath("//table[@id='dtAssessmentSummary']/tbody/tr/td[5]")).getText(), "36");
            //Verify Student Assessment
            Assert.assertEquals(driver.findElement(By.xpath("//table[@id='dtAssessmentSummary']/tbody/tr[2]/td[5]")).getText(), "182"); */
        }

    } 
    
    @Test (description="Test 10.0 - Run Validate Submission Data")
    public void testRunValidate() throws Exception {
        log.info("Run Validation");
        TSDSPrepareFinalizePage pfp = new TSDSPrepareFinalizePage(driver);
        pfp.runValidateSubmissionData();
        Assert.assertEquals(driver.getTitle(), "Validate Submission Data");
        TSDSValidateSubmissionDataPage valdata = new TSDSValidateSubmissionDataPage(driver);
        valdata.validateAllCategories();
        TSDSConfirmDataPage confirm = new TSDSConfirmDataPage(driver);
        confirm.submitValidationData();
        TSDSMonitorDataValidationPage monitor = new TSDSMonitorDataValidationPage(driver);
        monitor.waittillValidationCompletes();
        monitor.accessPrepareFinalize();
    }
    
    @Test (description="Test 10.1 - Run Validate Submission Data on Specific Category/SubCategory")
    public void testValidateCatSubcat(String cat, String subcat) throws InterruptedException {
        log.info("Run Validation");
        TSDSPrepareFinalizePage pfp = new TSDSPrepareFinalizePage(driver);
        pfp.runValidateSubmissionData();
        Assert.assertEquals(driver.getTitle(), "Validate Submission Data");
        TSDSValidateSubmissionDataPage valdata = new TSDSValidateSubmissionDataPage(driver);
        valdata.validateSpecificCatandSubCatData(cat, subcat);
        TSDSConfirmDataPage confirm = new TSDSConfirmDataPage(driver);
        confirm.submitValidationData();
        TSDSMonitorDataValidationPage monitor = new TSDSMonitorDataValidationPage(driver);
        monitor.waittillValidationCompletes();
        monitor.accessPrepareFinalize();
    }
   
    @Test (description="Test 11.0 - Run Complete")
    public void testRunComplete() throws Exception {
        log.info("Run Complete");
        TSDSPrepareFinalizePage pfp = new TSDSPrepareFinalizePage(driver);
        pfp.runComplete();
        if (pfp.getComplete()) {
            log.info("Data is marked as completed");
            Assert.assertEquals(driver.findElement(By.id("resetButtonId")).isEnabled(),true); //Verify Reset button is enabled.
            Assert.assertEquals(driver.findElement(By.id("completeButtonId")).isEnabled(),false);   // Verify Complete button is disabled
            Assert.assertEquals(driver.findElement(By.id("prepareButtonId")).isEnabled(),false);   //Verify Prepare button is disabled
            log.info(driver.findElement(By.xpath("//*[@class='vertical']/tbody/tr/td")).getText());
            Assert.assertEquals(driver.findElement(By.xpath("//*[@class='vertical']/tbody/tr/td")).getText(), "COMPLETED");
        }
        else {
            log.info("Unable to complete - data either contains fatal error or button is disabled.");
            
        }
    }
    
    @Test (description="Test 12.0 - Run Reset")
    public void testRunReset() throws Exception {
        log.info("Run Reset");
        TSDSPrepareFinalizePage pfp = new TSDSPrepareFinalizePage(driver);
        pfp.runReset();
        if (pfp.getReset()) {
            log.info("After Reset is selected");
            Assert.assertEquals(driver.findElement(By.id("completeButtonId")).isEnabled(),true);
            Assert.assertEquals(driver.findElement(By.id("prepareButtonId")).isEnabled(),true);
        }   
    }
    
    @Test (description = "Test 13.0 - Run Reports")
    @Parameters({"orgId", "reportNum", "reportTitle"})
    public void runReports(String orgId, String reportNum, String reportTitle) throws InterruptedException, IOException, ExecutionException {
    	TSDSPrepareFinalizePage pfp = new TSDSPrepareFinalizePage(driver);
    	pfp.selectViewReports();
        TSDSViewReportsPage reportspage = new TSDSViewReportsPage(driver);
        reportspage.runReport("By LEA", reportNum);
        File f1 = new File(Path_FileDownload);
        if (f1.listFiles().length > 0) {
        	for(File ifile: f1.listFiles()) {
        		ifile.delete();
        	}
        }
        reportspage.downloadReport(reportNum);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Task());
        try {
            System.out.println("Started..");
            System.out.println(future.get(60, TimeUnit.SECONDS));
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated!");
        }
        PDDocument reportpdf = PDFUtils.loadDoc(Path_FileDownload + orgId + "_" + reportTitle + "_" + reportNum + ".pdf");
        String pdftext = PDFUtils.getText(reportpdf);
        Assert.assertTrue(pdftext.contains(rundate));
        Assert.assertTrue(pdftext.contains(orgId));  	  	
        executor.shutdown();
        System.out.println("ECDS Report Check Passed");
        try {
        	reportpdf.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static boolean isElementExist(String id, WebDriver driver) {
        boolean exists = false;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            driver.findElement(By.id(id));
            exists = true;
                
        }
        catch (NoSuchElementException e) {}
        return exists;
    }    
   
    class Task implements Callable<String> {
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
}
