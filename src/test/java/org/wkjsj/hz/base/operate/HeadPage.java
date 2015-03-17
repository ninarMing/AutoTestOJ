package org.wkjsj.hz.base.operate;


import java.util.List;


import org.openqa.selenium.WebElement;
import org.wkjsj.hz.base.webDriver.Webdriver;

public class HeadPage {
	Webdriver driver;
	// Logger logger = Logger.getLogger(HeadPage.class);
	public HeadPage(Webdriver driver) {

		this.driver = driver;
	}
	
	public void clickElement(WebElement element){
		if(element!=null){
		element.click();
		driver.waitTimeforPageLoad(3);
		}else{
	//		logger.error("没有这个element");
		}
		
	}
	
	/**********************************************************************************************/
	// 获取所有的菜单名
	public List<WebElement> menuNum() {
		// 定位多了个【划词翻译 开启】里面的span的class="btn"
		return driver.findElementsByClassName("btn");

	}

	// 获取登录和注册
	public List<WebElement> loginRe() {

		return driver.findElementsByXpath("//*[@id='profile']/a");
	}
	/**********************************************************************************************/
	/*
	 *主页 i = 1
	 *讨论版 i = 2
	 *问题 i = 3
	 *状态  i = 4
	 * 排名i = 5
	 * 竞赛&作业i = 6
	 * 名校联赛i = 7
	 * 常见问题i = 8
	 * 
	 * */
	public void clickMenuElement(int i){
		WebElement menuElement = driver.findElementByXpath("//*[@id='menu']/a["+i+"]");
		this.clickElement(menuElement);
	}
	


	

	
	

	/**********************************************************************************************/
	/*
	 * 点击"登录"/"修改账号" i = 1
	 * 点击"注册" i = 2
	 * 点击"注销" i = 5
	 * 点击"管理" i = 6
	 * 
	 * */
	public void clickALOR(int i) {
		driver.waitTimeforPageLoad(3);
		String xpath = "//*[@id='profile']/a["+i+"]";
		WebElement ALORElement = driver.findElementByXpath(xpath);
		
		this.clickElement(ALORElement);

	}
	
	/**********************************************************************************************/
	/*
	 * @param i 为table的行号
	 * @param j 为table的列号
	 *  j = 2 为“用户ID”
	 *  j = 3 为“问题”
	 *  j = 4 为“结果”
	 *  j = 5 为“内存”
	 *  j = 6 为“耗时”
	 *  j = 7为“语言”但是a[1]为“语言”，a[2]为“Edit”[当前用户与提交用户一致时]
	 *  j = 8为“代码长度”
	 *  j = 9为“提交时间”
	 * */
	public WebElement findResultCell(int i,int j){
		driver.waitTimeThread(2);
		String xpath = "//*[@id='result-tab']/tbody/tr["+i+"]/td["+j+"]";
		WebElement element = driver.findElementByXpath(xpath);
		return element;
	}
	/*
	 * @param i 为table的行号
	 * @param j 为table的列号
	 *  j = 2 为“题目编号”
	 *  j = 3 为“标题”
	 *  j = 4 为“来源”
	 *  j = 5 为“正确数量”
	 *  j = 6 为“提交数量”
	 * */
	public WebElement findProblemCell(int i,int j){
		driver.waitTimeThread(2);
		String xpath = "//*[@id='problemset']/tbody/tr["+i+"]/td["+j+"]/div/a";
		WebElement element = driver.findElementByXpath(xpath);
		return element;
	}
	
	
	public void clickMainProblem(int i,int j){
		driver.waitTimeforPageLoad(3);
	//	String xpath = "//*[@id='problemset']/tbody/tr["+i+"]/td["+j+"]/div/a";
		WebElement problemElement = this.findProblemCell(i, j);
		this.clickElement(problemElement);
		
	}
	
	public void clickMainResult(int i,int j){
		driver.waitTimeforPageLoad(3);
	//	String xpath = "//*[@id='result-tab']/tbody/tr["+i+"]/td["+j+"]/div/a";
		WebElement problemElement = this.findResultCell(i, j);
		this.clickElement(problemElement);
	}
	
	
	/**********************************************************************************************/	
	/*
	 * s为submit  			j = 1
	 * s为state   			j = 2
	 * d为discuss 			j = 3
	 * E为管理员的Edit		j = 4,,未实现，先不管
	 * T为管理员的TestData	j = 5,,未实现，先不管
	 * i = 1 代表在题目下面的那三个SSD
	 * i = 2 代表在来源下面的那三个SSD
	 * */
	
	public void clickSSD(int i,int j){
		String xpath = "//*[@id='main']/center["+i+"]/a["+j+"]";
		WebElement element = driver.findElementByXpath(xpath);
		this.clickElement(element);
		
	}
	
	/************************************************************************************************/
	//提交
	public void clickSubmit(){
		this.clickElement(driver.findElementById("Submit"));
		if(driver.isAlertPresent()){
			driver.closeAlert();
		}
		driver.waitTimeforPageLoad(15);
	}


}
