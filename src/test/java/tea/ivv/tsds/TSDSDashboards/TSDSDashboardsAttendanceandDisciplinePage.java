package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsAttendanceandDisciplinePage {
	private WebDriver driver;
	
	public TSDSDashboardsAttendanceandDisciplinePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver campusListforAverage() {
		driver.findElement(By.id("mainmoreActions-1346")).click();
		ElementUtils.waitForElement(driver, By.id("moreActions-1346CampusList"), 20).click();
		return driver;
	}
	
	public String getCampusName() {
		return ElementUtils.waitForElement(driver, By.xpath("//*[@id='Metric1346-EdFiGrid-fixed-data-table']/tbody/tr[1]/td[2]/div/a"), 20).getText();
	}
	
	public TSDSDashboardsCampusAttendanceandDisciplinePage accessCampus(String CampusName) {
		driver.findElement(By.linkText(CampusName)).click();
		PageUtils.waitForNewWindowLoad(driver, "Attendance and Discipline");
		return new TSDSDashboardsCampusAttendanceandDisciplinePage(driver);
	}

}
