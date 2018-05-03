package tea.ivv.tsds.TSDSODS;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSDeleteUtilityPage {
	private WebDriver driver;
	
	public TSDSDeleteUtilityPage(WebDriver driver){
		this.driver = driver;
	}
	
	public static void clicknewDeleteRequest(WebDriver driver) {
		driver.findElement(By.linkText("New Delete Request")).click();
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.findElement(By.xpath("//*[@id='content']/table/tbody/tr/td")).getText().equals("New Delete Request");  
            }
            });
	}
	
	public static void delete2018SUMR1LEA(WebDriver driver) throws InterruptedException {
		Actions actions = new Actions(driver);
	    actions.moveToElement(driver.findElement(By.id("btn_toggleText34")));
	    actions.build().perform();
		driver.findElement(By.id("btn_toggleText34")).click();
		WebElement deletetable = driver.findElement(By.id("toggleText34"));
		WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.attributeContains(deletetable, "style", "display: block;"));
        Thread.sleep(1000);
        WebElement leadelete = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[2]/div/div[34]/table/tbody/tr[19]/td[2]/a/img"));
        wait.until(ExpectedConditions.visibilityOf(leadelete));
        leadelete.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id='content']/table/tbody/tr/td"), "Delete PEIMS Summer - LEA"));
        driver.findElement(By.id("form1_comments")).sendKeys("Delete SUMR 2018");
        driver.findElement(By.id("form1_Preview_Delete")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("confirmButton"))));
        driver.findElement(By.name("confirmButton")).click();
        try {
            driver.switchTo().alert().accept();
          } catch (NoAlertPresentException e) {
        	  System.out.println("No Data to Delete");
        }
	}
	
	public TSDSDeleteUtilityPage delete2018FALL1LEA() throws InterruptedException {
		Actions actions = new Actions(driver);
	    actions.moveToElement(driver.findElement(By.id("btn_toggleText28")));
	    actions.build().perform();
		driver.findElement(By.id("btn_toggleText28")).click();
		WebElement deletetable = driver.findElement(By.id("toggleText28"));
		WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.attributeContains(deletetable, "style", "display: block;"));
        Thread.sleep(1000);
        WebElement leadelete = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[2]/div/div[28]/table/tbody/tr[4]/td[2]/a/img"));
        //WebElement leadelete = driver.findElement(By.xpath("//*[@id='tableDeletes']/tbody/tr[3]/td[2]/a/img"));
        wait.until(ExpectedConditions.visibilityOf(leadelete));
        leadelete.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id='content']/table/tbody/tr/td"), "Delete PEIMS Fall - LEA"));
        driver.findElement(By.id("form1_comments")).sendKeys("Delete Fall 2018");
        driver.findElement(By.id("form1_Preview_Delete")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("confirmButton"))));
        driver.findElement(By.name("confirmButton")).click();
        try {
            driver.switchTo().alert().accept();
          } catch (NoAlertPresentException e) {
        	  System.out.println("No Data to Delete");
        }
		return new TSDSDeleteUtilityPage(driver);
	}
	
	public static void exitDelete(WebDriver driver) {
		driver.findElement(By.linkText("Exit")).click();
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : d.getWindowHandles()) {
                    d.switchTo().window(winHandle);
                    if(d.getTitle().equals("eScholar Data Manager")) {
                        break;
                    }
                }
                return d.getTitle().contains("eScholar Data Manager");   
            }
            });
	}
	

}
