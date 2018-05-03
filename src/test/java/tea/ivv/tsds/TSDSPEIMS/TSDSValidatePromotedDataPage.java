package tea.ivv.tsds.TSDSPEIMS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSValidatePromotedDataPage {
	private WebDriver driver;
	
	public TSDSValidatePromotedDataPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSValidatePromotedDataPage selectAllCategories() {
		WebElement catElement = waitForElement(By.id("categoriesListId"), 20);
		Select catSelect = new Select(catElement);
		catSelect.selectByValue("All");
		WebElement subcatSelectedElement = driver.findElement(By.id("subCategoryList-target"));
		Select subcatSelected = new Select(subcatSelectedElement);
		waitUntilSelectOptionsPopulated(subcatSelected);
		return this;
	}
	
	public boolean getFatalFlag() {
		return driver.findElement(By.id("fatalFlag")).isSelected();
	}
	
	public boolean getSpecialWarningFlag() {
		return driver.findElement(By.id("spWarningFlag")).isSelected();
	}
	
	public boolean getWarningFlag(){
		return driver.findElement(By.id("warningFlag")).isSelected();
	}
	
	public TSDSConfirmDataPage submitValidation() {
		driver.findElement(By.id("jobNameId")).sendKeys("TSDS Test Promotion");
		driver.findElement(By.id("submitButtonId")).click();
		return new TSDSConfirmDataPage(driver);
	}
	
	public TSDSValidatePromotedDataPage selectSpecificCatandSubCatData(String cat, String subcat) {
		WebElement catElement = waitForElement(By.id("categoriesListId"), 20);
		Select catSelect = new Select(catElement);
		catSelect.selectByVisibleText(cat);
		WebElement catSelectedElement = waitForElement(By.id("subCategoryList"), 20);
		Select catSelected = new Select(catSelectedElement);
		waitUntilSelectOptionsPopulated(catSelected);
		catSelected.selectByVisibleText(subcat);
		driver.findElement(By.id("subCategoryList-add-to-target")).click();
		WebElement subcatSelectedElement = driver.findElement(By.id("subCategoryList-target"));
		Select subcatSelected = new Select(subcatSelectedElement);
		waitUntilSelectOptionsPopulated(subcatSelected);
		return new TSDSValidatePromotedDataPage(driver);
	}
	
	public WebElement waitForElement(By by, long timeout) {
	      WebDriverWait wait = new WebDriverWait(driver,timeout);
	      WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	      return element;
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

}
