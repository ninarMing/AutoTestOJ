package org.wkjsj.hz.testcase.page;

/*
 * 以学生的身份登录
 * 
 * 显示“等待”、“等待重判”、“编译中”、“运行并评判”
 * 这几种状态在正常情况下不是最终状态，所以没有写关于这部分的用例
 * 人工来验证这些状态或者是用别的工具模拟并发才容易出现这些状态
 * */
import static org.testng.AssertJUnit.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.log4testng.Logger;
import org.wkjsj.hz.base.file.GetTestedCode;
import org.wkjsj.hz.base.operate.CommonFunction;
import org.wkjsj.hz.base.operate.HeadPage;
import org.wkjsj.hz.base.operate.Status;
import org.wkjsj.hz.base.webDriver.Webdriver;
import org.wkjsj.hz.base.webDriver.WebdriverBaseCase;

public class SubmitProblemTest extends WebdriverBaseCase {

	protected Webdriver webDriver;
	private String userPwd = "HiAuto";
	private HeadPage HeadPage;
	private CommonFunction CommonFunction;
	private GetTestedCode GetTestedCode;
	private Status Status;
	String problemTitle = "A+B Problem";
	String path = "../src/main/resources/codeData"; // 测试数据的路径
	 Logger logger = Logger.getLogger(SubmitProblemTest.class);
	String url; // 被测地址

	@BeforeTest
	public void beforeTest() {
		try {
			webDriver = new Webdriver(driver);
			String url = webDriver.getTestedUrl() + "problemset.php";
			HeadPage = new HeadPage(webDriver);
			Status = new Status(webDriver);
			GetTestedCode = new GetTestedCode();
			CommonFunction = new CommonFunction();
			// 如果下面的problem的数量很多，页面加载的数量很慢
			CommonFunction.initialLogin(webDriver, url, 4, userPwd);
			assertEquals("Problem Set", webDriver.getTitle());
		} catch (Exception e) {
			logger.error("beforeTest error:", e);
		}

	}

	// 1.找到作为测试的题目
	@Test
	public void testClickProblem() {
		try {
			// 以problem中的A+B为实例,编号为1000的题目是1000
			HeadPage.clickMainProblem(1, 3);
			assertEquals(problemTitle, webDriver.getTitle());
			webDriver.waitTimeforPageLoad(3);
		} catch (Exception e) {
			logger.error("testClickProblem error:", e);
			webDriver.captureScreenshot("testClickProblem");

		}
	}

	// 2.到题目的详情页里
	@Test(dependsOnMethods = { "testClickProblem" })
	public void testClickSubmit() {
		try {
			HeadPage.clickSSD(1, 1);
			webDriver.waitTimeforPageLoad(3);
			assertEquals("提交", webDriver.getTitle());
			// assertEquals("http://192.168.1.127/JudgeOnline/submitpage.php?id=1000",webDriver.getCurrentUrl());
		} catch (Exception e) {
			logger.error("testClickSubmit error:", e);
			webDriver.captureScreenshot("testClickSubmit");

		}
	}

	/** 不同语言包括：C、C++、Pascal、Java、Schema、Clang、Clang++、Lua **/

	/************************** 输入不同的代码字符数 ***********************************/
	// 3.1不同语言提交的代码为空
	//@Test(dependsOnMethods = { "testClickSubmit" })
	public void testEnterEmpty() {
		try {
			webDriver.waitTimeforPageLoad(20);
			WebElement element;

			List<WebElement> selectElements = Status.getLangElements();

			for (int i = 0; i < selectElements.size(); i++) {

				element = Status.getLangElement(i);
				// System.out.println(element.getText());
				HeadPage.clickElement(element);// 语言选择
				HeadPage.clickSubmit(); // 提交

				String asertText = Status.getCodeShortAsert();

				assertEquals("Code too short!", asertText);

				webDriver.browserBack();

			}
		} catch (Exception e) {
			logger.error("testEnterEmpty error:", e);
			webDriver.captureScreenshot("testEnterEmpty");

		}
	}

