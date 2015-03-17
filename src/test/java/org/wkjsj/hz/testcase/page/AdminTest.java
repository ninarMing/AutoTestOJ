package org.wkjsj.hz.testcase.page;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.log4testng.Logger;
import org.wkjsj.hz.base.operate.CommonFunction;
import org.wkjsj.hz.base.operate.HeadPage;
import org.wkjsj.hz.base.operate.AdminPage;
import org.wkjsj.hz.base.file.GetXmlContent;
import org.wkjsj.hz.base.webDriver.Webdriver;
import org.wkjsj.hz.base.webDriver.WebdriverBaseCase;

/*
 * 一个方法一个button
 * */
public class AdminTest extends WebdriverBaseCase {
	protected Webdriver webDriver;
	private String userPwd = "AutoAdmin";
	private HeadPage HeadPage;
	private AdminPage AdminPage;
	private CommonFunction CommonFunction;
	private GetXmlContent GetXmlContent;
	Logger logger = Logger.getLogger(AdminTest.class);
	String path = "../src/main/resources/problemData"; // 测试数据的路径

	@BeforeTest
	public void beforeTest() {
		/*
		 * 如果直接从登录页面进行登录，登录成功后会在登录界面，但是界面为空白 所以还得再次跳转到首页
		 */
		try {
			webDriver = new Webdriver(driver);
			String url = webDriver.getTestedUrl();
			CommonFunction = new CommonFunction();
			HeadPage = new HeadPage(webDriver);
			AdminPage = new AdminPage(webDriver);
			GetXmlContent = new GetXmlContent();
			CommonFunction.initialLogin(webDriver, url, 2, userPwd);
			// "6"为管理
			HeadPage.clickALOR(6);
			assertEquals("JudgeOnline Administration", webDriver.getTitle());
			// webDriver.waitTimeThread(4);

		} catch (Exception e) {
			logger.error("beforeTest error:", e);

		}

	}

	/*********** 额外代码 ***********/
	// 添加问题
	public void addProContent(String filePath) {
		String content = "";
		for (int i = 0; i < 3; i++) {
			content = GetXmlContent.parserIptXml(filePath, i);
			if (content != "") {
				AdminPage.sendTxtToInput(i, content);
			}
		}
		for (int i = 0; i < 4; i++) {
			content = GetXmlContent.parserCkXml(filePath, i);
			if (content != "") {
				AdminPage.sendTxtToCkeditor(i, content);
			}
		}
		for (int i = 0; i < 5; i++) {
			content = GetXmlContent.parserAreaXml(filePath, i);
			if (content != "") {
				AdminPage.sendTxtToTxtarea(i, content);
			}
			//为了论文能够截图
		//	webDriver.waitTimeThread(10);
		}
	}

	// 添加成功后的状态
	public void checkAssertStatus(String result) {

		AdminPage.toMFrame(4);
		webDriver.submitClick();
		WebElement str = webDriver.findElementByXpath("//body/a");
		// 防止没有提交成功，最多5次提交
		for (int i = 0; i < 5; i++) {
			if (str == null) {
				webDriver.submitClick();
				str = webDriver.findElementByXpath("//body/a");
			} else {
				break;
			}
		}
		assertEquals(result, str.getText());
		webDriver.awayFrame();

	}

	/*********** 添加问题 ***********/
	// 问题列表为空直接提交会有问题,会有空的值真的添加进去
	// 1.1 add problem
	//@Test
	public void testProblemAddEmpty() {
		try {
			String result = "Add More Test Data";
			AdminPage.clickAMenuElement(4);
			this.checkAssertStatus(result);
		} catch (Exception e) {
			logger.error("testProblemAddEmpty error:", e);
			webDriver.captureScreenshot("testProblemAddEmpty");

		}

	}

	// 1.2全部添加的问题添加成功，增加问题：
	@Test
	public void testProbleamAdd() {
		try {
			assertEquals("JudgeOnline Administration", webDriver.getTitle());
			// 先“添加问题”frame【menu】
			AdminPage.clickAMenuElement(4);
			/**
			 * 在另一个frame【main】里输入测试数据： 先要定位到这个frame里
			 * 
			 * 
			 * */
			String result = "Add More Test Data";
			String filePath = path + "/fps-2003年秋浙江省计算机等级考试二级 C 编程题[2].xml";
			/********** 添加问题内容 *********/
			this.addProContent(filePath);

			/***** 下面进行验证是否添加成功，，最好可以用数据库进行验证 **********/
			this.checkAssertStatus(result);
			// assert.equals("全都填写了不知道会怎么样",);
			// 上面就是全部的填写problem里的信息
			AdminPage.clickAMenuElement(5);
			AdminPage.toMFrame(4);
			String title = GetXmlContent.parserIptXml(filePath, 0);
			assertEquals(title, AdminPage.getlistProCell(3, 2).getText());
			webDriver.awayFrame();
		} catch (Exception e) {
			logger.error("testProblemAdd error:", e);
			webDriver.captureScreenshot("testProblemAdd");
		}

	}

