package tea.ivv.tsds.TSDSSupport;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSTIMSIssueNavigator {
	private WebDriver driver;
	
	public TSDSTIMSIssueNavigator (WebDriver driver) {
		this.driver = driver;
	}

	public TSDSTIMSIssueNavigator issuesByAssignee() {
		String currentassignee = driver.findElement(By.id("fieldassignee")).getText();
		if (currentassignee.contains("Current User")) {
			driver.findElement(By.id("fieldassignee")).click();
			WebElement cusercheckbox = driver.findElement(By.xpath("//*[@id='assignee-suggestions']/div/ul[1]/li/label/input"));
			boolean cvalue = isAttribtuePresent(cusercheckbox, "checked");
			if (cvalue) {
				driver.findElement(By.xpath("//*[@id='assignee-suggestions']/div/ul[1]/li/label/input")).click();
			}
		}
		return this;
	}
	
	public String clickFirstIssue() {
		String issuetitle = null;
		List<WebElement> issuelist = ElementUtils.waitForElement(driver, By.className("issue-list"), 30).findElements(By.tagName("li"));
		for (WebElement issue: issuelist) {
			if (issue.getText().contains("TSDS")) {
				issuetitle = issue.getText();
				issue.findElement(By.tagName("a")).click();
				break;
			}
		}
		return issuetitle;
	}
	
	public TSDSTIMSSupportPage returnToTIMSHome() {
		driver.findElement(By.xpath("//*[@id='logo']/a/img")).click();
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
	
	public String getIssueTitle() {
		PageUtils.waitForPageToLoad(driver);
		return ElementUtils.waitForElement(driver,By.id("summary-val"), 20).getText();
	}
	
	public boolean verifyIssueDisplayed(String tsdssub) {
		WebElement issuelist = ElementUtils.waitForElement(driver, By.className("issue-list"), 30);
		issuelist.findElements(By.tagName("li")).get(0).click();
		return ElementUtils.waitForElement(driver, By.id("summary-val"), 20).getText().equals(tsdssub);
	}
	
	private boolean isAttribtuePresent(WebElement element, String attribute) {
	    Boolean result = false;
	    try {
	        String value = element.getAttribute(attribute);
	        if (value != null){
	            result = true;
	        }
	    } catch (Exception e) {}

	    return result;
	}
}
