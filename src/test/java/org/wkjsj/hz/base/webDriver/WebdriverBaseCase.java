package org.wkjsj.hz.base.webDriver;

/**
 *用于日志的记录 
 * 
 **/
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;
import org.wkjsj.hz.base.util.*;

public class WebdriverBaseCase {
	protected static WebDriver driver;
//D:/Program Projects/MavenProjects/SeleniumProjects/AutoTestOJ/test-output/Capture/
	public final  String className = this.getClass().getName();
	public  String capturePath ="./test-output/Capture/ " ;
	public long beforeClassStarts = 0;
	public long afterClassStops = 0;
	public long beforeTestStarts = 0;
	public long afterTestStops = 0;

//	private PropUtil PropUtil = new PropUtil("./src/report.properties");
	public  Logger logger = Logger.getLogger(WebdriverBaseCase.class);

	public  void beforeClassTest() {
		String begins = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS");
		beforeClassStarts = System.currentTimeMillis();
		logger.info("======" + begins + ":测试【" + className + "】开始======");
	}

	public void afterClassTest() {
		String ends = DateTimeUtil.formatedTime("yyyy-MM-dd HH:MM:ss:SSS");
		afterClassStops = System.currentTimeMillis();
		logger.info("======" + ends + ":测试【" + className + "】结束======");
		logger.info("======本次测试运行消耗时间"
				+ (double) (afterClassStops - beforeClassStarts) / 1000
				+ "秒！======");
	}

	public void beforeTest(String methodName) {
		String begins = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss：SSS");
		beforeTestStarts = System.currentTimeMillis();
		logger.info("======" + begins + "案例【" + className + "." + methodName
				+ "】开始======");
	}

	public String afterTest(String methodName, boolean isSucsd) {
		String ends = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS");
		String captureName = "";
		if (isSucsd) {
			logger.info("案例【" + className + "." + methodName + "】运行通过！");
		} else {
			String dateTime = DateTimeUtil.formatedTime("-yyyyMMdd-HHmmssSSS");
			StringBuffer sb = new StringBuffer();
			captureName = sb.append(capturePath).append(className).append(".")
					.append(methodName).append(dateTime).append(".png")
					.toString();
			this.captureScreenshot(captureName);
			logger.error("案例【" + className + "." + methodName
					+ "】运行失败，请查看截图快照：" + captureName);
		}
		logger.info("======" + ends + ":案例【" + className + "." + methodName
				+ "】结束======");
		afterTestStops = System.currentTimeMillis();
		logger.info("======本次案例运行消耗时间"
				+ (double) (afterTestStops - beforeTestStarts) / 1000
				+ "秒！======");
		return captureName;

	}

	/*
	 * 截取屏幕截图并保存到指定路径
	 * 
	 * @param name 保存屏幕截图名称
	 * 
	 * @return无
	 */

	public void capture(String name) {
		String dateTime = DateTimeUtil.formatedTime("-yyyyMMdd-HHmmssSSS");
		StringBuffer sb = new StringBuffer();
		String captureName = sb.append(capturePath).append(name)
				.append(dateTime).append(".png").toString();
		this.captureScreenshot(captureName);
		logger.debug("请查看截图快照：" + captureName);

	}

	/**
	 * 截取屏幕截图并保存到指定路径
	 * 
	 * @param filePath
	 *            保存屏幕截图完整文件名称及路径
	 * @return 无
	 * 
	 * **/
	public void captureScreenshot(String filepath) {
		File screenShotFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShotFile, new File(filepath));
		} catch (Exception e) {
			logger.error("保存屏幕截图失败，失败信息：", e);
		}
	}
	
	public void operationCheck(String methodName, boolean isSucsd) {
		if (isSucsd) {
			logger.info("method【" + methodName + "】运行通过！");
		} else {
			logger.error("method【" + methodName + "】运行失败！");
		}
	}

}
