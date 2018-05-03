package tea.ivv.tsds.TSDSUniqueID;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import tea.ivv.tsds.TSDSSupport.TSDSSupportPage;

public class TSDSManageUniqueIDPage {
	private WebDriver driver;
	
	public TSDSManageUniqueIDPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSManageUniqueIDPage changeDistrict(String leaid) {
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		driver.findElement(By.linkText("Change LEA")).click();
		Select distselect = new Select(driver.findElement(By.id("selectedDistrict")));
		distselect.selectByValue(leaid + "-school-AAA");
		driver.findElement(By.xpath("//*[@id='tab2C']/div[2]/span/input[2]")).click();
		return this;
	}
	
	public TSDSManageUniqueIDPage enterOnline(String campusid, String leaid) {
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeToBe(By.xpath("//*[@id='slidebottom']/div"), "style", "display: block;"));
		driver.findElement(By.linkText("Enter Online")).click();
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys(RandomStringUtils.randomAlphabetic(5));
        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys(RandomStringUtils.randomAlphabetic(8));
        if (!driver.findElement(By.xpath("//select[@id='gender']//option[3]")).isSelected()) {
            driver.findElement(By.xpath("//select[@id='gender']//option[3]")).click();
        }
        if (!driver.findElement(By.xpath("//select[@id='mm']//option[11]")).isSelected()) {
            driver.findElement(By.xpath("//select[@id='mm']//option[11]")).click();
        }
        if (!driver.findElement(By.xpath("//select[@id='dd']//option[11]")).isSelected()) {
            driver.findElement(By.xpath("//select[@id='dd']//option[11]")).click();
        }
        if (!driver.findElement(By.xpath("//select[@id='yyyy']//option[27]")).isSelected()) {
            driver.findElement(By.xpath("//select[@id='yyyy']//option[27]")).click();
        }
        if (!driver.findElement(By.xpath("//select[@id='raceEthnicity']//option[6]")).isSelected()) {
            driver.findElement(By.xpath("//select[@id='raceEthnicity']//option[6]")).click();
        }
        if (!driver.findElement(By.xpath("//select[@id='ethnicityIndicator']//option[2]")).isSelected()) {
            driver.findElement(By.xpath("//select[@id='ethnicityIndicator']//option[2]")).click();
        }
        driver.findElement(By.id("ssn1")).click();
        driver.findElement(By.id("ssn1")).clear();
        driver.findElement(By.id("ssn1")).sendKeys(String.valueOf(ThreadLocalRandom.current().nextInt(111, 999)));
        driver.findElement(By.id("ssn2")).click();
        driver.findElement(By.id("ssn2")).clear();
        driver.findElement(By.id("ssn2")).sendKeys(String.valueOf(ThreadLocalRandom.current().nextInt(11, 99)));
        driver.findElement(By.id("ssn3")).click();
        driver.findElement(By.id("ssn3")).clear();
        driver.findElement(By.id("ssn3")).sendKeys(String.valueOf(ThreadLocalRandom.current().nextInt(1111, 9999)));
        driver.findElement(By.xpath("//div[@class='sub-container']/div[2]/div[7]/table/thead/tr[1]/td")).click();
        if (!driver.findElement(By.xpath("//select[@id='gradeLevel']//option[17]")).isSelected()) {
            driver.findElement(By.xpath("//select[@id='gradeLevel']//option[17]")).click();
        }
        driver.findElement(By.id("schoolCode")).click();
        driver.findElement(By.id("schoolCode")).clear();
        driver.findElement(By.id("schoolCode")).sendKeys(campusid);
        driver.findElement(By.id("districtCode")).click();
        driver.findElement(By.id("districtCode")).clear();
        driver.findElement(By.id("districtCode")).sendKeys(leaid);
        driver.findElement(By.id("schoolYear")).click();
        driver.findElement(By.id("schoolYear")).clear();
        driver.findElement(By.id("schoolYear")).sendKeys("2014");
        driver.findElement(By.id("localStudentID")).click();
        driver.findElement(By.id("localStudentID")).clear();
        driver.findElement(By.id("localStudentID")).sendKeys(String.valueOf(ThreadLocalRandom.current().nextInt(111111, 999999)));
        driver.findElement(By.xpath("/html/body/div/form/div[2]/div/div[2]/div[6]/span[2]/input[2]")).click();
        WebElement tableelement = waitForElement(By.xpath("/html/body/div/div[6]/div/table"), 20);
        List<WebElement> tablerows = tableelement.findElements(By.tagName("tr"));
        String batchid = tablerows.get(1).findElements(By.tagName("td")).get(1).getText();
        driver.findElement(By.linkText(batchid)).click();
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(2));
	    Assert.assertTrue(driver.findElement(By.xpath("//*[@id='tab1C']/div[1]/div/table/tbody/tr/th")).getText().contains("New ID Assigned"));
	    driver.findElement(By.xpath("//*[@id='tab1C']/div[2]/input")).click();
	    driver.switchTo().window(tabs2.get(1));
	    return this;
	}
	
	public TSDSManageUniqueIDPage searchPerson(String fname, String lname) {
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeToBe(By.xpath("//*[@id='slidebottom']/div"), "style", "display: block;"));
		driver.findElement(By.linkText("Person")).click();
		waitForElement(By.id("student_first_name"), 20).sendKeys(fname);
		driver.findElement(By.id("student_last_name")).sendKeys(lname);
		driver.findElement(By.xpath("//*[@id='tabsimpleC']/form/div[2]/span/input[2]")).click();
		return this;
	}
	
	public int personSearchResultCount() {
		WebElement tableid = waitForElement(By.xpath("//*[@id='searchResults']/table"), 20);
		return tableid.findElements(By.tagName("tr")).size();
	}
	
	public TSDSManageUniqueIDPage backHome() {
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeToBe(By.xpath("//*[@id='slidebottom']/div"), "style", "display: block;"));
		driver.findElement(By.linkText("<< Back to Home")).click();
		return this;
	}
	
	public TSDSManageUniqueIDPage accessReports() {
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeToBe(By.xpath("//*[@id='slidebottom']/div"), "style", "display: block;"));
		driver.findElement(By.linkText("Reports")).click();
		return this;
	}
	
	public TSDSManageUniqueIDPage runAliasIDReport() {
		waitForElement(By.xpath("/html/body/div/div[6]/div/table/tbody/tr[1]/td[2]/input[2]"), 20).click();
		return this;
	}
	
	public int verifyAliasReportCount() {
		WebElement tableid = waitForElement(By.className("table1"), 20);
		return tableid.findElements(By.tagName("tr")).size();
	}
	
	public TSDSManageUniqueIDPage writeAccess(String filename) throws IOException {
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeToBe(By.xpath("//*[@id='slidebottom']/div"), "style", "display: block;"));
		WebElement menuelement = driver.findElement(By.xpath("//*[@id='slidebottom']/div"));
		List<WebElement> menulist = menuelement.findElements(By.tagName("li"));
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);
			for(WebElement item:menulist)
			{
				bw.write(item.getText());
			}
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		return this;
	}
	
	public TSDSManageUniqueIDPage VerifyAccess(String filename) throws IOException {
		File tempfile = new File(filename);
		FileWriter tempfw = null;
		BufferedWriter tempbw = null;
		FileReader linkfr = null;
        BufferedReader linkbr = null;
        FileReader tempfr = null;
        BufferedReader tempbr = null;
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeToBe(By.xpath("//*[@id='slidebottom']/div"), "style", "display: block;"));
		WebElement menuelement = driver.findElement(By.xpath("//*[@id='slidebottom']/div"));
		List<WebElement> menulist = menuelement.findElements(By.tagName("li"));
		try {
			tempfw = new FileWriter(TSDSUniqueIDTest.basePath + tempfile.getName());
			tempbw = new BufferedWriter(tempfw);
			for(WebElement item:menulist) {
				tempbw.write(item.getText());
				//tempbw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (tempbw != null)
					tempbw.close();
				if (tempfw != null)
					tempfw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			linkfr = new FileReader(filename);
			linkbr = new BufferedReader(linkfr);
			tempfr = new FileReader(TSDSUniqueIDTest.basePath + tempfile.getName());
			tempbr = new BufferedReader(tempfr);
			String str;
			String tempstr;
			while((str=linkbr.readLine())!=null){
				tempstr = tempbr.readLine();
				Assert.assertTrue(tempstr.equals(str), "Received " + tempstr + " expected " + str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (linkfr != null)
					linkfr.close();
				if (linkbr != null)
					linkbr.close();
				if (tempfr != null)
					tempfr.close();
				if (tempbr != null)
					tempbr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File deletefile = new File(TSDSUniqueIDTest.basePath + tempfile.getName());
		deletefile.delete();
		waitForElement(By.xpath("//*[@id='slidemarginleft']/img"), 20).click();
		return this;
	}
	
	public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
     }

}
