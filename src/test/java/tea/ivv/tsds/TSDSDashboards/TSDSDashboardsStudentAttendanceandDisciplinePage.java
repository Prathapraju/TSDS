package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsStudentAttendanceandDisciplinePage {
	private WebDriver driver;
	
	public TSDSDashboardsStudentAttendanceandDisciplinePage (WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver accessStudentAttendanceRateChart() {
		driver.findElement(By.id("mainmoreActions-2")).click();
		ElementUtils.waitForElement(driver, By.id("moreActions-2AttendanceRateChart"), 20).click();
		return driver;
	}
	
	public boolean verifyattendanceRateChart() {
		return ElementUtils.waitForElement(driver, By.id("0_Chart_"), 20).isDisplayed();
	}
	
	public TSDSDashboardsStudentTranscriptPage accessStudentTranscript() {
		driver.findElement(By.linkText("Transcript")).click();
		PageUtils.waitForNewWindowLoad(driver, "Transcript");
		return new TSDSDashboardsStudentTranscriptPage(driver);
	}

}
