package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import us.tx.state.tea.seleniumfw.utils.PageUtils;

public class TSDSDashboardsPage {
	private WebDriver driver;
	
	public TSDSDashboardsPage(WebDriver driver){
		this.driver = driver;
	}
	
	public TSDSDashboardLEAAdminPage viewstudentGPS(String role) {
		Select roleselect = new Select(driver.findElement(By.id("selectDashboard")));
		roleselect.selectByVisibleText(role);
		driver.findElement(By.id("EasyBtnDashboard")).click();
		PageUtils.waitForNewWindowLoad(driver, "Admin");
		return new TSDSDashboardLEAAdminPage(driver);
	}
	
	public TSDSDashboardsReportPage viewReports() {
		waitForElement(By.id("EasyBtnDashboardReports"), 60).click();
		PageUtils.waitForNewWindowLoad(driver, "LEA Dashboards WorkSpace");
		return new TSDSDashboardsReportPage(driver);
	}
	
    public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
     }

	}
