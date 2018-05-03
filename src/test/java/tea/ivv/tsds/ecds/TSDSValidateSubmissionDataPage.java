package tea.ivv.tsds.ecds;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import tea.ivv.tsds.TSDSPEIMS.TSDSConfirmDataPage;

public class TSDSValidateSubmissionDataPage {
	private WebDriver driver;
	
	public TSDSValidateSubmissionDataPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSConfirmDataPage validateAllCategories() {
		WebElement catElement = waitForElement(By.id("categoriesListId"), 20);
		Select catSelect = new Select(catElement);
		catSelect.selectByValue("All");
		WebElement subcatSelectedElement = driver.findElement(By.id("subcategoryList-target"));
		Select subcatSelected = new Select(subcatSelectedElement);
		waitUntilSelectOptionsPopulated(subcatSelected);
		Assert.assertTrue(driver.findElement(By.id("fatalFlagId")).isSelected(), "Fatal Flag Was Note Selected");
		Assert.assertTrue(driver.findElement(By.id("specialWarningFlagId")).isSelected(), "Special Warning Flag Was Note Selected");
		Assert.assertTrue(driver.findElement(By.id("warningFlagId")).isSelected(), "Warning Flag Was Note Selected");
		driver.findElement(By.id("jobNameId")).sendKeys("TSDS Test Promotion");
		driver.findElement(By.id("submitButtonId")).click();
		return new TSDSConfirmDataPage(driver);
	}
	
	public TSDSValidateSubmissionDataPage validateSpecificCatandSubCatData(String cat, String subcat) {
		WebElement catElement = waitForElement(By.id("categoriesListId"), 20);
		Select catSelect = new Select(catElement);
		catSelect.selectByVisibleText(cat);
		WebElement catSelectedElement = waitForElement(By.id("subcategoryList"), 20);
		Select catSelected = new Select(catSelectedElement);
		waitUntilSelectOptionsPopulated(catSelected);
		catSelected.selectByVisibleText(subcat);
		driver.findElement(By.id("subcategoryList-add-to-target")).click();
		WebElement subcatSelectedElement = driver.findElement(By.id("subcategoryList-target"));
		Select subcatSelected = new Select(subcatSelectedElement);
		waitUntilSelectOptionsPopulated(subcatSelected);
		Assert.assertTrue(driver.findElement(By.id("fatalFlagId")).isSelected(), "Fatal Flag Was Note Selected");
		Assert.assertTrue(driver.findElement(By.id("specialWarningFlagId")).isSelected(), "Special Warning Flag Was Note Selected");
		Assert.assertTrue(driver.findElement(By.id("warningFlagId")).isSelected(), "Warning Flag Was Note Selected");
		driver.findElement(By.id("jobNameId")).sendKeys("TSDS Test Promotion");
		driver.findElement(By.id("submitButtonId")).click();
		return new TSDSValidateSubmissionDataPage(driver);
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
