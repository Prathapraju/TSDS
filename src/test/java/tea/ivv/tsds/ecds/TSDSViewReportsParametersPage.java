/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tea.ivv.tsds.ecds;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author jtran
 */
public class TSDSViewReportsParametersPage {
    List <String> campusList = new ArrayList<String>();
    private  static WebDriver driver;
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
        if (rptType == "byCampus") {
            orgElement = driver.findElement(By.id("param1"));
            orgSelect = new Select(orgElement);
            orgSelect.selectByValue("By Campus");
        }
        Thread.sleep(1000);
        driver.findElement(By.id("runReport")).click();
    }
    
    
    @SuppressWarnings("unchecked")
    public TSDSViewReportsPage addParams(String rptType, String orgType, String campusIds, String ecdsStatusOrSSN, String ecdsRptType) {
        //ECD1-100-001, ECD1-101-001, ECD1-103-001, ECD1-104-001
        Actions ac = new Actions(driver);
        
        try {
             //Select By LEA or By Campus
            if (!orgType.isEmpty()) {
                new Select(driver.findElement(By.id("param1"))).selectByVisibleText(orgType);
            }
            //Select Campus ID
            if (!campusIds.isEmpty() ) {
                if( campusIds.contains(",")) {
                    campusList = Arrays.asList(campusIds.trim().split(","));
                }
                else {
                    campusList.add(campusIds);
                }
                ac.keyDown(Keys.CONTROL);
                for(String id: campusList) {
                    new Select(driver.findElement(By.id("param2"))).selectByValue(id);
                }
                ac.keyUp(Keys.CONTROL);
                driver.findElement(By.id("param2-add-to-target")).click();
            }
            if(!ecdsStatusOrSSN.isEmpty()) {
                //new Select(driver.findElement(By.id("param3"))).selectByVisibleText(ecdsStatusOrSSN);
                switch(ecdsStatusOrSSN) {
                    case "Completed":
                        driver.findElement(By.cssSelector("#param3 > option[value=\"Completed\"]")).click();
                        break;
                    case "Prepared":
                        driver.findElement(By.cssSelector("#param3 > option[value=\"Prepared\"]")).click();
                        break;
                    case "Both":
                        driver.findElement(By.cssSelector("#param3 > option[value=\"Both\"]")).click();
                        break;
                    case "Full SSN/Alt ID":
                        driver.findElement(By.cssSelector("#param3 > option[value=\"Full SSN/Alt ID\"]")).click();
                        break;
                    case "Partial SSN/Alt ID":
                        driver.findElement(By.cssSelector("#param3 > option[value=\"Partial SSN/Alt ID\"]")).click();
                        break;
                }
            }
            if(!ecdsRptType.isEmpty()) {
                new Select(driver.findElement(By.id("param4"))).selectByVisibleText(ecdsRptType);
            }
            clickAnElementById("runReport");
           (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
                public Boolean apply(WebDriver d) {
                    return d.getTitle().contentEquals("View Reports");
                }
            });
        }
        catch(NoSuchElementException e) {}
        
        return new TSDSViewReportsPage(driver);
    }
    
    public void clickAnElementById(String id) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id))).click();
    }
}
