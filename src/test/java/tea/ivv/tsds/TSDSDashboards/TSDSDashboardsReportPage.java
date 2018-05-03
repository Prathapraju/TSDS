package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import us.tx.state.tea.seleniumfw.utils.*;

public class TSDSDashboardsReportPage {
	private WebDriver driver;

	public TSDSDashboardsReportPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSDashboardsReportPage runReport(String reportnum) {
		System.out.println(driver.getTitle());
		driver.switchTo().frame(0);
		driver.switchTo().frame("widgetIframe_id_19");
		driver.switchTo().frame("rbottom");
		driver.findElement(By.partialLinkText(reportnum)).click();
		return this;
	}
	
	public String checkReport() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(ElementUtils.waitForElement(driver, By.id("openDocChildFrame"), 180));
		driver.switchTo().frame(ElementUtils.waitForElement(driver, By.id("widgetIframe_id_21"), 180));
		try {
			driver.switchTo().frame(ElementUtils.waitForElement(driver, By.id("reportPanel"), 180));
		} catch (Exception ex) {
			driver.switchTo().frame(ElementUtils.waitForElement(driver, By.id("reportPanel"), 180));
		}
		try {
			driver.switchTo().frame(ElementUtils.waitForElement(driver, By.cssSelector("[id$=_page_1_iframe]"), 180));
		} catch (Exception x) {
			driver.switchTo().frame(ElementUtils.waitForElement(driver, By.cssSelector("[id$=_page_1_iframe]"), 180));
		}
		return driver.findElement(By.xpath("//*[@id='Text34']/p/span/span")).getText();
	}
	
	public String getReportHeader() {
    	driver = driver.switchTo().frame(5);
    	String reportHeader = driver.findElement(By.xpath("//*[@id='Text4']/p/span/span")).getText();
        driver = driver.switchTo().defaultContent();
        return reportHeader;
    }
}
