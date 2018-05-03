package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import us.tx.state.tea.seleniumfw.utils.PageUtils;

public class TSDSDashboardsCampusStudentPage {
	private WebDriver driver;
	
	public TSDSDashboardsCampusStudentPage(WebDriver driver){
		this.driver = driver;
	}
	
	public String getStudentName() {
		return driver.findElement(By.xpath("//*[@id='GradeAllStudents-EdFiGrid-fixed-data-table']/tbody/tr[1]/td[2]/div/div/span[3]/a")).getText();
	}
	
	public TSDSDashboardsStudentOverviewPage accessStudentOverview(String studentname) {
		driver.findElement(By.linkText(studentname)).click();
		PageUtils.waitForNewWindowLoad(driver, "Overview");
		return new TSDSDashboardsStudentOverviewPage(driver);
	}

}
