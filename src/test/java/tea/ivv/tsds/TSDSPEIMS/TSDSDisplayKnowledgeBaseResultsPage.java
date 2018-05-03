package tea.ivv.tsds.TSDSPEIMS;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import tea.ivv.tsds.TSDSSupport.TSDSSearchKnowledgeBasePage;

public class TSDSDisplayKnowledgeBaseResultsPage {
	private WebDriver driver;
	
	public TSDSDisplayKnowledgeBaseResultsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public static String getResultsHeader(WebDriver driver) {
		return driver.findElement(By.tagName("h1")).getText();
	}
	
	public TSDSSearchKnowledgeBasePage exitResults() {
		driver.findElement(By.linkText("Exit")).click();
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
