package org.wkjsj.hz.base.operate;


import org.openqa.selenium.WebElement;
import org.wkjsj.hz.base.webDriver.Webdriver;

public class AdminPage {
	Webdriver driver;
	// Logger logger = Logger.getLogger(AdminPage.class);

	public AdminPage(Webdriver driver) {
		this.driver = driver;
	}

	/**********************************************************************************************/
	// 常用的代码
	public void clickElement(WebElement element) {
		if (element != null) {
			element.click();
			driver.waitTimeforPageLoad(3);
		} else {
		//	logger.error("没有这个element");
		}

	}

	/********************左侧栏的一些选项**************************************************************************/
	/*
	 *  i = 1查看前台 ; i=2 添加新闻 ;i = 3新闻列表; i = 4添加问题 ; i = 5问题列表；i =6添加竞赛&作业 ;i = 7竞赛&作业列表 ;
	 *  i = 8比赛队账号生成器; i = 9 设置公告 ;i = 10 修改密码 ;i = 11重判题目; i = 12添加权限; i = 13权限列表 ;
	 *  i = 14  转移源码; i = 15导出问题; i = 16导入问题 ;i = 17更新数据库 ;i = 18 HUSTOJ; i = 19 FreeProblemSet;
	 * i = 20 ACM俱乐部免费开通校级OJ服务器 
	 */
	public void clickAMenuElement(int i) {
		// 切换到第一个fram里
		WebElement menuElement = driver
				.findElementByXpath("//frameset/frame[@name='menu']");
		driver.toFrame(menuElement);
		WebElement adminElement = driver.findElementByXpath("//ol/li[" + i
				+ "]/a/b");
		clickElement(adminElement);
		driver.awayFrame();
		driver.waitTimeforPageLoad(6);
	}

	/**********add problem里fram的定位的问题****************************************************************************/
	/*
	 * i=0 description
	 * i=1 input
	 * i=2 output
	 * i=3 Hint
	 * i=4 到fram【main】里
	 */
	// 切换到主frame里
	public void toMFrame(int i) {
		WebElement frameElement;
		String xpath = "";
		switch (i) {
	// 到里面一层的
		case 0:
			xpath = "//iframe[@id='description___Frame']";
			break;
		case 1:
			xpath = "//iframe[@id='input___Frame']";
			break;
		case 2:
			xpath = "//iframe[@id='output___Frame']";
			break;
		case 3:
			xpath = "//iframe[@id='hint___Frame']";
			break;
			//切换到外层
		case 4:
			xpath = "//frameset/frame[@name='main']";
			break;
			//添加新闻的时候
		case 5:xpath = "//iframe[@id='content___Frame']";
		default:
			;
		}
		frameElement = driver.findElementByXpath(xpath);
	/*	if (frameElement == null) {
			logger.error("没有找到这个element" + i + xpath);
		}*/
		driver.toFrame(frameElement);
		driver.waitTimeThread(1);
		driver.waitTimeforPageLoad(2);
	}

	/******************add problem 定位相应的元素****************************************************************************/
	/*定位Input的元素
	 *  
	 * @param i=0 title 
	 *        i=1 Time Limit
	 *        i=2 Memory Limit
	 */
	public WebElement getInputElement(int i) {
		i = i+2;
		String xpath = "//form/p[" + i + "]/input";
		WebElement inputElement = driver.findElementByXpath(xpath);

		return inputElement;
	}
	
	/*定位textarea 是在main的frame
	 * @param j=0 SampleInput 
	 *        j=1 Sample Output 
	 *        j=2 Test Input 
	 *        j=3 Test Output
	 * Output j=4 Source
	 */
	public WebElement getTxtElement(int i) {
        if(i!=4){
    		i=i+9;	
        }else{
        	i = i+11;
        }

		String xpath = "//form/p[" + i + "]/textarea";
		WebElement txtElement = driver.findElementByXpath(xpath);
		if(txtElement==null){
			//	System.out.println(i+"xpath"+xpath);
		}
		return txtElement;
	}

	/*****************Add problem里输入problem的具体信息**************************************************************/
	/* 往input里输入信息
	 * 
	 * @param i=0 title 
	 *        i=1 Time Limit
	 *        i=2 Memory Limit
	 */
	public void sendTxtToInput(int i,String content){
		this.toMFrame(4);
		
		if( this.getInputElement(i).getText()!=""){
		this.getInputElement(i).clear();	
		}
		this.getInputElement(i).sendKeys(content);	
		driver.waitTimeforPageLoad(3);
		driver.waitTimeThread(1);
		driver.awayFrame();
		
		
		
	}
	
	/*往ckeditor里输入信息
	 * 
	 * @param i=0 到ckeditor的那一层里 description 
	 *        i=1 input 
	 *        i=2 output 
	 *        i=3 Hint
	 * 处理完记得切换回默认的框架里
	 * 
	 */
	public void sendTxtToCkeditor(int i, String content) {
		this.toMFrame(4);
		// 切换到某个frame里
		this.toMFrame(i);

		WebElement framTElement = driver.findElementByTagName("iframe");
		driver.toFrame(framTElement);
		// 定位到textarea
		WebElement txtElement = driver.findElementByXpath("//body/p");
		if(txtElement.getText()!=""){
			//不知道为什么不能清楚数据
			//txtElement.clear();
		}
		driver.waitTimeforPageLoad(2);
		// 输入文字
		txtElement.sendKeys(content);
		driver.waitTimeforPageLoad(10);
		// 回复到初始状态，回到默认的frame里
		driver.awayFrame();

	}


	/*
	 *往textarea里输入信息 
	 *  @param j=0 SampleInput 
	 *        j=1 Sample Output 
	 *        j=2 Test Input 
	 *        j=3 Test Output
	 *        j=4 Source
	 * */
	public void sendTxtToTxtarea(int i,String content){
		
        this.toMFrame(4);
        WebElement element = this.getTxtElement(i);
        if(element!=null){
		this.getTxtElement(i).clear();	
		this.getTxtElement(i).sendKeys(content);
		driver.waitTimeforPageLoad(3);
        }
		driver.awayFrame();
		
		
	}
	
	
	/*****************list problem的元素定位**************************************************************/
	/*problem列表里的信息
	 * @param i 为行数，j为列数
	 *  i = 3 为第一行的题目
	 *  j = 2 title ; j = 4 Status[Available/Reserved] ; j = 5 Delete; j = 6 Edit;j = 7 TestData;
	 * 
	 * news列表里的信息
	 * @param i为行数，j为列数
	 * i = 2为第一行的新闻
	 * j = 2 title;j = 4 status;j = 5 Edit;
	 * */
	public WebElement getlistProCell(int i,int j){
		String xpath = "//center[2]/table/tbody/tr["+i+"]/td["+j+"]/a";
		WebElement cell = driver.findElementByXpath(xpath);
		return cell;
		
	}

}
