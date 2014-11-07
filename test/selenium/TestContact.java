package selenium;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.xalan.xsltc.runtime.BasisLibrary;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestContact extends AbstractTest {
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
      driver.get("http://egroupware.cursos.ces.com.uy/login.php");
      archivo = new Properties();
      archivo.load(new FileInputStream("parametros.properties"));
      
      driver.findElement(By.name("login")).sendKeys(archivo.getProperty("loginUSR"));
      driver.findElement(By.name("passwd")).sendKeys(archivo.getProperty("loginPSW"));
      driver.findElement(By.name("submitit")).click();
      
      timeOut("divMain", driver);
  }

  @Test
  public void testCreateContact() throws Exception {
      //Menu principal
      driver.findElement(By.cssSelector("img[alt=\"Addressbook\"]")).click();
      
      //Boton add (Contact)
      timeOut("exec[nm][add]", driver);
      driver.findElement(By.id("exec[nm][add]")).click();
      
      //Se obtiene los id de las ventanas (para manejo de popup)
      Set<String> windowId = driver.getWindowHandles();
      Iterator<String> itererator = windowId.iterator();
      String mainID = itererator.next();
      String popupID = itererator.next();

      //Cambio el foco a la ventana emergente de creacion de contacto
      driver.switchTo().window(popupID);
           
      //Espero que se habra el popup
      timeOut("exec[n_fn]", driver);
      driver.findElement(By.id("exec[title]")).sendKeys("asdasd");
      driver.findElement(By.id("exec[role]")).sendKeys("1222");
      
      //Vuelvo a la ventana principal de contacto
      driver.switchTo().window(mainID);
      
      //Cierro la instancia abierta
      driver.close();
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
