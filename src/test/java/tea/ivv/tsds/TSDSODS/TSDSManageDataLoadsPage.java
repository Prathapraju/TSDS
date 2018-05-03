package tea.ivv.tsds.TSDSODS;

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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSManageDataLoadsPage {
	private WebDriver driver;
	
	
	public TSDSManageDataLoadsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSDeleteUtilityPage deleteUtility () {
		driver.findElement(By.linkText("Delete Utility")).click();
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().contains("eScholar Delete Utility")) {
                        break;
                    }
                }
                return driver.getTitle().contains("eScholar Delete Utility");   
            }
            });
    	return new TSDSDeleteUtilityPage(driver);
	}
	
	public TSDSManageDataLoadsPage interchangeUpload(String collection) {
		driver.findElement(By.linkText("Interchange Upload")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("dataCollectionId"))));
        new Select(driver.findElement(By.id("dataCollectionId"))).selectByVisibleText(collection);
		return new TSDSManageDataLoadsPage(driver);
	}
	
	public TSDSManageDataLoadsPage uploadFiles(String collection){
		File folder = new File("\\\\tea4dpfs2.tea.state.tx.us\\ACOFM\\ProjectMgmt\\QA\\Cody\\TSDS\\" + collection.substring(0,  9));
		int i = 0;
		for (File file: folder.listFiles()) {
			System.out.println(file.getAbsolutePath());
			driver.findElement(By.id("files[" + i + "]")).sendKeys(file.getAbsolutePath());
			i++;
		}
		driver.findElement(By.name("submitCart")).click();
		return new TSDSManageDataLoadsPage(driver);
	}
	
	public TSDSManageDataLoadsPage deleteFiles() {
		driver.findElement(By.linkText("File Manager")).click();
		if (IsPresentandDisplayed(driver, By.className("selectedObjects"))) {
			driver.findElement(By.name("checkall")).click();
			driver.findElement(By.xpath("//*[@id='content']/form/table[3]/tbody/tr[2]/td[1]/input[2]")).click();
			driver.findElement(By.name("deleteFile")).click();
		}
		return this;
	}
	
	public TSDSManageDataLoadsPage submittoBatch() throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.name("Refresh")).click();
		driver.findElement(By.name("checkall")).click();
		driver.findElement(By.name("submitCart")).click();
		waitForElement(By.xpath("//*[@id='content']/form/table[3]/tbody/tr[2]/td[1]/input[2]"), 20).click();
		waitForElement(By.name("viewCart"),20).click();
		return new TSDSManageDataLoadsPage(driver);
	}
	
	public TSDSManageDataLoadsPage monitorBatch() throws InterruptedException {
		String status = driver.findElement(By.xpath("//*[@id='content']/form/table[5]/tbody/tr[2]/td[6]")).getText();
		while (!status.equals("Complete") && !status.equals("Failed")) {
			Thread.sleep(5000);
			driver.findElement(By.name("Refresh")).click();
			status = driver.findElement(By.xpath("//*[@id='content']/form/table[5]/tbody/tr[2]/td[6]")).getText();
		}
		return new TSDSManageDataLoadsPage(driver);
	}
	
	public boolean verifyBatch() {
		return driver.findElement(By.xpath("//*[@id='content']/form/table[5]/tbody/tr[2]/td[6]")).getText().equals("Complete");
	}
	
	public TSDSManageDataLoadsPage runAdminReports() {
		driver.findElement(By.linkText("Admin Reports")).click();
		return new TSDSManageDataLoadsPage(driver);
	}
	
	public TSDSManageDataLoadsPage runDistrictSubmissionsReport(String collname) throws IOException {
		waitForElement(By.linkText("District Submissions"), 20).click();
		WebElement selectelement = waitForElement(By.id("reportParameters[0].value"), 20);
		Select selectdrop = new Select(selectelement);
		selectdrop.selectByVisibleText(collname);
		File f1 = new File(TSDSODSTests.Path_FileDownload);
        int initialcount = f1.listFiles().length;
        if (f1.listFiles().length > 0) {
        	for(File ifile: f1.listFiles()) {
        		ifile.delete();
        	}
        	initialcount = 0;
        }
		driver.findElement(By.xpath("//*[@id='edmRNavUserSection']/form/input[3]")).click();
        File f2 = new File(TSDSODSTests.Path_FileDownload);
        File[] pdflist = f2.listFiles(pdfFilter);
        long startTime = System.currentTimeMillis();
        while ((pdflist.length == initialcount)||(System.currentTimeMillis()-startTime)<10000){
            pdflist = f2.listFiles(pdfFilter);
        }
        PDDocument tsdspdf = PDFUtils.loadDoc(TSDSODSTests.Path_FileDownload + "districtsubmissionsreport.pdf");
        System.out.println("PDF Loaded");
        String pdftext = PDFUtils.getText(tsdspdf);
        try {
        	tsdspdf.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
        Assert.assertTrue(pdftext.contains("District Submissions"));
        return new TSDSManageDataLoadsPage(driver);
	}
	
	public boolean downloadDTU() throws InterruptedException, ExecutionException {
		System.out.println("Starting DTU Download Test");
		boolean returnvalue = false;
		int initialcount;
		File f1 = new File(TSDSODSTests.Path_FileDownload);
        initialcount = f1.listFiles().length;
        if (f1.listFiles().length > 0) {
        	for(File ifile: f1.listFiles()) {
        		ifile.delete();
        	}
        	initialcount = 0;
        }
        driver.findElement(By.linkText("DTU Package")).click();
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
        File f2 = new File(TSDSODSTests.Path_FileDownload);
        File[] exelist = f2.listFiles();
		if (exelist[0].canExecute() && exelist[0].getName().equals("dtu_installer.exe")) {
        	returnvalue = true;
        	exelist[0].delete();
        }
		return returnvalue;
	}
    public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
     }
    
    public static boolean IsPresentandDisplayed(WebDriver driver, By by) {
    	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    	try {
    		driver.findElement(by);
    	} catch (NoSuchElementException e) {
    		return false;
    	}
    	return driver.findElement(by).isDisplayed();
    }
    
    FilenameFilter exeFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
        	String lowercaseName = name.toLowerCase();
        	if (lowercaseName.endsWith(".exe")) {
        		return true;
        	} else {
        		return false;
        	}
        }
    };

    class Task implements Callable<String> {
	    @Override
	    public String call() throws Exception {
	    	File f2 = new File(TSDSODSTests.Path_FileDownload);
	        File[] pdflist = f2.listFiles(exeFilter);
	        while (pdflist.length == 0){
	            pdflist = f2.listFiles(exeFilter);
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
