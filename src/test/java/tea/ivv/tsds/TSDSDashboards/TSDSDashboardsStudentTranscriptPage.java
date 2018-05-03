package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import us.tx.state.tea.seleniumfw.utils.PageUtils;

public class TSDSDashboardsStudentTranscriptPage {
	private WebDriver driver;

	public TSDSDashboardsStudentTranscriptPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public int getStudentTranscriptCount() {
		return driver.findElement(By.id("FallSemester_CoursesDiv")).findElements(By.tagName("tr")).size();
	}
	
	public TSDSDashboardsCampusOverviewPage accessCampusOverview (String schoolname){
		driver.findElement(By.linkText(schoolname)).click();
		PageUtils.waitForNewWindowLoad(driver, "Overview");
		return new TSDSDashboardsCampusOverviewPage(driver);
	}

}
