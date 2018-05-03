package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsCampusInformationPage {
	private WebDriver driver;
	
	public TSDSDashboardsCampusInformationPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSDashboardsCampusStudentPage accessStudentsbyGrade() {
		driver.findElement(By.linkText("Students by Grade")).click();
		PageUtils.waitForNewWindowLoad(driver, "All Students");
		return new TSDSDashboardsCampusStudentPage(driver);
	}
	
	public TSDSDashboardsCampusStaffPage accessStaffList() {
		driver.findElement(By.linkText("Staff List")).click();
		PageUtils.waitForNewWindowLoad(driver, "Staff List");
		return new TSDSDashboardsCampusStaffPage(driver);
	}

}
