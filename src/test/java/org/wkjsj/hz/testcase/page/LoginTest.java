package org.wkjsj.hz.testcase.page;

import static org.testng.AssertJUnit.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.log4testng.Logger;
import org.wkjsj.hz.base.db.DB;
import org.wkjsj.hz.base.operate.CommonFunction;
import org.wkjsj.hz.base.operate.LoginPage;
import org.wkjsj.hz.base.operate.HeadPage;
import org.wkjsj.hz.base.webDriver.Webdriver;
import org.wkjsj.hz.base.webDriver.WebdriverBaseCase;

public class LoginTest extends WebdriverBaseCase {
	protected Webdriver webDriver;
	protected DB db;
	private LoginPage LoginPage;
	private HeadPage HeadPage;
	private CommonFunction CommonFunction;
	String userPwd = "HiAuto"; // 用户名和密码同一个值
	Logger logger = Logger.getLogger(CheckLinkPageTest.class);

	@BeforeTest
	public void beforeTest() {

		try {
			webDriver = new Webdriver(driver);
			HeadPage = new HeadPage(webDriver);
			String url = webDriver.getTestedUrl(); // 获取首页地址
			// 传入初始地址和页面加载的时间
			CommonFunction = new CommonFunction();
			CommonFunction.initialFunction(webDriver, url, 2);
			webDriver.navigateUrl(url + "loginpage.php");// 跳转到指定页面

			String title = webDriver.getTitle();
			// 首页校验，首页都不对，下面就可以不用进行了
			assertEquals("LOGIN", title);
			LoginPage = new LoginPage(webDriver);
		} catch (Exception e) {
			logger.error("beforeTest error:", e);
		}
	}

	// 验证登录的地址是对的
	@Test
	public void testLgInfoEnter() {
		beforeTest("testLgInfoEnter");
		try {
			// 输入用户名和密码
			LoginPage.login(userPwd);
			assertEquals("Welcome To Online Judge", webDriver.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("testLgInfoEnter error:", e);
			webDriver.captureScreenshot("testLgInfoEnter");
		}

	}

	// @Test
	public void testNameSQL() {

		try {
			db = new DB();
			Connection con = db.getConnection(webDriver);
			String query = "select * from users where user_id =" + userPwd;
			try {
				PreparedStatement pstm = con.prepareStatement(query);
				ResultSet rs = pstm.executeQuery();
				assertEquals(true, rs.next());
				pstm.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("testNameSQL error:", e);
			}

			db.closeConnection(con);
		} catch (Exception e) {
			logger.error("testNameSQL error:", e);

		}

	}

	@AfterTest
	public void afterTest() {
		try {
			HeadPage.clickALOR(5);
			webDriver.browserEnd();
		} catch (Exception e) {
			logger.error("afterClassTest error:", e);
		}
	}

}
