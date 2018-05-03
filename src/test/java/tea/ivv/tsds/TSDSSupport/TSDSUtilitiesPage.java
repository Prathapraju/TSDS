package tea.ivv.tsds.TSDSSupport;

import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class TSDSUtilitiesPage {
	private WebDriver driver;
	
	public TSDSUtilitiesPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean verifyValidationTool() {
		boolean returnvalue = false;
		try {
			returnvalue = driver.findElement(By.id("EasyBtnVTool")).isDisplayed();
		} catch (NoSuchElementException e) {
			returnvalue = false;
		}
		return returnvalue;
	}
	
	public boolean verifyDTU() {
		boolean returnvalue = false;
		try {
			returnvalue = driver.findElement(By.id("EasyBtnDtu")).isDisplayed();
		} catch (NoSuchElementException e) {
			returnvalue = false;
		}
		return returnvalue;
	}
	
	public boolean downloadValidationTool() throws InterruptedException, ExecutionException {
		boolean returnvalue = false;
		int initialcount = 0;
		File f1 = new File(TSDSSupportTest.Path_FileDownload);
        initialcount = f1.listFiles().length;
        if (f1.listFiles().length > 0) {
        	for(File ifile: f1.listFiles()) {
        		ifile.delete();
        	}
        	initialcount = 0;
        }
        driver.findElement(By.id("EasyBtnVTool")).click();
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
        File f2 = new File(TSDSSupportTest.Path_FileDownload);
        File[] exelist = f2.listFiles(exeFilter);
        if (exelist[0].canExecute() && exelist[0].getName().equals("ConverterValidatorTool.exe")) {
        	returnvalue = true;
        	exelist[0].delete();
        }
		return returnvalue;
	}
	
	public boolean downloadDTU() throws InterruptedException, ExecutionException {
		boolean returnvalue = false;
		int initialcount = 0;
		File f1 = new File(TSDSSupportTest.Path_FileDownload);
        initialcount = f1.listFiles().length;
        if (f1.listFiles().length > 0) {
        	for(File ifile: f1.listFiles()) {
        		ifile.delete();
        	}
        	initialcount = 0;
        }
        driver.findElement(By.id("EasyBtnDtu")).click();
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
        File f2 = new File(TSDSSupportTest.Path_FileDownload);
        File[] exelist = f2.listFiles(exeFilter);
        if (exelist[0].canExecute() && exelist[0].getName().equals("dtu_installer.exe")) {
        	returnvalue = true;
        	exelist[0].delete();
        }
		return returnvalue;
	}
	
    class pdfTask implements Callable<String> {
	    @Override
	    public String call() throws Exception {
	    	File f2 = new File(TSDSSupportTest.Path_FileDownload);
	        File[] pdflist = f2.listFiles(exeFilter);
	        while (pdflist.length == 0){
	            pdflist = f2.listFiles(exeFilter);
	        }
	        return "Ready!";
	    }
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

}
