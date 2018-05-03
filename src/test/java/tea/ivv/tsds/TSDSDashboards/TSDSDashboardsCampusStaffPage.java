package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsCampusStaffPage {
	private WebDriver driver;
	
	public TSDSDashboardsCampusStaffPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public int getStaffListCount() {
		return ElementUtils.waitForElement(driver, By.id("StaffList-EdFiGrid-fixed-data-table"), 20).findElements(By.tagName("tr")).size();
	}
	
	public boolean checkSupportTicketLoads() {
		driver.findElement(By.id("buttonFeedback")).click();
		String title = ElementUtils.waitForElement(driver, By.id("ui-id-1"), 20).getText();
		driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/button[2]/span")).click();
		return title.equals("Submit Request");
	}
	
	public TSDSDashboardsPage exitDashboards() {
		ElementUtils.waitForElement(driver, By.xpath("//*[@id='header']/ul/li[5]/a"), 20).click();
		PageUtils.waitForNewWindowLoad(driver, "TSDS: studentGPS Dashboards");
		return new TSDSDashboardsPage(driver);
	}

}
