package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private By LogoutLink=By.linkText("Logout");
	private By headers=By.cssSelector("div#content h2");
	private By search=By.name("search");
	private By searchIcon=By.cssSelector("div#search button");
	
	
	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getAccountPageTitle() {
		String title= eleUtil.waitForTitleContainsAndReturn(AppConstants.ACCOUNT_PAGE_TITLE,AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("account page title: " + title);
		return title;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.isElementDisplayed(LogoutLink);
	}
	
	public int getTotalAccPageHeadersCount() {
		return eleUtil.waitForElementsVisible(headers,AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
	}
	
	public List<String> getAccPageHeaders() {
		List<WebElement> headersList=eleUtil.waitForElementsVisible(headers,AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> headersValueList = new ArrayList<String>();
		for(WebElement e: headersList) {
			String headers=e.getText();
			headersValueList.add(headers);
		}
		return headersValueList;
	}
	
	public ResultsPage doSearch(String searchKey) {
		System.out.println("Search Key ===>" +searchKey);
		WebElement searchEle=eleUtil.waitForElementVisible(search,AppConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.doSendKeys(searchEle, searchKey);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);
	}
	
	
	
}