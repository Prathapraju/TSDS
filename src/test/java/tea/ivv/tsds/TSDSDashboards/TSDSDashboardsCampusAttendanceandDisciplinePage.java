package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsCampusAttendanceandDisciplinePage {
	private WebDriver driver;
	
	public TSDSDashboardsCampusAttendanceandDisciplinePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver clickAttendanceHistoricalChart() {
		ElementUtils.waitForElement(driver, By.id("mainmoreActions-1444"), 20).click();
		ElementUtils.waitForElement(driver, By.id("moreActions-1444HistoricalChart"), 20).click();
		return driver;
	}
	
	public String getattendanceHistoricalChartdata() throws InterruptedException {
		String data;
		Thread.sleep(10000);
		data = driver.findElement(By.id("placeHolderForSchoolGoal1444")).getText();
		System.out.println(data);
		return data;
	}
	
	public TSDSDashboardsCampusInformationPage accessCampusInformation() {
		driver.findElement(By.linkText("Campus Information")).click();
		PageUtils.waitForNewWindowLoad(driver, "Campus Information");
		return new TSDSDashboardsCampusInformationPage(driver);
	}

}
