package selenium;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class LogInfoTest  extends AbstractTest{
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private static Properties archivo;
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    archivo = new Properties();
    archivo.load(new FileInputStream("parametros.properties"));
    baseUrl = "http://egroupware.cursos.ces.com.uy/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get("http://egroupware.cursos.ces.com.uy/login.php");
    driver.findElement(By.name("login")).sendKeys("tvs2014g05");
    driver.findElement(By.name("passwd")).sendKeys("tvs2014g05");
    driver.findElement(By.name("submitit")).click();
    timeOut("divMain", driver);
  }

  @Test
  public void testLogInfo() throws Exception {
    driver.get(baseUrl + "/index.php");
    driver.findElement(By.cssSelector("img[alt=\"InfoLog\"]")).click();
    driver.findElement(By.id("exec[add][note]")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | _blank | 30000]]
    //driver.get(baseUrl + "/index.php?menuaction=infolog.uiinfolog.edit&type=note&action=&action_id=0&cat_id=");
    Set<String> windowId = driver.getWindowHandles();
    Iterator<String> itererator = windowId.iterator();
    itererator.next();
    String popupID = itererator.next();

    //Cambio el foco a la ventana emergente de creacion de contacto
    driver.switchTo().window(popupID);
    new Select(driver.findElement(By.id("exec[info_contact][app]"))).selectByVisibleText("InfoLog");
    driver.findElement(By.id("exec[info_subject]")).clear();
    driver.findElement(By.id("exec[info_subject]")).sendKeys(archivo.getProperty("subjectQuickNote"));//"Descripción Nota rápida"
    driver.findElement(By.id("exec[info_des]")).clear();
    driver.findElement(By.id("exec[info_des]")).sendKeys(archivo.getProperty("descriptionNote"));
    new Select(driver.findElement(By.id("exec[info_status]"))).selectByVisibleText(archivo.getProperty("statusOngoing"));
    driver.findElement(By.id("exec[info_startdate][str]")).clear();
    driver.findElement(By.id("exec[info_startdate][str]")).sendKeys(archivo.getProperty("dateCompleted"));
    driver.findElement(By.id("exec[info_access]")).click();
    new Select(driver.findElement(By.id("exec[info_contact][app]"))).selectByVisibleText("Addressbook");
    
    driver.findElement(By.id("exec[info_contact][search]")).click();
    new Select(driver.findElement(By.id("exec[info_contact][id]"))).selectByVisibleText(archivo.getProperty("contact"));
    driver.findElement(By.id("exec[info_startdate][str]")).clear();
    driver.findElement(By.id("exec[info_startdate][str]")).sendKeys(archivo.getProperty("dateCompleted"));
    driver.findElement(By.id("exec[info_datecompleted][str]")).clear();
    driver.findElement(By.id("exec[info_datecompleted][str]")).sendKeys(archivo.getProperty("dateCompleted"));
    driver.findElement(By.id("exec[button][save]")).click();
  }

    @Test
  public void testLogInfoEmail() throws Exception {
    driver.get(baseUrl + "/index.php");
    driver.findElement(By.cssSelector("img[alt=\"InfoLog\"]")).click();
    driver.findElement(By.id("exec[add][note]")).click();
    Set<String> windowId = driver.getWindowHandles();
    Iterator<String> itererator = windowId.iterator();
    itererator.next();
    String popupID = itererator.next();

    //Cambio el foco a la ventana emergente de creacion de contacto
    driver.switchTo().window(popupID);
    new Select(driver.findElement(By.id("exec[info_type]"))).selectByVisibleText("E-Mail");
    driver.findElement(By.id("exec[info_subject]")).clear();
    driver.findElement(By.id("exec[info_subject]")).sendKeys(archivo.getProperty("subjectEmail"));
    new Select(driver.findElement(By.id("exec[info_percent]"))).selectByVisibleText(archivo.getProperty("completed"));
    driver.findElement(By.id("exec[info_datecompleted][str]")).clear();
    driver.findElement(By.id("exec[info_datecompleted][str]")).sendKeys(archivo.getProperty("dateCompleted"));
    driver.findElement(By.id("exec[info_des]")).clear();
    driver.findElement(By.id("exec[info_des]")).sendKeys(archivo.getProperty("descriptionEmail"));
    new Select(driver.findElement(By.id("exec[info_status]"))).selectByVisibleText(archivo.getProperty("statusDone"));
    driver.findElement(By.id("exec[info_contact][search]")).click();
    new Select(driver.findElement(By.id("exec[info_contact][id]"))).selectByVisibleText(archivo.getProperty("contact"));
    driver.findElement(By.id("infolog.edit.delegation-tab")).click();
    driver.findElement(By.id("exec[info_location]")).clear();
    driver.findElement(By.id("exec[info_location]")).sendKeys(archivo.getProperty("location"));
    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | id=eT_accountsel_exec_info_responsible_ | label=[tvs05] Grupo 5 Grupo 5]]
    
    driver.findElement(By.id("exec[button][save]")).click();
  }
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
