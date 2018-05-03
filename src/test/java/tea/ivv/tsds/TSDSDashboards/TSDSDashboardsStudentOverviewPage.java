package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsStudentOverviewPage {
	private WebDriver driver;
	
	public TSDSDashboardsStudentOverviewPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSDashboardsStudentAttendanceandDisciplinePage accessStudentAttendanceandDiscipline() {
		driver.findElement(By.linkText("Attendance and Discipline")).click();
		PageUtils.waitForNewWindowLoad(driver, "Attendance and Discipline");
		return new TSDSDashboardsStudentAttendanceandDisciplinePage(driver);
	}

}