	// 3.2默认的语言C情况下，提交的代码长度为1时太短
	//@Test(dependsOnMethods = { "testClickSubmit" })
	public void testEnterOne() {
		try {
			String content = "W";
			Status.sendContTocode(content);
			WebElement element = Status.getLangElement(1);
			// System.out.println(element.getText());
			HeadPage.clickElement(element);// 语言选择
			HeadPage.clickSubmit(); // 提交

			String asertText = Status.getCodeShortAsert();

			assertEquals("Code too short!", asertText);

			webDriver.browserBack();
		} catch (Exception e) {
			logger.error("testEnterOne error:", e);
			webDriver.captureScreenshot("testEnterOne");

		}

	}

	// 3.3默认的编程语言C情况下，提交的代码长度为2时[2是边界值]
	//@Test(dependsOnMethods = { "testClickSubmit" })
	public void testEnterTwo() {
		try {
			webDriver.waitTimeforPageLoad(40);
			WebElement element = Status.getLangElement(1);
			// System.out.println(element.getText());
			HeadPage.clickElement(element);// 语言选择
			String content = "Hi";
			String result = "编译错误";

			String asertText = this.getStatusText(content, result);
			assertEquals(result, asertText);
			webDriver.browserBack();
		} catch (Exception e) {
			logger.error("testEnterTwo error:", e);
			webDriver.captureScreenshot("testEnterTwo");

		}

	}

	public String getStatusText(String content, String result) {
		Status.sendContTocode(content);

		HeadPage.clickSubmit(); // 提交

		assertEquals("状态", webDriver.getTitle());
		/*
		 * 当某个状态获取不到时，尝试将休眠的时间适当的调长webDriver得休眠15秒，因为这个状态时时改变，经过编译，运行并判断
		 */
		webDriver.waitTimeThread(40);

		WebElement resultElement = HeadPage.findResultCell(1, 4);
		String asertText = resultElement.getText();
		// 增加多次寻找相应的元素
		for (int i = 0; i < 5; i++) {
			if (asertText != result) {
				resultElement = HeadPage.findResultCell(1, 4);
				asertText = resultElement.getText();
			} else {
				break;
			}
		}

		return asertText;

	}

	/*
	 * @param filePath为测试的状态类型的目录路径
	 * 
	 * @param status 为期望的运行结果
	 * 
	 * @param seconds 为等待真是运行结果时webdriver休眠的时间， 如果期望结果与实际结果不符合时，可以适当的调整休眠时间
	 */
	public void setCodeSituation(String filePath, String result, int seconds) {
		webDriver.waitTimeThread(10);
		WebElement element;
		String content, asertText;
		// String allPath = path+"/code-right";
		String pathD, pathDF;
		List<String> list = new ArrayList<String>();
		List<String> listFile = new ArrayList<String>();
		list = GetTestedCode.getDirList(filePath);
		// C、C++等文件夹的循环
		for (int i = 0; i < list.size(); i++) {
			pathD = filePath + "/" + list.get(i);
			listFile = GetTestedCode.getFileList(pathD);
			// C或者C++某一种语言里的具体的多个测试代码的循环
			for (int j = 0; j < listFile.size(); j++) {
				element = Status.getLangElement(i);
				HeadPage.clickElement(element);// 语言选择
				pathDF = pathD + "/" + listFile.get(j);
				content = GetTestedCode.getText(pathDF);

				// *下面的可以通过一个函数概括，但是会导致嵌套过多，就舍弃了，重复了一遍代码
				Status.sendContTocode(content);
				//为了论文能够截图
           //     webDriver.waitTimeThread(10);
                
				HeadPage.clickSubmit(); // 提交

				assertEquals("状态", webDriver.getTitle());
				/*
				 * 当某个状态获取不到时，尝试将休眠的时间适当的调长webDriver得休眠15秒，因为这个状态时时改变，经过编译，运行并判断
				 */
				webDriver.waitTimeThread(seconds);

				WebElement resultElement = HeadPage.findResultCell(1, 4);
				asertText = resultElement.getText();
				// 若找不到元素，增加5次寻找相应的元素
				for (int k = 0; k < 5; k++) {
					if (asertText != result) {
						resultElement = HeadPage.findResultCell(1, 4);
						asertText = resultElement.getText();
					} else {
						break;
					}
				}
				assertEquals(result, asertText);
				webDriver.browserBack();
			}

		}
	}

