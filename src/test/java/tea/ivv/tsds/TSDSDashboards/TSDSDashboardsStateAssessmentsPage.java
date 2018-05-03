package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsStateAssessmentsPage {
	private WebDriver driver;
	
	public TSDSDashboardsStateAssessmentsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver clickSTAARReadingCampusList() {
		driver.findElement(By.xpath("//*[@id='mainmoreActions-11672']/i")).click();
		ElementUtils.waitForElement(driver, By.id("moreActions-11672CampusList"), 20).click();
		return driver;
	}
	
	public String getCampusName(){
		return ElementUtils.waitForElement(driver, By.xpath("//*[@id='Metric11672-EdFiGrid-fixed-data-table']/tbody/tr[1]/td[2]/div/a"), 20).getText();
	}
	
	public TSDSDashboardsCampusStateAssessmentsPage accessCampus(String CampusName) {
		ElementUtils.waitForElement(driver, By.linkText(CampusName), 20).click();
		PageUtils.waitForNewWindowLoad(driver, "State Assessments");
		return new TSDSDashboardsCampusStateAssessmentsPage(driver);
		
	}

}
