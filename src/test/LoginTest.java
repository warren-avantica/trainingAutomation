package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {

    public String baseUrl = "http://192.168.0.103:86/";
    String driverPath = "D:\\webdrivers\\chromedriver.exe";
    public WebDriver driver;
    String username = "kagebushin17";
    String password = "Ganbarimasu17*";

    @BeforeTest
    public void launchBrowser() {
        System.out.println("lauching browser");
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }

    @Test
    public void verifyLoginPage() {
        String errorMessage = "Page not loaded";
        driver.findElement(By.id("ctl00_LoginView_LoginLink")).click();
        boolean isLoginPageDisplayed = driver.findElement(By.xpath("//h2[contains(text(), 'Login')]")).isDisplayed();
        Assert.assertTrue(isLoginPageDisplayed, errorMessage);
    }

    @Test
    public void verifyLoginWithValidCredentials() {
        //precondition - logout
        driver.findElement(By.id("ctl00_Main_LoginConrol_UserName")).clear();
        driver.findElement(By.id("ctl00_Main_LoginConrol_Password")).clear();

        driver.findElement(By.id("ctl00_Main_LoginConrol_UserName")).sendKeys(username);
        driver.findElement(By.id("ctl00_Main_LoginConrol_Password")).sendKeys(password);
        driver.findElement(By.id("ctl00_Main_LoginConrol_LoginButton")).click();

        boolean wasSuccessLogin = driver.findElement(By.id("ctl00_LoginView_MemberName")).isDisplayed();
        Assert.assertTrue(wasSuccessLogin, "Login error");
    }

    @Test
    public void verifyLoginWithBadCredentials() {
        //test
        driver.findElement(By.id("ctl00_Main_LoginConrol_UserName")).sendKeys(username);
        driver.findElement(By.id("ctl00_Main_LoginConrol_Password")).sendKeys("123");
        driver.findElement(By.id("ctl00_Main_LoginConrol_LoginButton")).click();

        boolean wasFailedLogin = driver.findElement(By.xpath("//td[contains(text(), 'Please try again')]")).isDisplayed();
        Assert.assertTrue(wasFailedLogin, "Login success");
    }

    @AfterTest
    public void terminateBrowser() {
        driver.close();
    }
}