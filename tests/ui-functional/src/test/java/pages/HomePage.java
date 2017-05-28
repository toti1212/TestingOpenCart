package test.java.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	@FindBy(how = How.XPATH, using = "//input[@name='search']")
	private WebElement searchInput;
	
	@FindBy(how = How.XPATH, using = "//div[@id='search']//button")
	private WebElement searchButton;
	
	private WebDriver driver;
	
	public HomePage() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver", "C:/Users/Silvia/Documents/UCU/Testing/open-cart/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://open-cart.azurewebsites.net");
		Thread.sleep(5000);
		PageFactory.initElements(driver, this);
	}
	
	public SearchResult search(String string){
		searchInput.sendKeys(string);
		searchButton.click();
		return new SearchResult(driver);
	}
}
