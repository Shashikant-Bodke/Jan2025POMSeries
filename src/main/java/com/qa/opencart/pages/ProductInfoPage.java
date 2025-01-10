package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader=By.tagName("h1") ;
	private By productMetaData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private By productImage=By.cssSelector("ul.thumbnails img");
	
	private Map<String, String> productMap;
	
	
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getProductHeader() {
		String ProductHeaderValue=eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
		System.out.println("Product Header =====> " +ProductHeaderValue);
		return ProductHeaderValue;
	}
	
	private void getProductMetadata() {
		//productMap=new HashMap<String,String>();
		List<WebElement> metaList=eleUtil.getElements(productMetaData);	
		for (WebElement e: metaList) {
			String metaText= e.getText();
			String metaData[]=metaText.split(":");
			String metaKey=metaData[0].trim();
			String metaValue=metaData[1].trim();
			productMap.put(metaKey, metaValue);
		}
		
	}
	
	private void getProductPriceData() {
		//productMap=new HashMap<String,String>();
		List<WebElement> priceList=eleUtil.getElements(productPriceData);	
		String price=priceList.get(0).getText();
		String exTaxprice=priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice", price);
		productMap.put("extaxprice", exTaxprice);
		}
	
	public Map<String, String> getProductData() {
		productMap=new HashMap<String,String>(); //no order
	//	productMap=new LinkedHashMap<String,String>();// to maintain insertion order
	//	productMap=new TreeMap<String,String>(); //to maintain alphabetical order
		productMap.put("productheader", getProductHeader());
		getProductMetadata();
		getProductPriceData();
		System.out.println("Product Data:" + productMap);
		return productMap;
	}
	
	public int getProductImageCount() {
		int imagesCount=eleUtil.waitForElementsPresence(productImage, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("imagesCount===========> "+imagesCount);
		return imagesCount;
	}
	
	
}
