package tea.ivv.tsds.TSDSUniqueID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSUniqueIDPage {
	
	public static void launchManageUniqueID(WebDriver driver){
		driver.findElement(By.id("EasyBtnUID")).click();
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : d.getWindowHandles()) {
                    d.switchTo().window(winHandle);
                    if(d.getTitle().equals("TSDS Unique ID Application")) {
                        break;
                    }
                }
                return d.getTitle().equals("TSDS Unique ID Application");   
            }
            });
	}
	
	public static void exitTSDS(WebDriver driver) {
		driver.findElement(By.linkText("Exit")).click();
	}

}
