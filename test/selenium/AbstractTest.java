package selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
public class AbstractTest extends TestCase{

    //private String JAVASCRIPT_ID = "javascript:document.getElementById('cntPanel').contentWindow.document.getElementById(";
    //private String JAVASCRIPT_ID = "javascript:document.getElementById(";

    public AbstractTest(){}

    public void timeOut(final String id, WebDriver driver){
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<WebElement>() {
        public WebElement apply(WebDriver driver) {
            return driver.findElement(By.id(id));
       }
            });
    }

    public void timeOutBoolean(final String id, WebDriver driver, final String compare){
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
        public Boolean apply(WebDriver driver) {
            return driver.findElement(By.id(id)).getText().equals(compare);
       }
            });
    }

//    public String getJavaScriptId(String id){
//            return JAVASCRIPT_ID + "'" + id + "')";
//    }

}
