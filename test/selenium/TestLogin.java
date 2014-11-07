package selenium;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestLogin extends AbstractTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
private static Properties archivo;
  @Before
  public void setUp() throws Exception {
      driver = new FirefoxDriver();
      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      archivo = new Properties();
      archivo.load(new FileInputStream("parametros.properties"));
      baseUrl = "http://egroupware.cursos.ces.com.uy/";
  }

  @Test
  public void testLogin() throws Exception {
    driver.get(baseUrl + "login.php");
    driver.findElement(By.name("login")).sendKeys(archivo.getProperty("loginUSR"));
    driver.findElement(By.name("passwd")).sendKeys(archivo.getProperty("loginPSW"));
    driver.findElement(By.name("submitit")).click();
    timeOut("divMain", driver);
  }

  @Test
  public void testFailLogin() throws Exception {
    driver.get(baseUrl + "login.php");
    driver.findElement(By.name("login")).sendKeys(archivo.getProperty("loginUSR"));
    driver.findElement(By.name("passwd")).sendKeys(archivo.getProperty("loginPSW_INCORRECTO"));
    driver.findElement(By.name("submitit")).click();
    assertFalse(isElementPresent(By.id("divMain")));
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