	/****************** 输入不同测试数据验证几种最终状态 ***************************/
	// 4.1 不同语言提交的代码答案“正确”
	@Test(dependsOnMethods = { "testClickSubmit" })
	public void testCodeRight() {
		try {
			String filePath = path + "/code-Right";
			String result = "正确";
			int seconds = 20;
			this.setCodeSituation(filePath, result, seconds);
		} catch (Exception e) {
			logger.error("testCodeRight error:", e);
			webDriver.captureScreenshot("testCodeRight");

		}

	}

	// 4.2 不同语言都显示“格式错误”
	@Test(dependsOnMethods = { "testClickSubmit" })
	public void testFormatError() {
		try {

			String filePath = path + "/code-formatError";
			String result = "格式错误";
			int seconds = 20;
			this.setCodeSituation(filePath, result, seconds);
		} catch (Exception e) {
			logger.error("testFormatError error:", e);
			webDriver.captureScreenshot("testCodeRight");

		}
	}

	// 4.3不同语言都显示“答案错误”
	@Test(dependsOnMethods = { "testClickSubmit" })
	public void testCodeError() {
		try {
			String filePath = path + "/code-Error";
			String result = "答案错误";
			int seconds = 25;
			this.setCodeSituation(filePath, result, seconds);
		} catch (Exception e) {
			logger.error("testCodeError error:", e);
			webDriver.captureScreenshot("testCodeError");

		}

	}

	// 4.4不同语言都显示“时间超限”
	@Test(dependsOnMethods = { "testClickSubmit" })
	public void testTimeOverRun() {
		try {
			String filePath = path + "/time-overRun";
			String result = "时间超限";
			int seconds = 40;
			this.setCodeSituation(filePath, result, seconds);
		} catch (Exception e) {
			logger.error("testTimeOverRun error:", e);
			webDriver.captureScreenshot("testTimeOverRun");
		}

	}

	// 4.5不同语言都显示“内存超限”
	@Test(dependsOnMethods = { "testClickSubmit" })
	public void testMemoryOverRun() {
		try {
			String filePath = path + "/memory-overRun";
			String result = "内存超限";
			int seconds = 30;
			this.setCodeSituation(filePath, result, seconds);
		} catch (Exception e) {
			logger.error("testMemoryOverRun error:", e);
			webDriver.captureScreenshot("testMemoryOverRun");

		}
	}

	// 4.6 不同语言都显示“输出超限”
	@Test(dependsOnMethods = { "testClickSubmit" })
	public void testOutputOverRun() {
		try {
			String filePath = path + "/output-overRun";
			String result = "输出超限";
			int seconds = 30;
			this.setCodeSituation(filePath, result, seconds);
		} catch (Exception e) {
			logger.error("testOutputOverRun error:", e);
			webDriver.captureScreenshot("testOutputOverRun");
		}

	}

	// 4.7 不同语言都显示“运行错误”
	@Test(dependsOnMethods = { "testClickSubmit" })
	public void testRunTimeError() {
		try {
			String filePath = path + "/run-timeError";
			String result = "运行错误";
			int seconds = 30;
			this.setCodeSituation(filePath, result, seconds);
		} catch (Exception e) {
			logger.error("testRunTimeError error:", e);
			webDriver.captureScreenshot("testRunTimeError");
		}

	}

	// 4.8 不同语言都显示“编译错误”
	@Test(dependsOnMethods = { "testClickSubmit" })
	public void testCodeCompileError() {
		try {
			String filePath = path + "/code-compileError";
			String result = "编译错误";
			int seconds = 30;
			this.setCodeSituation(filePath, result, seconds);
		} catch (Exception e) {
			logger.error("testCodeCompileError error:", e);
			webDriver.captureScreenshot("testCodeCompileError");
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
