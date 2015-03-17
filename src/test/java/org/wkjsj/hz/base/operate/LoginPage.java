package org.wkjsj.hz.base.operate;

import org.openqa.selenium.By;
import org.wkjsj.hz.base.webDriver.Webdriver;

public class LoginPage {
	private Webdriver driver;

	public LoginPage(Webdriver driver) {
		this.driver = driver;
	}

	// 登录
	public void login(String userPwd) {
		for (int i = 1; i < 3; i++) {
			driver.findElementById("main")
					.findElement(By.xpath("//tr[" + i + "]/td[2]/input"))
					.sendKeys(userPwd);
			driver.waitTimeThread(3);

		}
		driver.submitClick();
		if (driver.isAlertPresent()) {
			driver.closeAlert();
			driver.waitTimeThread(3);
			// alertText="UserName or Password Wrong!"
			this.login(userPwd);

		}

	}

	public void login(String user, String password) {

		driver.findElementByName("user_id").sendKeys(user);
		driver.findElementByName("password").sendKeys(password);
		driver.submitClick();
		if (driver.isAlertPresent()) {
			driver.closeAlert();
			driver.waitTimeThread(3);
			// alertText="UserName or Password Wrong!"
			this.login(user, password);

		}

	}

}
