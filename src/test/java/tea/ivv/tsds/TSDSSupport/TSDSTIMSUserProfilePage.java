package tea.ivv.tsds.TSDSSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSTIMSUserProfilePage {
	private WebDriver driver;
	
	public TSDSTIMSUserProfilePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean verifyAccess() {
		boolean returnvalue;
		String groups = driver.findElement(By.xpath("//*[@id='details-profile-fragment']/div[2]/ul/li/dl[7]/dd")).getText();
		System.out.println(groups);
		returnvalue = groups.contains("221901") && groups.contains("IMTLEA") && groups.contains("jira-users");
		return returnvalue;
	}
	
	public TSDSTIMSSupportPage returnToTIMSHome() {
		driver.findElement(By.xpath("//*[@id='logo']/a/img")).click();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().contains("System Dashboard - TSDS")) {
                        break;
                    }
                }
                return d.getTitle().contains("System Dashboard - TSDS");
            }
        });
        
        return new TSDSTIMSSupportPage(driver);
	}

}
