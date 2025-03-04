package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
//import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;


//@Listeners(ChainTestListener.class)

public class BaseTest {
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected ResultsPage resultPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
//	protected SoftAssert softAssert;   //-not worked well if we make one fail & one pass, so to overcome we use in specific class only
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		df=new DriverFactory();
		prop=df.initProp();
		
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
		}
		
		driver=df.initDriver(prop);
		loginPage=new LoginPage(driver);
//		softAssert=new SoftAssert();
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
}
