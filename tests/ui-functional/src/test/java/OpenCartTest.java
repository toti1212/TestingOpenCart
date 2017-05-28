package test.java;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import test.java.pages.HomePage;
import test.java.pages.SearchResult;

public class OpenCartTest {
	
	@Test
	public void abrirPag() throws InterruptedException {
		HomePage homePage = new HomePage();
		SearchResult searchResult = homePage.search("iMac"); 
		searchResult.addToCart("iMac");
	}
}
