package test.java.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {
	
	/**
	 * Get a WebElement from current driver position using a By locator.
	 * If the element is not present after given timeout, null is returned.
	 * 
	 * @param driver
	 * @param byLocator
	 * @param seconds max time to wait for the element to be present
	 * @return
	 */
    public static WebElement getWebElement(WebDriver driver, final By byLocator, int seconds) {
    	WebElement webElement = null;
    	
        try {
            webElement = (new WebDriverWait(driver, seconds)).until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver d) {
                    return d.findElement(byLocator);
                }
            });
        } catch (Exception e) {
            return null;
        }
        
        return webElement;
    }
    
    /**
     * Get browser alert. Alerts can then be accepter or dismissed through its corresponding methods.
     *
     * @param driver
     * @param seconds 
     * @return the alert
     */
    public static Alert getAlert(WebDriver driver, int seconds) {
    	Alert alert = null;
        try {
        	alert = (new WebDriverWait(driver, seconds)).ignoring(NullPointerException.class).until(new ExpectedCondition<Alert>() {
                public Alert apply(WebDriver d) {
                	Alert alert = d.switchTo().alert();
                    alert.getText();
                    return alert;
                }
            });
        } catch (Exception e) {
            return null;
        }
        return alert;
    }
    
    /**
     * Wait for the given element to disappear. If the element disappears before the timeout,
     * the function returns True. If the element is still present after the given timeout,
     * the function returns False.
     *
     * @param driver
     * @param byLocator
     * @param seconds max time to wait for the element to disappear
     * @return true if the element disappeared, else false
     */
    public static Boolean waitForElementToDisappear(WebDriver driver, final By byLocator, int seconds) {
        try {
            new WebDriverWait(driver, seconds).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return !d.findElement(byLocator).isDisplayed();
                }
            });
        } catch (Exception e) {
            if (null != e.getCause() && e.getCause().getClass() == NoSuchElementException.class) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
    
    /**
     * Switch driver into frame, if iframe is found by the given name. If iframe is not found,
     * returns false.
     *
     * @param driver
     * @param iframeName
     * @param seconds max time to wait for the frame to be present
     */
    public static Boolean switchToFrame(WebDriver driver, final String iframeName, int seconds) {
        WebDriver newFrameDriver = null;
        try {
        	newFrameDriver = (new WebDriverWait(driver, seconds)).until(new ExpectedCondition<WebDriver>() {
                @Override
        		public WebDriver apply(WebDriver d) {
                	WebDriver driver = d.switchTo().frame(iframeName);
                    return driver;
                }
            });
        } catch (Exception e) {
            return Boolean.FALSE;
        }
        return newFrameDriver != null;
    }
    
    /**
     * Get the amount of elements with the given locator.
     *
     * @param locator the By locator for the elements
     * @return
     */
    public static Integer getNumberOfElements(WebDriver driver, By locator) {
        return driver.findElements(locator).size();
    }
    
    
    /**
     * Switch driver to next browser window (or tab). Returns true if new window was found, else false.
     * 
     * @param driver
     * @return
     */
    public static Boolean switchToNextWindow(WebDriver driver) {
        String current = driver.getWindowHandle();
        Set<String> allHandlers = driver.getWindowHandles();

        String next = current;
        for (String handler : allHandlers) {
            if (!handler.equals(next)) {
                driver.switchTo().window(handler);
                next = handler;
                break;
            }
        }

        return !current.equals(next);
    }
    
    /**
     * Wait for an element to be present and visible
     * 
     * @param driver
     * @param byLocator
     * @param seconds max seconds to wait for element to present and be visible
     */
    public static Boolean waitForElementToBeVisible(WebDriver driver, final By byLocator, int seconds) throws InterruptedException {

        for (int second = 0; second < seconds; second++) {
            try {
                if (driver.findElement(byLocator).isDisplayed()) {
                    return Boolean.TRUE;
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        }
        
        return Boolean.FALSE;
    }
    
    /**
     * Get a list of WebElements for a given locator.
     *
     * @param driver
     * @param xpathLocator xpath string
     * @param seconds
     * @return list of webElements with given locator
     */
    public static List<WebElement> getWebElementsList(WebDriver driver, String xpathLocator, int seconds) {
        List<WebElement> lst = new ArrayList<WebElement>();
        
        // Wait for the elements to be present
        SeleniumUtils.getWebElement(driver, By.xpath(xpathLocator), seconds);
        int count = getNumberOfElements(driver, By.xpath(xpathLocator));
        
        for (int i = 1; i <= count; i++) {
            lst.add(getWebElement(driver, By.xpath("(" + xpathLocator + ")[" + i + "]"), seconds));
        }

        return lst;
    }
    
    /**
     * Wait for the page to be loaded and for the DOM to be processed.
     * Returns true if page loaded within timeout.
     * 
     * @param driver
     * @param seconds max seconds to wait for page to load
     */
    public static Boolean waitForPageToBeLoaded(WebDriver driver, int seconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        Wait<WebDriver> wait = new WebDriverWait(driver, seconds);
        try {
            wait.until(expectation);
            return Boolean.TRUE;
        } catch (Throwable error) {
            return Boolean.FALSE;
        }
    }

}
