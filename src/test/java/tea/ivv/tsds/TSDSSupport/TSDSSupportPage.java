package tea.ivv.tsds.TSDSSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSSupportPage {
	private WebDriver driver;
	
	public TSDSSupportPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean verifyAccess(String userid) {
		boolean returnvalue = false;
		switch(userid) {
		case "TSDSRegression.UIDDistrictMany":
		case "TSDSRegression.UIDSuper":
		case "TSDSRegression.UIDCampusOne":
		case "TSDSRegression.Search":
			returnvalue = driver.findElement(By.id("EasyBtnSearch")).isDisplayed() && driver.findElement(By.id("EasyBtnCreate")).isDisplayed() && driver.findElement(By.id("EasyBtnView")).isDisplayed();
			break;
		case "TSDSRegression.UIDStAdmin":
			returnvalue = driver.findElement(By.id("EasyBtnTIMS")).isDisplayed();
			break;
		}
		return returnvalue;
	}
	
	public TSDSCreateIncidentPage createIncident() {
		driver.findElement(By.id("EasyBtnCreate")).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().equals("Create")) {
                        break;
                    }
                }
                return d.getTitle().contentEquals("Create");
            }
        });
        
        return new TSDSCreateIncidentPage(driver);
	}
	
	public TSDSViewIncidentPage viewIncidents() {
		driver.findElement(By.id("EasyBtnView")).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().equals("View")) {
                        break;
                    }
                }
                return d.getTitle().contentEquals("View");
            }
        });
        
        return new TSDSViewIncidentPage(driver);
	}
	
	public TSDSTIMSSupportPage accessTIMS(){
		driver.findElement(By.id("EasyBtnTIMS")).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().contains("System Dashboard")) {
                        break;
                    }
                }
                return d.getTitle().contains("System Dashboard");
            }
        });
        
        return new TSDSTIMSSupportPage(driver);
	}	
	
	public TSDSSearchKnowledgeBasePage searchKnowledgeBase() {
		driver.findElement(By.id("EasyBtnSearch")).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().equals("Search")) {
                        break;
                    }
                }
                return d.getTitle().contentEquals("Search");
            }
        });
        
        return new TSDSSearchKnowledgeBasePage(driver);
	}

}
