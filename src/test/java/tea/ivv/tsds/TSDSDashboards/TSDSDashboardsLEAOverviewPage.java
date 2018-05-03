package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSDashboardsLEAOverviewPage {
	private WebDriver driver;
	
	public TSDSDashboardsLEAOverviewPage(WebDriver driver){
		this.driver = driver;
	}
	
	public TSDSDashboardsDistrictInfoPage accessDistrictInformation() {
		driver.findElement(By.linkText("District Information")).click();
		waitforPageSwitch("District Information");
		return new TSDSDashboardsDistrictInfoPage(driver);
	}
	
	public TSDSDashboardsStateAssessmentsPage accessStateAssessments() {
		driver.findElement(By.linkText("State Assessments")).click();
		waitforPageSwitch("State Assessments");
		return new TSDSDashboardsStateAssessmentsPage(driver);
	}
	
	public TSDSDashboardsAttendanceandDisciplinePage accessAttendanceandDiscipline() {
		driver.findElement(By.linkText("Attendance and Discipline")).click();
		waitforPageSwitch("Attendance and Discipline");
		return new TSDSDashboardsAttendanceandDisciplinePage(driver);
	}
	
	private void waitforPageSwitch(final String pagename) {
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().contains("ISD - " + pagename)) {
                        break;
                    }
                }
                return d.getTitle().contains("ISD - " + pagename);
            }
        });
		
	}

}
