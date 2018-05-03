package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsCampusStateAssessmentsPage {
	private WebDriver driver;
	
	public TSDSDashboardsCampusStateAssessmentsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver clickSTAARReadingGradeChart(){
		driver.findElement(By.id("mainmoreActions-11567")).click();
		ElementUtils.waitForElement(driver, By.id("moreActions-11567GradeLevelChart"), 20).click();
		return driver;
	}
	
	public boolean verifyChartDisplayed(String orgId) {
		return ElementUtils.waitForElement(driver, By.id("0_Chart_" + orgId + "101_GLC_11567"), 20).isDisplayed();
	}
	
	public TSDSDashboardsLEAOverviewPage clickLEAName(String lea) {
		driver.findElement(By.linkText(lea)).click();
		PageUtils.waitForNewWindowLoad(driver, "Overview");
		return new TSDSDashboardsLEAOverviewPage(driver);
	}

}
