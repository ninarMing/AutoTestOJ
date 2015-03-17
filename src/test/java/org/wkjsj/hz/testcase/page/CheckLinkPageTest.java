package org.wkjsj.hz.testcase.page;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.log4testng.Logger;
import org.wkjsj.hz.base.operate.*;
import org.wkjsj.hz.base.file.*;
import org.wkjsj.hz.base.webDriver.*;

//import org.testng.annotations.Test;

public class CheckLinkPageTest extends WebdriverBaseCase {
	protected Webdriver webDriver;
	private HeadPage HeadPage;
	private CommonFunction CommonFunction;
	String url;
	List<WebElement> menuElements; // 实际获取的menu值
	List<WebElement> lgReElements; // 实际获取的注册、登录值
	String[] expMenuStr = GetCSVContent.getLinkName(
			"../src/main/resources/fixedData/LinkData.csv", 8); // 期望的menu Name
	String[] expLgReStr = GetCSVContent.getLinkName(
			"../src/main/resources/fixedData/LoginReData.csv", 2);
	WebElement nowElement; // 当前要被点击的元素

	Logger logger = Logger.getLogger(CheckLinkPageTest.class);

	// BeforeTest正式测试前得保证页面是对的，，验证被测url所指的地址是正确的
	@BeforeTest
	public void beforeTest() {
		try {
			webDriver = new Webdriver(driver);
			url = webDriver.getTestedUrl();// 测试入口url
			CommonFunction = new CommonFunction();
			CommonFunction.initialFunction(webDriver, url, 3);
			String title = webDriver.getTitle();
			// 首页校验，首页都不对，下面就可以不用进行了
			assertEquals("Welcome To Online Judge", title);
			HeadPage = new HeadPage(webDriver);
			menuElements = HeadPage.menuNum();
			lgReElements = HeadPage.loginRe();
		} catch (Exception e) {
			logger.error("beforeTest error:", e);

		}
	}

	// Test验证链接数量
	@Test
	public void testCheckLinkNum() {
		try {
			assertEquals(9, menuElements.size());

			assertEquals(2, lgReElements.size());
		} catch (Exception e) {
			logger.error("testCheckLinkNum error:", e);

		}
	}

	// Test验证前7个链接所指页面的正确性
	@Test
	public void testCheckLinkPage() {
		try {

			for (int i = 1; i < menuElements.size(); i++) {

				webDriver.waitTimeThread(7);
				nowElement = webDriver.findElementByXpath("//*[@id='menu']/a["
						+ i + "]");
				nowElement.click();
				webDriver.waitTimeThread(2);
				assertEquals(expMenuStr[i - 1], webDriver.getTitle());

			}
		} catch (Exception e) {
			logger.error("testCheckLinkPage error:", e);

		}
	}

	// Test验证登录和注册链接的正确性
	@Test
	public void testCheckloginLink() {
		try {

			for (int i = 1; i < lgReElements.size(); i++) {
				nowElement = webDriver
						.findElementByXpath("//*[@id='profile']/a[" + i + "]");
				nowElement.click();
				webDriver.waitTimeThread(5);
				assertEquals(expLgReStr[i - 1], webDriver.getTitle());

			}
			webDriver.waitTimeThread(5);
		} catch (Exception e) {
			logger.error("testCheckloginLink error:", e);
		}
	}

	// AfterTest关闭driver的资源
	@AfterTest
	public void afterTest() {
		try {
			webDriver.browserEnd();
		} catch (Exception e) {
			logger.error("afterClassTest error:", e);
		}

	}

}
