package tea.ivv.tsds.TSDSSupport;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class TSDSCreateIncidentPage {
	private WebDriver driver;
	private String timssub;
	private String tsdsincidentnumber;
	
	public TSDSCreateIncidentPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String submitIncident() {
		driver.findElement(By.id("phoneNumber")).sendKeys("512-936-3515");
		Select issueSelect = new Select (driver.findElement(By.id("issueTypeId")));
		issueSelect.selectByValue("Problem");
		Select systemSelect = new Select (driver.findElement(By.id("subSystem")));
		systemSelect.selectByValue("TIMS");
		timssub = "Test" + String.valueOf(ThreadLocalRandom.current().nextInt(100, 999));
		driver.findElement(By.id("summary")).click();
		driver.findElement(By.id("summary")).clear();
		driver.findElement(By.id("summary")).sendKeys(timssub);
		driver.findElement(By.id("detaileddescription")).click();
		driver.findElement(By.id("detaileddescription")).clear();
		driver.findElement(By.id("detaileddescription")).sendKeys("This is my test issue to enter.");
		driver.findElement(By.id("submitButton")).click();
		return timssub;
	}
	
	public boolean verifyIncidentLoaded() {
		return driver.findElement(By.tagName("h1")).getText().equals("TSDS Support: Create an Incident");
	}
	
	public TSDSSupportPage cancelIncident(){
		driver.findElement(By.id("cancelButton")).click();
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
	
	public String verifyIncident() {
		String message = driver.findElement(By.className("errorHeader")).getText();
		tsdsincidentnumber = message.substring(message.indexOf("#") + 1, message.indexOf("#") + 10);
		Assert.assertTrue(message.endsWith("Successfully Created."), "Message Recieved : " + message);
		return tsdsincidentnumber;
	}
	
	public TSDSSupportPage exitCreateIncident() {
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
	    driver.findElement(By.linkText("Exit")).click();
	    driver.switchTo().window(tabs2.get(0));
	    return new TSDSSupportPage(driver);
	}

}
