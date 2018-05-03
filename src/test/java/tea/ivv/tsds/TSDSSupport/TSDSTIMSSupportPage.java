package tea.ivv.tsds.TSDSSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSTIMSSupportPage {
	private WebDriver driver;
	
	public TSDSTIMSSupportPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSTIMSIssueNavigator searchforIssues() {
		Actions action = new Actions(driver);
		WebElement validate = driver.findElement(By.id("find_link"));
		action.moveToElement(validate).build().perform();
		driver.findElement(By.id("find_link")).click();
		driver.findElement(By.linkText("Search for issues")).click();
        return new TSDSTIMSIssueNavigator(driver);
	}
	
	public TSDSTIMSIssueNavigator accessMyIssues() {
		Actions action = new Actions(driver);
		WebElement validate = driver.findElement(By.id("find_link"));
		action.moveToElement(validate).build().perform();
		driver.findElement(By.id("find_link")).click();
		driver.findElement(By.linkText("My open issues")).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().contains("Issue Navigator")) {
                        break;
                    }
                }
                return d.getTitle().contains("Issue Navigator");
            }
        });
        
        return new TSDSTIMSIssueNavigator(driver);
	}
	
	public TSDSSupportPage exitTIMS() {
		driver.findElement(By.id("header-details-user-fullname")).click();
		driver.findElement(By.id("log_out")).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().equals("TSDS: Support")) {
                        break;
                    }
                }
                return d.getTitle().contentEquals("TSDS: Support");
            }
        });
        
        return new TSDSSupportPage(driver);
	}
	
	public TSDSTIMSUserProfilePage accessProfile() {
		driver.findElement(By.id("header-details-user-fullname")).click();
		driver.findElement(By.id("view_profile")).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().contains("User Profile:")) {
                        break;
                    }
                }
                return d.getTitle().contains("User Profile:");
            }
        });
        
        return new TSDSTIMSUserProfilePage(driver);
	}

}