	// 1.3验证list problem 中的edit是否成功
	//@Test(dependsOnMethods = { "testProbleamAdd" })
	public void testProblemEdit() {
		try {
			String result = "See The Problem!";
			AdminPage.clickAMenuElement(5);
			AdminPage.toMFrame(4);
			WebElement editElement = AdminPage.getlistProCell(3, 6);
			editElement.click();
			webDriver.awayFrame();
			webDriver.waitTimeThread(3);
			// 修改内容
			String filePath = path + "/fps-2003年秋浙江省计算机等级考试二级 C 编程题[2].xml";
			this.addProContent(filePath);

			this.checkAssertStatus(result);
			// 验证是否修改成功
			//
			AdminPage.clickAMenuElement(5);

			AdminPage.toMFrame(4);
			String title = GetXmlContent.parserIptXml(filePath, 0);
			assertEquals(title, AdminPage.getlistProCell(3, 2).getText());
			webDriver.awayFrame();
		} catch (Exception e) {
			logger.error("testProblemEdit error:", e);
			webDriver.captureScreenshot("testProblemEdit");
		}

	}

	// 1.4验证list problem 中的delete是否成功
	//@Test
	public void testProblemDelete() {
		try {
			this.testProblemAddEmpty();
			AdminPage.clickAMenuElement(5);
			AdminPage.toMFrame(4);
			String title = AdminPage.getlistProCell(3, 2).getText();
			String titleS = AdminPage.getlistProCell(4, 2).getText();
			AdminPage.getlistProCell(3, 5).click();
			webDriver.closeAlert();
			title = AdminPage.getlistProCell(3, 2).getText();
			assertEquals(titleS, title);
			// 最好是链接数据库用数据库验证,上面的方法的验证必须保证添加前就有数据存在；
			webDriver.waitTimeThread(6);
		} catch (Exception e) {
			logger.error("testProblemDelete error:", e);
			webDriver.captureScreenshot("testProblemDelete");
		}
	}

	// 添加新闻
	// 2.1添加新闻成功
	//@Test
	public void testNewsAdd() {
		// 添加完新闻后，直接跳到新闻列表里
		try {
			AdminPage.clickAMenuElement(2);

			String title = "测试添加新闻是否成功";
			AdminPage.sendTxtToInput(0, title);
			String content = "添加一下新闻的内容，看可不可以";
			AdminPage.sendTxtToCkeditor(5, content);
			AdminPage.toMFrame(4);
			webDriver.submitClick();
			WebElement titleElement = AdminPage.getlistProCell(2, 2);
			for (int i = 0; i < 5; i++) {
				if (titleElement == null) {
					webDriver.submitClick();
					titleElement = AdminPage.getlistProCell(2, 2);
				} else {
					break;
				}
			}
			webDriver.captureScreenshot("testNewsAdd");
			String newsTitle = AdminPage.getlistProCell(2, 2).getText();
			assertEquals(title, newsTitle);
			webDriver.awayFrame();
		} catch (Exception e) {
			logger.error("testNewsAdd error:", e);
			webDriver.captureScreenshot("testNewsAdd");
		}

	}

	// 2.2添加空新闻
	//@Test
	public void testNewsAddEmpty() {
		try {
			AdminPage.clickAMenuElement(2);
			AdminPage.toMFrame(4);
			webDriver.submitClick();
			WebElement titleElement = AdminPage.getlistProCell(2, 2);
			for (int i = 0; i < 5; i++) {
				if (titleElement == null) {
					webDriver.submitClick();
					titleElement = AdminPage.getlistProCell(2, 2);
				} else {
					break;
				}
			}
			assertEquals("", titleElement.getText());
			webDriver.awayFrame();
		} catch (Exception e) {
			logger.error("testNewsAddEmpty error:", e);
			webDriver.captureScreenshot("testNewsAddEmpty");
		}

	}

	// 2.3编辑新闻
	//@Test(dependsOnMethods = { "testNewsAddEmpty" })
	public void testNewsEdit() {

		try {
			// 先添加空的新闻，然后再编辑，添加title,由判断title获得添加是否成功
			// this.testNewsAddEmpty();
			AdminPage.clickAMenuElement(3);
			AdminPage.toMFrame(4);
			AdminPage.getlistProCell(2, 5).click();
			webDriver.waitTimeThread(3);
			webDriver.awayFrame();
			// 修改内容
			String titleS = "testNewsEditTow";
			AdminPage.sendTxtToInput(0, titleS);
			AdminPage.toMFrame(4);

			webDriver.findElementByXpath("//input[3]").click();// 提交
			webDriver.waitTimeforPageLoad(2);
			// webDriver.waitTimeThread(2);
			webDriver.awayFrame();
			AdminPage.clickAMenuElement(3);
			AdminPage.toMFrame(4);
			// webDriver.captureScreenshot("testNewsEdit");
			webDriver.waitTimeThread(1);
			WebElement titleElement = AdminPage.getlistProCell(2, 2);

			assertEquals(titleS, titleElement.getText());

			webDriver.awayFrame();
		} catch (Exception e) {

			logger.error("testNewsEdit error:", e);
			webDriver.captureScreenshot("testNewsEdit");
		}

	}

	// 3.1添加竞赛
	/** 添加竞赛 **/

	@AfterTest
	public void afterTest() {
		try {
			// 点了“管理”就不用点击“注销”了
			// HeadPage.clickALOR(5);
			webDriver.browserEnd();
		} catch (Exception e) {
			logger.error("afterClassTest error:", e);
		}
	}

}
