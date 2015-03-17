package org.wkjsj.hz.testcase.page;

import static org.testng.AssertJUnit.assertEquals;

import java.sql.Connection;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.log4testng.Logger;
import org.wkjsj.hz.base.db.DB;
import org.wkjsj.hz.base.operate.CommonFunction;
import org.wkjsj.hz.base.operate.RegisterPage;
import org.wkjsj.hz.base.webDriver.Webdriver;
import org.wkjsj.hz.base.webDriver.WebdriverBaseCase;

/*注册了2个账号：seleniumTestId和testID,用户名和密码一致，，只验证正确的注册情况，如果注册同一个账号，将会fail
 * 说明：beforeTest里的内容大部分重复，到时分装成一个类;
 获取一个Elements的list也要封装成一个类*/

public class RegisterTest extends WebdriverBaseCase {
	protected Webdriver webDriver;
	private RegisterPage RegisterPage;
	private CommonFunction CommonFunction;
	protected DB db;
	String url;
	List<WebElement> mainElements; // 实际获取的menu值
	WebElement nowElement; // 当前要被focus的元素
	String userInfo = "testID"; // 用户名等信息都是一致
	Logger logger = Logger.getLogger(RegisterTest.class);

	@BeforeTest
	public void beforeTest() {
		try {
			webDriver = new Webdriver(driver);
			url = webDriver.getTestedUrl(); // 被测试地址
			CommonFunction = new CommonFunction();
			CommonFunction.initialFunction(webDriver, url, 2);
			webDriver.navigateUrl(url + "registerpage.php"); // 跳转到指定页面
			String title = webDriver.getTitle();
			// 首页校验，首页都不对，下面就可以不用进行了
			assertEquals("Welcome To Online Judge", title);
			RegisterPage = new RegisterPage(webDriver);
		} catch (Exception e) {
			logger.error("beforeTest error:", e);
		}
	}

	// 测试注册的输入框的数量，但是把submit和reset的button都定位到了，，这个不好，得改进
	@Test
	public void testNumberReInfo() {

		try {
			mainElements = webDriver.findElementsByTagName("input");
			assertEquals(8, mainElements.size()); // 包括了button的元素
		} catch (Exception e) {
			logger.error("testNumberReInfo error:", e);

		}
	}

	// 注册验证，所有的输入都是同一个值
	@Test(dependsOnMethods = { "testNumberReInfo" })
	public void testIdReInfoValid() {

		try {
			RegisterPage.register(mainElements.size());
			// RegisterPage.register(userInfo, mainElements.size());
			webDriver.submitClick();
			assertEquals("Welcome To Online Judge", webDriver.getTitle());
		} catch (Exception e) {
			logger.error("testIdReInfoValid error:", e);

		}
	}

	// 将指定用户变成管理员
	@Test(dependsOnMethods = { "testIdReInfoValid" })
	// @Test
	public void testReAdmin() {
		try {
			db = new DB();
			Connection con = db.getConnection(webDriver);
			String query = "insert into privilege(user_id,rightstr)values(?,'administrator')";
			String user_id = "testID";
			int i = RegisterPage.registAdmin(con, query, user_id);
			assertEquals(1, i);
		} catch (Exception e) {
			logger.error("testReAdmin error:", e);

		}

	}

	@AfterTest
	public void afterTest() {
		try {
			// 如果登录了就得退出，缺了退出代码
			webDriver.browserEnd();
		} catch (Exception e) {
			logger.error("afterClassTest error:", e);
		}

	}

}
