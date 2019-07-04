package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import main.CucumberRunner;

public class SearchPage extends CucumberRunner {

    public WebElement searchBox = driver.findElement(By.cssSelector("input[name='q']"));
    
    @FindBy(xpath="//input[@name='userid']")
	public WebElement Username_Internal;
    
    @FindBy(xpath="//input[@name='b64Pwd']")
	public WebElement Password_Internal;
    
    @FindBy(xpath="//input[@type='SUBMIT']")
   	public WebElement clk_lgin;
    
}