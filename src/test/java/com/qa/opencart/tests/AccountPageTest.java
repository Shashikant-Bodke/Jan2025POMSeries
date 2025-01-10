package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountPageTest extends BaseTest{
	
	
	@BeforeClass
	public void accSetup() {
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));		
	}
	
	@Test
	public void accPageTitleTest() {
		String actTitle=accPage.getAccountPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE);
	}

	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersCountTest() {
		Assert.assertEquals(accPage.getTotalAccPageHeadersCount(),AppConstants.ACCOUNT_PAGE_HEADERS_COUNT);		
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String>actualheaderslist=accPage.getAccPageHeaders();
		Assert.assertEquals(actualheaderslist, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);
	}

	@DataProvider
	public Object[][] getSearchKey() {
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
		};
	}
	
	@Test(dataProvider="getSearchKey")
	public void searchCountTest(String searchKey, int searchCount) {
		resultPage=accPage.doSearch(searchKey);
		Assert.assertEquals(resultPage.getSearchResultsCount(),searchCount);
	}

	
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"},
		};
	}
	
	@Test(dataProvider="getSearchData")
	public void searchTest(String searchKey, String productName) {
		resultPage=accPage.doSearch(searchKey);
		productInfoPage=resultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(),productName);	
	}

}
