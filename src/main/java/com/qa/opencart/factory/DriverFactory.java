package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.TakesScreenshot;

import com.qa.opencart.errors.AppError;
import com.qa.operncart.exceptions.BrowserException;
import com.qa.operncart.exceptions.FrameworkException;

/**
 * 
 * @author Shashikant Bodke
 *
 */
public class DriverFactory {

	WebDriver driver;
	Properties prop;
	
	public static String isHighlight;
	
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
	
	/**
	 * This method is used to initialize the driver on the basis of given browser
	 * name.
	 * 
	 * @param browserName
	 * @return it return driver
	 */
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		System.out.println("Browser name is:" + browserName);

		isHighlight=prop.getProperty("isHighlight");
		OptionsManager optionsManager=new OptionsManager(prop);
		
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());  //without thread local driver
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions())); // with thread local driver
			break;
		case "edge":
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "firefox":
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;

		default:
			System.out.println(AppError.INVALID_BROWSER_MESG + browserName + " is invalid");
			throw new BrowserException(AppError.INVALID_BROWSER_MESG);
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}

	/**
	 * This method is returning the driver with threadlocal
	 * @return 
	 * 
	 */
	public static WebDriver getDriver() {
		return tlDriver.get(); // local copy of driver to each thread
	}
	
	
	/**
	 * This method is used to initialize the properties from the config file.
	 * 
	 * @return
	 */

	// mvn clean install -Denv="sit"

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		
		isHighlight=prop.getProperty("highlight");

		String envName = System.getProperty("env");
	//	String headlessProp = System.getProperty("headless");
	//	String incognitoProp = System.getProperty("incognito");
		
		System.out.println("Running test on env: " + envName);

		try {
			if (envName == null) {
				System.out.println("Env is null, hence running tests on Prod Environment");
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "sit":
					ip = new FileInputStream("./src/test/resources/config/sit.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				default:
					System.out.println("Pls pass thr right env name.." + envName);
					throw new FrameworkException("INVALID ENV NAME");
				}
			}
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * Take Screenshot
	 */
	public static String getScreenshot(String methodName) {
		File srcFile=((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+ "/screenshot/" + methodName +"_" + 
						System.currentTimeMillis() + ".png";
		File destination=new File(path);
		
		try{
			FileHandler.copy(srcFile,destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;	
	}
}