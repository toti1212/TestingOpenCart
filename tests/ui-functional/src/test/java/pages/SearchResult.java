package test.java.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import test.java.framework.SeleniumUtils;

public class SearchResult {
	
	private WebDriver driver;
	public SearchResult(WebDriver driver){
		this.driver = driver;
	}
	
	public void addToCart(String prod) {
		WebElement addToCartButton = SeleniumUtils.getWebElement(driver, By.xpath("(//h4//a[text()='"+ prod +"']//following::button[contains(@onclick,'cart.add')])[1]"), 30);
		addToCartButton.click();
	}
}

