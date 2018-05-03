package tea.ivv.tsds.TSDSSupport;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSViewIncidentPage {
	private WebDriver driver;
	private final String datepattern = "yyyy-MM-dd";
	String submitdate = CreateDate.newDate(datepattern);
	
	public TSDSViewIncidentPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSViewIncidentPage verifyIncident(String tsdsincidentnumber, String timssub) {
		WebElement incidenttableid = driver.findElement(By.id("viewIncidentTableId"));
		List<WebElement> incidentrows = incidenttableid.findElements(By.tagName("tr"));
		WebElement createdrow = incidentrows.get(1);
		List<WebElement> incidentcols = createdrow.findElements(By.tagName("td"));
		Assert.assertTrue(incidentcols.get(0).getText().equals(tsdsincidentnumber), "Displayed Incident number is " + incidentcols.get(0).getText() + " Created Incident was " + tsdsincidentnumber);
		Assert.assertTrue(incidentcols.get(1).getText().equals(timssub), "Displayed Short Description is " + incidentcols.get(1).getText() + " Created Short Description was " + timssub);
		Assert.assertTrue(incidentcols.get(2).getText().equals("Level 3 Incoming"), "Displayed status was " + incidentcols.get(2).getText() + " instead of Level 3 Incoming");
		Assert.assertTrue(incidentcols.get(3).getText().contains(submitdate), "The Displayed Submitted date is " + incidentcols.get(3).getText() + " but should be " + submitdate);
		return this;
	}
	
	public TSDSSupportPage exitViewIncident() {
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

}
