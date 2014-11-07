package selenium;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestContact extends AbstractTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();


  @Before
  public void setUp() throws Exception {
      driver = new FirefoxDriver();
      //driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      driver.get("http://egroupware.cursos.ces.com.uy/login.php");
      driver.findElement(By.name("login")).sendKeys("tvs2014g05");
      driver.findElement(By.name("passwd")).sendKeys("tvs2014g05");
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
      String mainPopup = driver.getWindowHandle();
      Set<String> windowId = driver.getWindowHandles();
      Iterator<String> itererator = windowId.iterator();
      itererator.next();
      String popupID = itererator.next();

      //Cambio el foco a la ventana emergente de creacion de contacto
      driver.switchTo().window(popupID);
           
      //Espero que se habra el popup
      timeOut("exec[n_fn]", driver);
      
      driver.findElement(By.id("exec[n_fn]")).click();
      driver.findElement(By.id("exec[n_prefix]")).sendKeys("Grupo 05");
      driver.findElement(By.id("exec[n_given]")).sendKeys("Iter1");
      EventPoint event = new EventPoint();
      event.click(360, 380);
      driver.findElement(By.id("exec[title]")).sendKeys("Example Contact");
      driver.findElement(By.id("exec[role]")).sendKeys("Usuario");
      driver.findElement(By.id("exec[room]")).sendKeys("1");
      driver.findElement(By.id("exec[org_name]")).sendKeys("TVS");
      driver.findElement(By.id("exec[org_unit]")).sendKeys("Montevideo");
      driver.findElement(By.id("exec[adr_one_street]")).sendKeys("Julio Herrera y Reissig 565 - Codigo Postal 11.300 - Montevideo - Uruguay");
      driver.findElement(By.id("exec[adr_one_locality]")).sendKeys("Montevideo");
      new Select(driver.findElement(By.id("exec[adr_one_countryname]"))).selectByVisibleText("URUGUAY");
      driver.findElement(By.id("exec[tel_work]")).sendKeys("555-555-555");
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("javascript:document.getElementById('exec[tel_prefer][tel_work]').checked = 'checked';");
      driver.findElement(By.id("exec[url]")).sendKeys("www.fing.edu.uy");
      driver.findElement(By.id("exec[email]")).sendKeys("grupo05@geocom.com.uy");
      driver.findElement(By.id("exec[button][save]")).click();
      //driver.findElement(By.id("exec[button][cancel]")).click();
     
      //Vuelvo a la ventana principal de contacto
      driver.switchTo().window(mainPopup);
      Assert.assertTrue(isElementPresent(By.id("exec[nm][search]")));
      
  }
  
  @Test
  public void testFindContact() throws Exception {
      //Menu principal
      driver.findElement(By.cssSelector("img[alt=\"Addressbook\"]")).click();
      
      driver.findElement(By.id("exec[nm][search]")).sendKeys("Iter 1");
      driver.findElement(By.id("exec[nm][start_search]")).click();
      Thread.sleep(1000);
      Assert.assertTrue(isElementPresent(By.className("row_on")));
      driver.findElement(By.cssSelector("img[src=\"/phpgwapi/templates/idots/images/view.png\"]")).click();
      
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
