package org.wkjsj.hz.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	
	/*
	 * 获取系统当前日期和时间并格式化为yyyyMMddHHmmss即类似20150310141523格式
	 * @param 无
	 * @return 系统当前日期和时间并格式化为yyyyMMddHHmmss即类似20150310141523格式
	 *
	 * */
	
	public static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	

	
	/*
	 * 获取系统当前日期和时间并格式化为yyyyMMddHHmmssSSS即类似20150310002728796格式
	 * @param 无
	 * @return 系统当前日期和时间并格式化为yyyyMMddHHmmssSSS即类似20150310002728796
	 * 
	 */
	
	
	public static String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}

	
	
	/*
	 * 获取系统当前日期并格式化为yyyyMMdd即类似20150310格式
	 * 
	 * @param 无
	 * @return 系统当前日期并格式化为yyyyMMdd即类似20150310格式
	 * */
	
	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	
	
	/*
	 * 获取系统当前时间兵格式化为HHmmss即类似142921
	 * @param 无
	 * @return 系统当前时间并格式化为HHmmss即类似142921格式 
	 * */


	public static String getCurrentTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(new Date());
	}
	
	/*
	 * 获取系统当前时间并格式化为HHmmssSSS即类似142921123格式
	 * @param 无
	 * @return 系统当前时间并格式化为HHmmssSSS即类似142921123格式
	 * */
	public static String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
		return sdf.format(new Date());
	}
	
	/*
	 * 根据自定义格式化获取系统当前时间
	 * @param format
	 * 时间格式化如 yyyy-MM-dd HH:mm:ss:SSS
	 * @return 根据自定义格式化返回系统当前时间
	 * */
	public static String formatedTime(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/*
	 * Qparam days 
	 * 
	 */
	public static String addDaysByFormatter(int days,String dateFormat){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, days);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
		
	}

	/**
	 * 
	 */
	 public static String addMonthsByFormatter(int months,String dateFormat){
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(new Date());
		 cal.add(Calendar.MONTH,months);
		 SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		 return formatter.format(cal.getTime());
		 
	 } 
	 
	 public static String addYearsByFormatter(int years,String dateFormat){
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(new Date());
		 cal.add(Calendar.YEAR, years);
		 SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		 return formatter.format(cal.getTime());
		 
	 }
	 
	 public static String firstDayOfNextMonth(String dateFormat){
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(new Date());
		 cal.add(Calendar.MONTH, 1);
		 cal.set(Calendar.DAY_OF_MONTH,1);
		 SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		 return formatter.format(cal.getTime());
	 }
	 
	 public static String firstDayOfMonth(int year,int month,String dateFormat){
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.YEAR, year);
		 cal.add(Calendar.MONTH,month);
		 cal.add(Calendar.DAY_OF_MONTH,1);
		 SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		 return formatter.format(cal.getTime());
	 }
	 
	 public static String firstDayOfMonth(int month,String dateFormat){
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(new Date());
		 cal.add(Calendar.MONTH, month);
		 cal.set(Calendar.DAY_OF_MONTH, 1);
		 SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		 return formatter.format(cal.getTime());
		 
	 }
	 
	 public static String getMilSecNow(){
		 return String.valueOf(System.currentTimeMillis());
	 }
	 
	 
	 
	
	
	
	
	
	
	
	

}
