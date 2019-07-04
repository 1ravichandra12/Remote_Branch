package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Files;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import helpers.ReportHelper;
import pages.SearchPage;

//@CucumberOptions(strict = true, monochrome = true, features = "src/test/resources/features", glue = "stepdefinition", format = {"pretty","json:target/cucumber.json"}, tags = { "@Regression,@JunitScenario,@TestngScenario" })
@CucumberOptions(strict = true, monochrome = true, features = "src/test/resources", glue = "stepdefinition", format = {"pretty","json:target/cucumber.json"}, tags = { "@Regression,@Login" })

public class CucumberRunner extends AbstractTestNGCucumberTests {
	//Internal_Login in = new Internal_Login();
	public static Properties config = null;
	public static WebDriver driver = null;
	
	public void LoadConfigProperty() throws IOException {
		config = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
		config.load(ip);
	}
	String URL ="https://wwwqa.warrantyauthoring.ford.com/prweb/SSOServlet ";
	SearchPage page = new SearchPage();
	
	@Given ("^I am in ford internal login page$")
	public void Login_Page() throws Throwable  {
		driver.get(URL);
		
	}
	
	@When("^I enter Username as \"([^\"]*)\" and Password as \"([^\"]*)\"$") 
	public void entercredentials(String username,String password) {
		explicitWait(page.Username_Internal);
		page.Username_Internal.sendKeys(username);	
		explicitWait(page.Password_Internal);
		page.Password_Internal.sendKeys(password);
				
	}
	
	@And ("^click on the login link$")
	public void click_login() throws Throwable  {
		page.clk_lgin.click();
	}
	public void configureDriverPath() throws Throwable {
		//if(System.getProperty("os.name").startsWith("Windows")) {
		/*	String firefoxDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", firefoxDriverPath);*/
			WebDriver driver = new ChromeDriver();
			String chromeDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			System.out.println("CHROME BROWSER OPENED");
			driver.manage().window().maximize();
			Thread.sleep(2000);
			//driver.get(URL);	
			
		}
		
	
	public void openBrowser() throws Throwable {
			LoadConfigProperty();		
			configureDriverPath();	
			Login_Page();
			entercredentials(null, null);
			click_login();
			
		}

	public void maximizeWindow() {
		driver.manage().window().maximize();
	}
	public void OpenURL() {		
		driver.get(URL);
	}
	public void implicitWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void explicitWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 3000);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void pageLoad(int time) {
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	public void setEnv() throws Exception {
		LoadConfigProperty();
		String baseUrl = config.getProperty("siteUrl");
		driver.get(baseUrl);
	}

	public static String currentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String cal1 = dateFormat.format(cal.getTime());
		return cal1;
	}

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Throwable {
		openBrowser();
	//	maximizeWindow();
		implicitWait(30);
		deleteAllCookies();
		setEnv();
	}

	@AfterClass(alwaysRun = true)
	public void takeScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File trgtFile = new File(System.getProperty("user.dir") + "//screenshots/screenshot.png");
		trgtFile.getParentFile().mkdir();
		trgtFile.createNewFile();
		Files.copy(scrFile, trgtFile);

	}

	@AfterMethod(alwaysRun = true)
	public void tearDownr(ITestResult result) throws IOException {
		if (result.isSuccess()) {
			File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String failureImageFileName = result.getMethod().getMethodName()
					+ new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";
			File failureImageFile = new File(System.getProperty("user.dir") + "//screenshots//" + failureImageFileName);
			failureImageFile.getParentFile().mkdir();
			failureImageFile.createNewFile();
			Files.copy(imageFile, failureImageFile);
		}

	}
	@AfterSuite(alwaysRun=true)
	public void generateHTMLReports() {
		ReportHelper.generateCucumberReport();
	}

	@AfterSuite(alwaysRun = true)
	public void quit() throws IOException, InterruptedException {
		//driver.quit();
	}
}
