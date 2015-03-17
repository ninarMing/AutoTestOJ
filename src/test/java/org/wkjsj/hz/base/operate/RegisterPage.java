package org.wkjsj.hz.base.operate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.wkjsj.hz.base.file.GetCSVContent;
import org.wkjsj.hz.base.webDriver.Webdriver;


public class RegisterPage {
	protected Webdriver driver;
	private WebElement nowElement;
	String[] infoStr = GetCSVContent.getLinkName(
			"src/main/resources/fixedData/RegisterInfoData.csv", 6); // 期望的menu
																		// Name

	public RegisterPage(Webdriver driver) {
		this.driver = driver;
	}

	/**********************************************************************************************/
	// 用户注册
	public void register(String userInfo, int size) {
		for (int i = 2; i < size; i++) {
			nowElement = driver.findElementById("main").findElement(
					By.xpath("//tr[" + i + "]/td[2]/input"));
			nowElement.sendKeys(userInfo); // 除了验证码的输入框没有输入，其他都输入了
			driver.waitTimeThread(1);
		}

	}

	public void register(int size) {
		for (int i = 2; i < size; i++) {
			nowElement = driver.findElementById("main").findElement(
					By.xpath("//tr[" + i + "]/td[2]/input"));
			nowElement.sendKeys(infoStr[i - 2]); // 除了验证码的输入框没有输入，其他都输入了
			driver.waitTimeThread(1);
		}
	}

	/**********************************************************************************************/
	// 指定用户变成管理员
	public int  registAdmin(Connection con,String query,String user_id) {
		int i = 0 ;
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(query);
			pstm.setString(1, user_id);
			i = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;


	}
	/**********************************************************************************************/


}
