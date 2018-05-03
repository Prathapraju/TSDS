package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import us.tx.state.tea.seleniumfw.utils.PageUtils;

public class TSDSDashboardsCampusOverviewPage {
	private WebDriver driver;
	
	public TSDSDashboardsCampusOverviewPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSDashboardsCampusInformationPage accessCampusInformation() {
		driver.findElement(By.linkText("Campus Information")).click();
		PageUtils.waitForNewWindowLoad(driver, "Campus Information");
		return new TSDSDashboardsCampusInformationPage(driver);
	}

}
