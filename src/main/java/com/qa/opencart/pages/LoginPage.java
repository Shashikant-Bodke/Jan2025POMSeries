package com.qa.opencart.pages;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private By locators: Page object
	
	private By username=By.id("input-email");
	private By password=By.id("input-password");
	private By loginBtn=By.xpath("//input[@value='Login']");
	private By forgotPwdLink=By.linkText("Forgotten Password");
	private By RegisterLink=By.linkText("Register");
	private By logo=By.cssSelector("img.img-responsive");
	
	//2. public page constr..
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	//3. public page actions/methods...
	@Step("getting login page title values")
	public String getLoginPageTitle() {
		String title= eleUtil.waitForTitleContainsAndReturn(AppConstants.LOGIN_PAGE_TITLE,AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("Login page title: " + title);
		return title;
	}
	
	@Step("getting login page url values")
	public String getLoginPageURL() {
		String url=eleUtil.waitForURLContainsAndReturns(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("login page url:" +url);
		return url;
	}
	
	@Step("checking forgot pwd link exist")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}
	
	@Step("check logo exist")
	public boolean isLogoExit() {
		return eleUtil.isElementDisplayed(logo);
	}
	
	@Step("doing login with username: {0} and password:{1}")
	public AccountsPage doLogin(String userName, String pwd) {
		 //bodkeshashi12@gmail.com //@2024Testing
		
		
		System.out.println("Creds are ==== " +userName+ "  :  " +pwd);
		eleUtil.waitForElementVisible(username, AppConstants.DEFAULT_SHORT_TIME_OUT).sendKeys(userName);
		
		eleUtil.doSendKeys(password, pwd);
		
		eleUtil.doClick(loginBtn);
		
		return new AccountsPage(driver);
	}
	
	@Step("navigating to register pages")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(RegisterLink);
		return new RegisterPage(driver);
	}
	
}

