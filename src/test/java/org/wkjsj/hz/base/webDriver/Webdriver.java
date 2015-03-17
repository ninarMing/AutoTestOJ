package org.wkjsj.hz.base.webDriver;
/**
 * webdriver的一些常用方法
 * 
 * */
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.wkjsj.hz.base.util.DateTimeUtil;


public class Webdriver {
	private WebDriver driver;
	private String ip = "192.168.0.101";
	
	public String getIp(){
	
		return ip;
	}
	
	public String getTestedUrl(){
		
		return ("http://"+ip+"/JudgeOnline/");
	}
	
	
	public Webdriver(WebDriver driver){
		this.driver = driver ;
		
	}
	public void captureScreenshot(String name){
		//	String path = "./test-output/Capture/";
		    name = name+"-";
			String path="../test-output/Capture/";
			String dateTime = DateTimeUtil.formatedTime("yyyyMMdd-HHmmssSSS");
			StringBuffer sb = new StringBuffer();
			path = sb.append(path).append(dateTime)
					.append(name).append(".png").toString();
			File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			System.out.println("1");
			try {
				FileUtils.copyFile(screenShotFile, new File(path));
			} catch (Exception e) {
			//	logger.error("保存屏幕截图失败，失败信息：", e);
			}
			
		}

	
	
	
	/******************** 启动浏览器 ********************/
	/*chrome*/
	public  void browserCRStart(){
		
		System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe");
		ChromeOptions options = new ChromeOptions();
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY,options);
		driver = new ChromeDriver();
	    
	}
	/*IE*/
	public void browserIEDriver(){
		
		System.setProperty("webdriver.ie.bin","C:/Program Files (x86)/Internet Explorer/iexplore.exe");
		driver = new InternetExplorerDriver();
		
	}
	/*FireFox*/
	public void  browserFFDriver(){
		
		//打开指定路径的firefox
		System.setProperty("webdriver.firefox.bin", "D:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		ProfilesIni allProfiles = new ProfilesIni();
		//"AutoTestOJ" is the new profile just created
		FirefoxProfile profile = allProfiles.getProfile("AutoTestOJ");
		driver = new FirefoxDriver(profile);
		
		
	}
	
	

	
	/******************** 浏览器基本操作********************/
	
	/*窗口最大化*/
	public  void browserMax(){
		
		driver.manage().window().maximize();
	}
	
	/*关闭浏览器*/
	public void browserEnd(){
		
		driver.quit();
	}
	
	/*对浏览器进行后退的操作*/
	public void browserBack(){
		driver.navigate().back();
		this.waitTimeforPageLoad(20);
	}
	/*对浏览器进行前进的操作*/
	public void browserForward(){
		driver.navigate().forward();
	}
	
	/*打开指定地址*/
	public  void  gotoUrl(String url){
		
		driver.get(url);
	}
	 /*到指定页面能获取页面*/
	public void navigateUrl(String url){
		driver.navigate().to(url);
		this.waitTimeforPageLoad(3);
	}
	

	
	/******************** 页面等待 ********************/
	/*页面等待加载,,,得了解两者的区别*/
	public void waitTimeforPageLoad(int sec){
		//只适用于firefox浏览器
		driver.manage().timeouts().pageLoadTimeout(sec, TimeUnit.SECONDS);
		
	}
	//线程休眠
	public void waitTimeThread(int sec){
		
		try {
			
			Thread.sleep(1000*sec);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/******************** 页面基本操作********************/
	/*获取页面标题*/
	public String getTitle(){
		
		return driver.getTitle();
	}


	

	
	//获取当前的URL
	public String getCurrentUrl(){
		return driver.getCurrentUrl();
	}
	
	
	
	//登录时点击的“提交”
	public void submitClick(){
		WebElement element =this.findElementByName("submit") ;
		element.click();
		this.waitTimeThread(4);
	}
	
	/*有待商榷*/
	public void cleanAllCookies(){
		
		driver.manage().deleteAllCookies();
	}
	
	public String getHandle(){
		return driver.getWindowHandle();
	}
	
	
	/*******************定位元素*******************/
	/*切换到iframe*/
	public void toFrame(WebElement frame){
		driver.switchTo().frame(frame);
		
	} 
	public void toFrame(int i){
		driver.switchTo().frame(i);
	}
	
	/*从iframe里切换到默认页面*/
	public void awayFrame(){
		driver.switchTo().defaultContent();
	}
	
	/*元素存在判断*/
	/*元素是否存在*/
	public boolean isElementExists(By by){
		try{
			driver.findElement(by);
			return true;
		}catch(NoSuchElementException e){
			
		}
		return false;
	}
	/*元素列表是否存在*/
	public boolean isElementsExists(By by){
		return (driver.findElements(by).size()>0)?true:false;
	}
	
	/*查找元素列表中的特定的第几个元素*/
	public WebElement findByElements(By by,int index){
		WebElement element = null;
		for(int i=0;i<3;i++){
		if(this.isElementsExists(by)){
			this.waitTimeThread(3);
			element = driver.findElements(by).get(index) ;
			break;
		}
		}
		
		return element;
	}
	
	/*查找元素*/
	public WebElement findElementById(String id){
		WebElement element =null;
		for(int i=0;i<3;i++){
		if(this.isElementExists(By.id(id))){
			this.waitTimeThread(3);
			element = driver.findElement(By.id(id));
			break;
		}
		}
		return element;
		
	} 
	public WebElement findElementByName(String name){
		WebElement element = null;
		for(int i=0;i<3;i++){
		if(this.isElementExists(By.name(name))){
			this.waitTimeThread(3);
			element = driver.findElement(By.name(name));
			break;
		}
		}
		return element;
	}
	
	public WebElement findElementByXpath(String xpath){
		WebElement element = null;
		for(int i=0;i<3;i++){
		if(this.isElementExists(By.xpath(xpath))){
			this.waitTimeThread(3);
			element = driver.findElement(By.xpath(xpath));
			break;
		}
		}
		return element;
		
	}
	public WebElement findElementByTagName(String tagName){
		WebElement element = null;
		for(int i=0;i<3;i++){
		if(this.isElementExists(By.tagName(tagName))){
			this.waitTimeThread(3);
			element = driver.findElement(By.tagName(tagName));
			break;
		}
		}
		return element;
		
	}
	
	

	/*find table's cell*/
	/*
	 * 从一个table的单元格得到文本值，参数tableCellAddress的格式为row.column，行列从0开始。
	 * @param table 之前根据实际情况获取到的一个table Element
	 * @param tableCellAddress 一个单元格地址，如.“1.4”
	 * @return 从一个table的单元格中得到文本值
	 * */
	/*
	public WebElement getCell(List<WebElement> table,int i,int j){
		//得到table里的行对象，并得到所要查询的行对象。
	//	table.get(index)
  	//	List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement row = table.get(i);
	//	WebElement row = rows.get(i);
		List<WebElement> cells;
		WebElement cell = null;
		//列里面有"<th>"、"<td>"两种标签，所以分开处理。
		if(this.isElementsExists(By.tagName("th"))){
			cells = row.findElements(By.tagName("th"));
			cell = cells.get(j);
		}
		if(this.isElementsExists(By.tagName("td"))){
			cells = row.findElements(By.tagName("td"));
			cell = cells.get(j);
		}
		return cell;
		
	}
	
	
	*/
	
	
	
	/*查找元素组*/
	public List<WebElement> findElementsByTagName(String tagName){
		List<WebElement> elements =null;
		for(int i=0;i<3;i++){
		if(this.isElementsExists(By.tagName(tagName))){
			this.waitTimeThread(3);
			elements = driver.findElements(By.tagName(tagName));
		break;
		}
		}
		return elements;
		
	}
	
	public List<WebElement> findElementsByXpath(String xpath){
		
		List<WebElement> elements = null;
		for(int i=0;i<3;i++){
		if(this.isElementsExists(By.xpath(xpath))){
			this.waitTimeThread(3);
			elements = driver.findElements(By.xpath(xpath));
			break;
		}
		}
		return elements;
		
	}
	
	public List<WebElement>findElementsByClassName(String className){
		List<WebElement> elements = null;
		for(int i=0;i<3;i++){
		if(this.isElementsExists(By.className(className))){
			this.waitTimeThread(3);
			elements = driver.findElements(By.className(className));
			break;
			}
		}
		return elements;
	}
	

	
	
	
//	public WebElement findElementBy
	
	//alert
	public boolean isAlertPresent(){
		try{
		Alert alert = driver.switchTo().alert();
		return true;
		}catch(NoAlertPresentException EX){
			return false;
		}
	}
	
	public String getAlertPresent(){
		String alertText="";
		if(isAlertPresent()){
			alertText = driver.switchTo().alert().getText();
		}
		return alertText;
	}
	
	public void closeAlert(){
		
		if(isAlertPresent()){
			driver.switchTo().alert().accept();
		}
	}
	

	
	
}

