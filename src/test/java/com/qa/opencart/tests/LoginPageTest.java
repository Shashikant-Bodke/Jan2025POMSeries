package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.listeners.ExtentReportListener;
import com.qa.opencart.pages.AccountsPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//@Listeners(ExtentReportListener.class )//----used for listener at class level

@Epic("Epic 100: design open login page")
@Feature("Feature 101: login feature")
@Story("US 120: All the features related to open cart login page")


public class LoginPageTest extends BaseTest {

	
	@Severity(SeverityLevel.MINOR)
	@Description("Login page title test feature")
	@Feature("Feature400: title test feature")
	@Test 
	public void loginPageTitleTest() {
		String actTitle=loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Severity(SeverityLevel.MINOR)
	@Description("Login page title test feature")
	@Feature("Feature401: title test feature")
	@Test 
	public void loginPageURLTest() {
		String actURL=loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("Login page forgot pwd link exist test feature")
	@Feature("Feature402: title test feature")
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Severity(SeverityLevel.MINOR)
	@Description("Logo exist test --")
	@Feature("Feature403: logo test feature")
	@Test
	public void logoExistTest() {
		Assert.assertTrue(loginPage.isLogoExit());
	}
	
	@Severity(SeverityLevel.MINOR)
	@Description("login user test feature")
	@Feature("Feature404: test feature")
	@Test(priority=Integer.MAX_VALUE)
	@Owner("Shashikant Bodke")
	public void loginTest() {
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE);
	}
	
}
