package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;
import com.qa.operncart.exceptions.FrameworkException;

public class ElementUtil {
	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil=new JavaScriptUtil(driver);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClick(By locator, int timeout) {
		waitForElementPresence(locator, timeout).click();
	}

	public void doSendKeys(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
	}
	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}

	public void doSendKeys(By locator, CharSequence... value) {
		getElement(locator).sendKeys(value);
	}
	
	public void doSendKeys(By locator, String value, int timeout) {
		waitForElementPresence(locator, timeout).sendKeys(value);
	}
	
	private void checkElementHighlight(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.isHighlight)) {
			jsUtil.flash(element);
		}
	}
	
	
	public WebElement getElement(By locator) {
		WebElement element= driver.findElement(locator);
		checkElementHighlight(element);
		return element;
	}

	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed:" + locator);
			return false;
		}

	}

	public boolean isElePresent(By locator, int expectedCount) {

		if (getElementsCount(locator) == expectedCount) {
			return true;
		}
		return false;
	}

	public boolean isElePresent(By locator) {

		if (getElementsCount(locator) == 1) {
			return true;
		}
		return false;
	}

	public boolean isElementNotPresent(By locator) {
		if (getElementsCount(locator) == 0) {
			return true;
		}
		return false;
	}

	public boolean isElementPresentMultipleTimes(By locator) {
		if (getElementsCount(locator) >= 1) {
			return true;
		}
		return false;
	}

	public boolean docheckIsEnabled(By locator) {
		return getElement(locator).isEnabled();
	}

	public boolean docheckIsSelecteded(By locator) {
		return getElement(locator).isSelected();
	}

	public String dogetElementText(By locator) {
		String eleText = getElement(locator).getText();
		if (eleText != null) {
			return eleText;
		} else {
			System.out.println("Element text is null" + eleText);
			return null;
		}

	}

	public String doElementGetAttribute(By locator, String attrName) {
		// return getElement(locator).getDomAttribute(attrName);
		// attrName is attribute name from DOM
		return getElement(locator).getDomProperty(attrName);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void printElementTextList(By locator) {
		List<String> eleTextList = getElementsTextList(locator);
		for (String e : eleTextList) {
			System.out.println(e);
		}
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String eleTxt = e.getText();
			if (eleTxt.length() != 0) {
				eleTextList.add(eleTxt);
			}
		}
		return eleTextList;
	}

	public boolean doSearch(By searchField, By suggestions, String searchKey, String matchVal)
			throws InterruptedException {

		boolean flag = false;
		doSendKeys(suggestions, searchKey);
		Thread.sleep(5000);

		List<WebElement> suggList = getElements(suggestions);
		int totalSuggestion = suggList.size();
		System.out.println("Totla number of suggestions =====" + totalSuggestion);

		if (totalSuggestion == 0) {
			System.out.println("No suggestion found");
			throw new FrameworkException("No Suggestion Found");
		}

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(matchVal)) {
				e.click();
				flag = true;
				break;
			}

		}
		if (flag) {
			System.out.println("Match Value is Found" + matchVal);
			return true;
		} else {
			System.out.println("match Value is Not Found" + matchVal);
			return false;
		}
	}

	// ******************Select Drop Down**************************//

	private Select getSelect(By locator) {
		return new Select(getElement(locator));
	}

	public int getDropDownOptionsCode(By locator) {
		return getSelect(locator).getOptions().size();
	}

	public void selectDropDownValuesByVisibleText(By locator, String visibleText) {
		getSelect(locator).selectByVisibleText(visibleText);
	}

	public void selectDropDownValuesByIndex(By locator, int index) {
		getSelect(locator).selectByIndex(index);
	}

	public void selectDropDownValuesByValue(By locator, String value) {
		getSelect(locator).selectByValue(value);
	}

	public List<String> getDropDownOptionTextList(By locator) {
		List<WebElement> optionList = getSelect(locator).getOptions();
		System.out.println("total number of options =====" + optionList.size());

		List<String> optionsTextList = new ArrayList<String>();
		for (WebElement e : optionList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	// Without using Select Class
	public void selectDropDownValue(By locator, String value) {
		List<WebElement> optionList = getElements(locator);
		selectDropDown(optionList, value);
	}

	// Using Select Class
	public void selectDropDownValueUsingSelect(By locator, String value) {
		List<WebElement> optionList = getSelect(locator).getOptions();
		selectDropDown(optionList, value);
	}

	private void selectDropDown(List<WebElement> optionList, String value) {
		System.out.println("total number of options =====" + optionList.size());

		for (WebElement e : optionList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	// *******************************Actions Util **************************//

	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}

	public void doActionsSendKeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionsSendKeysWithPause(By locator, String value, long pauseTime) {
		char ch[] = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}

	public void ParentChildMenu(String parentmenu, String childmenu) throws InterruptedException {

		act.moveToElement(getElement(By.xpath("//*[text()='" + parentmenu + "']"))).perform();
		Thread.sleep(1500);
		doClick(By.xpath("//*[text()='" + childmenu + "']"));
	}

	public void ParentChildMenu(By parentmenu, By childmenu) throws InterruptedException {

		act.moveToElement(getElement(parentmenu)).perform();
		Thread.sleep(1500);
		doClick(childmenu);
	}

	public void ParentChildMenu(By level1, By level2, By level3, By level4) throws InterruptedException {
		doClick(level1);
		Thread.sleep(1000);

		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1500);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1500);
		doClick(level4);
	}

//	************************Wait until******************************
	

	
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible on the page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element= wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible on the page as well. Visibility means that the element is not
	 * only displayed but also has a height and width that is greater than 0.
	 * default poling time/interval time=500ms
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public  WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	public  WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut),Duration.ofSeconds(intervalTime));
		WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	
	/**
	 * This wait for element visible on the page with fluent wait features
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */
	public WebElement waitForElementVisibleWithFluentFeatures(By locator, int timeOut, int pollingTime) {
	Wait<WebDriver> wait =	new FluentWait<WebDriver>(driver)
							.withTimeout(Duration.ofSeconds(timeOut))
							.pollingEvery(Duration.ofSeconds(pollingTime))
							.ignoring(NoSuchElementException.class)
							.withMessage("=====element is not found======");
	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));			
	}
	
	
	
	/** 
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void waitForElementAndClick(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public  List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	public String getPageTitleIs(String expectedTitle, int timeOut) {
		if(waitForTitleIs(expectedTitle, timeOut)) {
			return driver.getTitle();
		}
		else {
			return "-1";
		}
	}
	
	public String getPageTitleContains(String expectedTitle, int timeOut) {
		if(waitForTitleContains(expectedTitle, timeOut)) {
			return driver.getTitle();
		}
		else {
			return "-1";
		}
	}
	
	public Boolean waitForTitleIs(String expectedTitle, int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.titleIs(expectedTitle));//if title is not matched, it will throw timeOut exception
		} catch(TimeoutException e) {
			return false;
		}
	}
	
	public Boolean waitForTitleContains(String expectedTitle, int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.titleIs(expectedTitle));//if title is not matched, it will throw timeOut exception
		} catch(TimeoutException e) {
			return false;
		}
	}
	
	public String waitForTitleContainsAndReturn(String expectedTitle, int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try {
			 wait.until(ExpectedConditions.titleIs(expectedTitle));
			 return driver.getTitle();
		} catch(TimeoutException e) {
			System.out.println("title is not matched");
			return "-1";
		}
	}
	
	public String getPageURLContains(String fractionURL, int timeOut) {
		if(waitForURLContains(fractionURL,timeOut)) {
			return driver.getCurrentUrl();
		}
		else {
			return "-1";
		}
	}
	
	public boolean waitForURLContains(String fractionURL, int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.urlContains(fractionURL));
		} catch(TimeoutException e) {
			System.out.println("URL is not matched");
			return false;
		}
	}
	
	public String waitForURLContainsAndReturns(String fractionURL, int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		try {
			 wait.until(ExpectedConditions.urlContains(fractionURL));
			 return driver.getCurrentUrl();
		} catch(TimeoutException e) {
			System.out.println("URL is not matched");
			return "-1";
		}
	}
	
	public Alert waitForAlertAndSwitch(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public Alert waitForAlertUsingFluentWaitAndSwitch(int timeOut) {
		Wait<WebDriver> wait =	new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoAlertPresentException.class)
				.withMessage("=====JS Alert is not found======");
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public String getAlertText(int timeOut) {
		return waitForAlertAndSwitch(timeOut).getText();
	}

	public void acceptAlert(int timeOut) {
		waitForAlertAndSwitch(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlertAndSwitch(timeOut).dismiss();
	}

	public void enterValueOnAlert(int timeOut, String value) {
		waitForAlertAndSwitch(timeOut).sendKeys(value);
	}

	// wait for frame:
	public void waitForFrameUsingLocatorAndSwitchToIt(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameUsingLocatorAndSwitchToIt(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameUsingLocatorAndSwitchToIt(String idOrName, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFrameUsingLocatorAndSwitchToIt(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	// wait for window/tab:
	public boolean waitForNewWindowOrTab(int expectedNumberOfWindows, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if (wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
					return true;
				}
			} catch (TimeoutException e) {
				System.out.println("number of windows are not matched....");
			}

			return false;
		}


	

}
