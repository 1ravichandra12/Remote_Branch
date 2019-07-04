/*package stepdefinition;


import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.CucumberRunner;
import pages.SearchPage;

public class Internal_Login extends CucumberRunner {
	
	WebDriver driver;
	SearchPage page = new SearchPage();
	String URL ="https://wwwqa.warrantyauthoring.ford.com/prweb/SSOServlet";
	
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
	
}


*/