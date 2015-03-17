package org.wkjsj.hz.base.operate;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;
import org.wkjsj.hz.base.webDriver.Webdriver;

public class Status {
	Webdriver driver;
	 Logger logger = Logger.getLogger(Status.class);
	public Status(Webdriver driver) {

		this.driver = driver;
	}
	
	public void sendContTocode(String content){
		
		driver.waitTimeforPageLoad(30);
		
		//iframe里的“代码框”里的操作
		String iframPath ="//iframe[@id='frame_source']";
		WebElement iframElement = driver.findElementByXpath(iframPath);
	   
		//切换到iframe里
		driver.toFrame(iframElement);
		
		driver.waitTimeforPageLoad(5);
		String txtareaPath ="//textarea[@id='textarea']";
		WebElement txtareaElement = driver.findElementByXpath(txtareaPath);
		txtareaElement.clear();
		txtareaElement.sendKeys(content);
		driver.waitTimeforPageLoad(20);
		
		//切换回默认的页面
		driver.awayFrame();
			    
		
	}
	
	public String getCodeShortAsert(){
		
		
		String asertText = driver.findElementById("main").getText().trim();
		asertText = asertText.substring(0,15);
		return asertText;
	}
	
	public WebElement getLangElement(int i){
		i = i+1;
		String langPath = "//*[@id='language']/option"+"[" + i + "]";
		WebElement langElement = driver.findElementByXpath(langPath);
		return langElement;
		
	}
	
	public List<WebElement> getLangElements(){
		String langPath = "//*[@id='language']/option";
		List<WebElement> langElements = driver.findElementsByXpath(langPath);
		return langElements;
		
	}

}
