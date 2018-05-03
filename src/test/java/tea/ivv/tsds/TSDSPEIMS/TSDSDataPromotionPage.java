package tea.ivv.tsds.TSDSPEIMS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSDataPromotionPage {
	private WebDriver driver;
	
	public TSDSDataPromotionPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSDataPromotionPage setUpParams(String acadYr, String collectionId, String submissionId) throws InterruptedException {
		waitForPageLoad("Promote Loaded Data");
        new Select(driver.findElement(By.id("hdrAcadYearLst"))).selectByVisibleText(acadYr);
        new Select(driver.findElement(By.id("hdrCollectionsLst"))).selectByVisibleText(collectionId);
        new Select(driver.findElement(By.id("hdrSubmissionsLst"))).selectByVisibleText(submissionId);
        Thread.sleep(1000);
        waitForElement(By.id("goButton"), 1000).click();
        //driver.findElement(By.id("goButton")).click();
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("Promote Loaded Data")) {
                        break;
                    }
                }
                return driver.getTitle().equals("Promote Loaded Data");   
            }
            });
        return this;
    }
	
	public int promotespecificCatandSubCat(String cat, String subcat) {
		System.out.println("Entering Promote Loaded Data");
		WebElement catElement = driver.findElement(By.id("categoriesListId"));
		Select catSelect = new Select(catElement);
		int size = catSelect.getOptions().size();
		catSelect.selectByVisibleText(cat);
		WebElement catSelectedElement = driver.findElement(By.id("subCategoryList"));
		Select catSelected = new Select(catSelectedElement);
		waitUntilSelectOptionsPopulated(catSelected);
		catSelected.selectByVisibleText(subcat);
		driver.findElement(By.id("subCategoryList-add-to-target")).click();
		WebElement subcatSelectedElement = driver.findElement(By.id("subCategoryList-target"));
		Select subcatSelected = new Select(subcatSelectedElement);
		waitUntilSelectOptionsPopulated(subcatSelected);
		return size;
	}
	
	public TSDSConfirmDataPage submitPromotion() {
		driver.findElement(By.id("jobNameId")).sendKeys("TSDS Promotion");
		driver.findElement(By.id("submitButtonId")).click();
		return new TSDSConfirmDataPage(driver);
	}

	public int promoteAllCategories() {
		System.out.println("Entering Promote Loaded Data");
		WebElement catElement = driver.findElement(By.id("categoriesListId"));
		Select catSelect = new Select(catElement);
		int size = catSelect.getOptions().size();
		catSelect.selectByValue("All");
		WebElement subcatSelectedElement = driver.findElement(By.id("subCategoryList-target"));
		Select subcatSelected = new Select(subcatSelectedElement);
		waitUntilSelectOptionsPopulated(subcatSelected);
		return size;
	}
	
	private void waitUntilSelectOptionsPopulated(final Select select) {
    	WebDriverWait wait = new WebDriverWait(driver, 60);
    	wait.withTimeout(60, TimeUnit.SECONDS);
    	wait.pollingEvery(5, TimeUnit.MILLISECONDS);
    	wait.until(new ExpectedCondition<Boolean>(){
    	@Override
    	public Boolean apply(WebDriver driver) {
    		return !(select.getOptions().isEmpty());
    	}
    	});
    }
	
    public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
     }
    
    public void waitForPageLoad(String title){
    	WebDriverWait wait = new WebDriverWait(driver, 100);
    	wait.until(ExpectedConditions.titleIs(title));
    }

}
