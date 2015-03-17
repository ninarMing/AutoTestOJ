package org.wkjsj.hz.base.operate;

import static org.testng.AssertJUnit.assertEquals;

import org.wkjsj.hz.base.webDriver.Webdriver;
import org.wkjsj.hz.base.webDriver.WebdriverBaseCase;



public class CommonFunction extends WebdriverBaseCase {
	LoginPage LoginPage;
	//before的一些方法，初始化的
	public void initialFunction(Webdriver driver,String url, int sec){
		
		driver.browserFFDriver();
		driver.gotoUrl(url);
		driver.browserMax();
		driver.waitTimeforPageLoad(sec);	
	}
	
	public void initialLogin(Webdriver driver,String url,int sec,String userPwd){
		initialFunction(driver,url,sec);
		url = driver.getTestedUrl();
		driver.navigateUrl(url + "loginpage.php");// 跳转到指定页面
		driver.waitTimeforPageLoad(2);
		assertEquals("LOGIN",driver.getTitle());
		LoginPage = new LoginPage(driver);
        LoginPage.login(userPwd);
        driver.waitTimeforPageLoad(sec);
				
		
	}
	
	public void initialLogin(Webdriver driver,String url,int sec,String user,String password){
		initialFunction(driver,url,sec);
		url = driver.getTestedUrl();
		driver.navigateUrl( url +"loginpage.php");
		LoginPage = new LoginPage(driver);
		LoginPage.login(user, password);
		driver.waitTimeforPageLoad(sec);
	}

	

}
