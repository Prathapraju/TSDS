package tea.ivv.tsds.TSDSPEIMS;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


class TSDSViewReportsParametersPage {

    private WebDriver driver;
    public TSDSViewReportsParametersPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void executeReport(String rptType, String rptNum) throws InterruptedException {
        WebElement orgElement;
        Select orgSelect;
        WebElement stutype;
        Select stutypeSelect;
        WebElement snapshotElement;
        Select snapshotSelect;
        WebElement reporttypeElement;
        Select reporttypeSelect;
        System.out.println("Running " + rptNum);
        switch(rptNum) {
            case "PDM1-120-004":
                stutype = driver.findElement(By.id("param3"));
                stutypeSelect = new Select(stutype);
                stutypeSelect.selectByValue("All Reports");
                break;
            case "PDM1-120-006":
                snapshotElement = driver.findElement(By.id("param3"));
                snapshotSelect = new Select(snapshotElement);
                snapshotSelect.selectByValue("ALL Reports");
                break;
            case "PDM1-231-001":
                reporttypeElement = driver.findElement(By.id("param1"));
                reporttypeSelect = new Select(reporttypeElement);
                reporttypeSelect.selectByValue("All Reports");
                break;
        }
        if (rptType == "LEAbyCampus") {
            orgElement = driver.findElement(By.id("param1"));
            orgSelect = new Select(orgElement);
            orgSelect.selectByValue("By Campus");
        }
        Thread.sleep(1000);
        driver.findElement(By.id("runReport")).sendKeys(Keys.ENTER);
    }
    
    public WebElement waitForElement(By by, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver,timeout);
      WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
      return element;
   }
}
