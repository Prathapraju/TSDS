package tea.ivv.tsds.TSDSSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tea.ivv.tsds.TSDSPEIMS.TSDSDisplayKnowledgeBaseResultsPage;

public class TSDSSearchKnowledgeBasePage {
	private WebDriver driver;
	
	public TSDSSearchKnowledgeBasePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSSearchKnowledgeBasePage searchKnowledgeBase(String searchstring) {
		driver.findElement(By.id("searchIssueInputId")).click();
		driver.findElement(By.id("searchIssueInputId")).clear();
		driver.findElement(By.id("searchIssueInputId")).sendKeys(searchstring);
		driver.findElement(By.id("searchButtonId")).click();
		return this;
	}
	
	public int verifyResultCount() {
		return waitForElement(By.id("issueListTableId"), 20).findElements(By.tagName("tr")).size();
	}
	
	public TSDSDisplayKnowledgeBaseResultsPage accessSearchResults(String linkname) {
		driver.findElement(By.partialLinkText(linkname)).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().equals("Display Search")) {
                        break;
                    }
                }
                return d.getTitle().contentEquals("Display Search");
            }
        });
        
        return new TSDSDisplayKnowledgeBaseResultsPage(driver);
		
	}
	
	public TSDSSupportPage exitKnowledgeBase() {
		driver.findElement(By.linkText("Exit")).click();
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
	
	
	public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
     }
	

}
