package com.sinosig.global.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 时间相关的公共类
 * @author: Aladdin.Cao
 */
public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public enum DateType {
		YEAR, MONTH, DAY, HH, MI, SS, YYYY_MM_DD, YYYYMMDD
	}

	public static java.sql.Date maxDate() {
		return java.sql.Date.valueOf("9999-09-09");
	}

	/**
	 * 得到当前应
     * 用服务器的系统日期
	 * 
	 * @return 系统日期
	 */
	public static Timestamp sysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 得到当前应用服务器的系统日期
	 * 
	 * @return 系统日期
	 */
	public static java.sql.Date sysDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * 得到当前应用服务器的系统时间
	 * 
	 * @return 系统日期
	 */
	public static Time sysTime() {
		return new Time(System.currentTimeMillis());
	}

	/**
	 * 得到当前应用服务器的系统日期<br>
	 * 字符串类型
	 *
	 * @return 系统日期
	 */
	public static String sysDate4Str() {
		return new java.sql.Date(System.currentTimeMillis()).toString();
	}

	/**
	 * 得到d1与d2之间相差数值
	 * <p>
	 * 数值可以为年份，DateType.YEAR<br>
	 * 数值可以为月份，DateType.MONTH<br>
	 * 数值可以为天数，DateType.DAY<br>
	 * 数值可以为小时，DateType.HH<br>
	 * 数值可以为分钟，DateType.MI<br>
	 * 数值可以为秒，DateType.SS
	 *
	 * @param d1
	 *            日期1(较晚(靠后)的日期)
	 * @param d2
	 *            日期2(较早(靠前)的日期)
	 *            数值类型
	 * @return 相差数值 <br>
	 *         d1早于d2时，返回-1。<br>
	 *         dateType类型不正确时，返回-2。
	 */
	public static int dateBetween(Date d1, Date d2, DateType dateType) {
		if (d1.before(d2)) {
			return -1;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (DateType.YEAR.equals(dateType)) {
			return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		}
		if (DateType.MONTH.equals(dateType)) {
			return (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12 + c1.get(Calendar.MONTH)
					- c2.get(Calendar.MONTH);
		}
		if (DateType.DAY.equals(dateType)) {
			return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 86400000L);
		}
		if (DateType.HH.equals(dateType)) {
			return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 3600000L);
		}
		if (DateType.MI.equals(dateType)) {
			return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 60000L);
		}
		if (DateType.SS.equals(dateType)) {
			return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000L);
		}
		return -2;
	}

	/**
	 * 将精确到秒的字符串转换为日期类型
	 * <p>
	 * 字符串格式需要为yyyy-MM-dd hh:mm:ss
	 *
	 * @param date
	 *            将精确到秒的字符串
	 * @return Timestamp对象
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static Date str2Timestamp(String date) {
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			clsFormat.parse(date);
			return new Timestamp(clsFormat.parse(date).getTime());
		} catch (ParseException e) {
			logger.error("将精确到秒的字符串转换为日期类型", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将精确到秒的字符串转换为日期类型
	 * <p>
	 * 字符串格式需要为yyyy-MM-dd hh:mm:ss
	 *
	 * @param date
	 *            将精确到秒的字符串
	 * @return Timestamp对象
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static Timestamp str2Timestamp(String date, String pattern) {
		SimpleDateFormat clsFormat = new SimpleDateFormat(pattern);
		try {
			clsFormat.parse(date);
			return new Timestamp(clsFormat.parse(date).getTime());
		} catch (ParseException e) {
			logger.error("将精确到秒的字符串转换为日期类型" + e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * String 转 date
	 *  2018-07-05   --> 2018-07-05  00:00:00
	 * @param date
	 * @return date
	 */
	public static Date string2Date(String date) {
		Date parse = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			parse = sdf.parse(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  parse;
	}


	/**
	 * 将字符型时间转换为Time型
	 * <p>
	 * 字符串格式需要为HH:mm:ss
	 *
	 *            将精确到天的字符串
	 * @return Timestamp对象
	 * @throws ParseException
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static Time string2Time(String time) {
		SimpleDateFormat clsFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			return new Time(clsFormat.parse(time).getTime());
		} catch (ParseException e) {
			logger.error("将字符型时间转换为Time型" + e);
			throw new RuntimeException(e);
		}
	}

	// 将精确到天的字符串对象转换为日期类型
//	public static java.sql.Date object2Date(Object date) {
//		return string2Date((String) date);
//	}

	/**
	 * 将字符串转为指定格式的日期
	 * <p>
	 * 日期字符串与字符串格式需要匹配
	 *
	 * @param date
	 *            日期字符串
	 * @param frm
	 *            字符串格式
	 * @return Timestamp对象
	 * @throws ParseException
	 * @throws RuntimeException
	 *             日期类型转换错误
	 */
	public static java.sql.Date string2Date(String date, String frm) {
		SimpleDateFormat clsFormat = new SimpleDateFormat(frm);
		try {
			clsFormat.parse(date);
			return new java.sql.Date(clsFormat.parse(date).getTime());
		} catch (ParseException e) {
			logger.error("将字符串转为指定格式的日期" + e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将日期类型转换为精确到天的字符串
	 * <p>
	 * 字符串格式为yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 *            日期类型
	 * @return String 日期
	 *
	 */
	public static String date2String(Date date) {
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd ");
		return clsFormat.format(date);
	}

	/**
	 * 将日期类型转换为指定格式的字符串
	 *
	 *
	 * @param date
	 *            日期类型
	 * @param frm
	 *            需要转换的格式,具体格式参加java说明
	 * @return String 日期
	 *
	 */
	public static String date2String(Date date, String frm) {
		SimpleDateFormat clsFormat = new SimpleDateFormat(frm);
		return clsFormat.format(date);
	}

	/**
	 * 日期加运算
	 *
	 * @param date
	 *            需要加的日期
	 * @param dateType
	 *            需要加的类型<br>
	 *            数值可以为年份，DateType.YEAR<br>
	 *            数值可以为月份，DateType.MONTH<br>
	 *            数值可以为天数，DateType.DAY<br>
	 *            数值可以为小时，DateType.HH<br>
	 *            数值可以为分钟，DateType.MI<br>
	 *            数值可以为秒，DateType.SS
	 * @param num
	 *            需要加的数值
	 * @return 加完之后的日期
	 */
	public static Date addDate(Date date, DateType dateType, int num) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		if (DateType.YEAR.equals(dateType)) {
			c1.add(Calendar.YEAR, num);
			return c1.getTime();
		}
		if (DateType.MONTH.equals(dateType)) {
			c1.add(Calendar.MONTH, num);
			return c1.getTime();
		}
		if (DateType.DAY.equals(dateType)) {
			c1.add(Calendar.DAY_OF_MONTH, num);
			return c1.getTime();
		}
		if (DateType.HH.equals(dateType)) {
			c1.add(Calendar.HOUR_OF_DAY, num);
			return c1.getTime();
		}
		if (DateType.MI.equals(dateType)) {
			c1.add(Calendar.MINUTE, num);
			return c1.getTime();
		}
		if (DateType.SS.equals(dateType)) {
			c1.add(Calendar.SECOND, num);
			return c1.getTime();
		}
		return date;
	}

	/**
	 * 指定日期前一天。
	 *
	 * @param date
	 * @return
	 */
	public static java.sql.Date yesterday(Date date) {
		Calendar cdate = Calendar.getInstance();
		cdate.setTime(date);
		cdate.add(Calendar.DATE, -1);
		java.sql.Date ldate = new java.sql.Date(cdate.getTime().getTime());
		return ldate;
	}

	/**
	 * 当前日期上月1日
	 *
	 * @return
	 */
	public static java.sql.Date firstDayOfLastMonth() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		date.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date sysDate = new java.sql.Date(date.getTime().getTime());
		return sysDate;

	}

	/**
	 * 当前日期1日
	 *
	 * @return
	 */
	public static java.sql.Date firstDayOfMonth() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date sysDate = new java.sql.Date(date.getTime().getTime());
		return sysDate;

	}

	/**
	 * 指定月上月1日
	 *
	 * @return
	 */
	public static java.sql.Date firstDayOfLastMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
            cal.setTime(date);
        }
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTimeInMillis());

	}

	/**
	 * 指定月当月1日
	 *
	 * @param date
	 * @return
	 */
	public static java.sql.Date firstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
            cal.setTime(date);
        }
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	/**
	 * 指定月次月1日
	 *
	 * @param calcdate
	 * @return
	 * @throws ParseException
	 */
	public static String firstDayOfNextMonth(String calcdate) {
		return DateUtil.date2String(DateUtil.firstDayOfNextMonth(DateUtil.string2Date(calcdate)));
	}

	/**
	 * 指定日次月1日
	 *
	 * @param calcdate
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date firstDayOfNextMonth(Date calcdate) {
		Calendar date = Calendar.getInstance();
		date.setTime(calcdate);
		date.add(Calendar.MONTH, 1);// 设置月为下个月
		date.set(Calendar.DAY_OF_MONTH, date.getMinimum(Calendar.DAY_OF_MONTH));// 设置日为月第一天
		java.sql.Date sysDate = new java.sql.Date(date.getTime().getTime());
		return sysDate;
	}

	/**
	 * 日期比较
	 * <p>
	 * 开始日期在结束日期之前返回-1<br>
	 * 开始日期等于结束日期返回0<br>
	 * 开始日期在结束日期之后返回1
	 *
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static int compareDate(Date startDate, Date endDate) {

		if(null != startDate && null != endDate){
			// 去除掉时分秒的时间
			startDate = DateUtil.string2Date(startDate.toString());
			endDate = DateUtil.string2Date(endDate.toString());

			if (startDate != null && endDate != null && startDate.before(endDate)) {
				return -1;
			}
			if (startDate != null && endDate != null && startDate.before(endDate)) {
				return 0;
			}
			if (startDate != null && endDate != null && startDate.before(endDate)) {
				return 1;
			}
		}
		return -2;
	}

	/**
	 * 指定月上月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date lastDayOfLastMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
            cal.setTime(date);
        }
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 指定月当月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static java.sql.Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
            cal.setTime(date);
        }
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	/**
	 * 指定月下月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date lastDayOfNextMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
            cal.setTime(date);
        }
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 得到日期中的“日”
	 *
	 * @param date
	 *            指定日期
	 * @return 日期中的“日”
	 */
	public static int getDayOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
            cal.setTime(date);
        }
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	/**
	 * Returns a hash code value for the object. This method is
	 * supported for the benefit of hash tables such as those provided by
	 * {@link HashMap}.
	 * <p>
	 * The general contract of {@code hashCode} is:
	 * <ul>
	 * <li>Whenever it is invoked on the same object more than once during
	 * an execution of a Java application, the {@code hashCode} method
	 * must consistently return the same integer, provided no information
	 * used in {@code equals} comparisons on the object is modified.
	 * This integer need not remain consistent from one execution of an
	 * application to another execution of the same application.
	 * <li>If two objects are equal according to the {@code equals(Object)}
	 * method, then calling the {@code hashCode} method on each of
	 * the two objects must produce the same integer result.
	 * <li>It is <em>not</em> required that if two objects are unequal
	 * according to the {@link Object#equals(Object)}
	 * method, then calling the {@code hashCode} method on each of the
	 * two objects must produce distinct integer results.  However, the
	 * programmer should be aware that producing distinct integer results
	 * for unequal objects may improve the performance of hash tables.
	 * </ul>
	 * <p>
	 * As much as is reasonably practical, the hashCode method defined by
	 * class {@code Object} does return distinct integers for distinct
	 * objects. (This is typically implemented by converting the internal
	 * address of the object into an integer, but this implementation
	 * technique is not required by the
	 * Java&trade; programming language.)
	 *
	 * @return a hash code value for this object.
	 * @see Object#equals(Object)
	 * @see System#identityHashCode
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * 将java.sql.Timestamp类型日期，或java.util.Date类型转换为java.sql.Date类型
	 *
	 * @param obj
	 * @return
	 */
	public static java.sql.Date obj2SqlDate(Object obj) {
		if (obj instanceof Date) {
			return new java.sql.Date(((Date) obj).getTime());
		}
		throw new RuntimeException("入参不为日期类型");
	}

	/**
	 * 将java.sql.Date类型日期，或java.util.Date类型转换为java.sql.Timestamp类型
	 *
	 * @param obj
	 * @return
	 */
	public static Timestamp obj2Timestamp(Object obj) {
		if (obj instanceof Date) {
			return new Timestamp(((Date) obj).getTime());
		}
		throw new RuntimeException("入参不为日期类型");
	}

	/**
	 * 计算年龄
	 * <p>
	 * 当前系统日期减去生日，得到年龄
	 *
	 * @param birthday
	 *            生日
	 * @return 年龄
	 */
	public static double calcAge(Date birthday) {
		return DateUtil.calcAge(birthday, new java.sql.Date(System.currentTimeMillis()));

	}

	/**
	 * 计算年龄
	 * <p>
	 * 指定日期减去生日，得到年龄
	 *
	 * @param birthday
	 *            生日
	 * @param date
	 *            指定日期
	 * @return 年龄
	 */
	public static double calcAge(Date birthday, Date date) {
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(birthday);
		Calendar sysDate = Calendar.getInstance();
		sysDate.setTimeInMillis(date.getTime());
		return sysDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
				+ (sysDate.get(Calendar.MONTH) - calendar.get(Calendar.MONTH)) / 12.00
				+ (sysDate.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH)) / 365.00;
	}

	/**
	 * 获取前一个小时
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBefore1Hour(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
            cal.setTime(date);
        }
		cal.set(Calendar.HOUR, Calendar.HOUR - 1);
		return cal.getTime();
	}
	/*
	 * 获取前一天
	 */
	public static Date getBefore1Day(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
            cal.setTime(date);
        }
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 判断是否为正确的日期格式
	 * 
	 * @return
	 */
	public static boolean isValidDate(String date) {

		try {
			DateUtil.str2Timestamp(date);
		} catch (RuntimeException e1) {
			try {
				DateUtil.string2Date(date);
			} catch (RuntimeException e2) {
				return false;// 转换失败是不正确的
			}
			return true;// 二次转换成功就是正确的
		}
		return true;// 转换成功就是正确的
	}

	public static String time2Str(Time time) {
		if (time == null) {
            return "";
        }
		return DateUtil.date2String(time, "hh:mm:ss");
	}
	
	/**
	 * 校验时间精确到分钟
	 * 例如：yyyy-MM-dd hh:mm 、 yyyy/MM/dd hh:mm
	* @param o
	* @return boolean
	* @description:
	 */
	public static boolean isValidDateMM(Object o){
		try {
			if(null!= o ){
				String s = o.toString();
				String [] strArr = s.split(" ");
			    String eL= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";        
		        Pattern p = Pattern.compile(eL);         
		        Matcher m = p.matcher(strArr[0]);         
		        if(m.matches()){//校验年月日是否正确
                    String[] minHArr = strArr[1].split(":");
		        	int min = Integer.valueOf(minHArr[1]);
		        	int hour = Integer.valueOf(minHArr[0]);
		        	if(hour>=0&&hour<=24&&min>=0&&min<=60){//校验小时分钟是否正确
		        		return true;
		        	}else{
		        		return false;
		        	}
		        }else{
		        	return false;
		        }
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error("校验时间精确到分钟" + e);
			return false;
		}
	}
	
	public static int compareDateHMM(Date startDate, Date endDate) {
		if (startDate.before(endDate)) {
			return -1;
		}
		if (startDate.equals(endDate)) {
			return 0;
		}
		if (startDate.after(endDate)) {
			return 1;
		}
		return -2;
	}
	
	/**
	 * 得到当前月份所在季度的第一个月的第一天
	* @param month
	* @return
	* @throws ParseException java.sql.Date
	* @description:
	 */
	public static java.sql.Date getQuarterFirstMonth( java.sql.Date month){
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date_str ="";
			String result_Date="";
			 java.sql.Date date = null;
			if(null!= month){
				date_str = clsFormat.format(month);
				String[] date_arr_split = date_str.split("-");
				int month_int  = Integer.valueOf(date_arr_split[1]);
				if(1==month_int||2==month_int||3==month_int){
					result_Date = date_arr_split[0]+""+"-01-01";
				}else if(4==month_int||5==month_int||6==month_int){
					result_Date = date_arr_split[0]+""+"-04-01";
				}else if(7==month_int||8==month_int||9==month_int){
					result_Date = date_arr_split[0]+""+"-07-01";
				}else{
					result_Date = date_arr_split[0]+""+"-10-01";
				}
				if(null!=result_Date&&!StringUtils.isEmpty(result_Date)){
					date =  java.sql.Date.valueOf(result_Date);
				}
			}
			return date;
    }

	/**
	 * 得到当前日期的所在半年度的第一个月份的第一天
	* @param month
	* @return
	* @throws ParseException java.sql.Date
	* @description:
	 */
	public static java.sql.Date getSemiAnnualFirstMonth( java.sql.Date month){
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date_str ="";
			String result_Date="";
			 java.sql.Date date = null;
			if(null!= month){
				date_str = clsFormat.format(month);
				String[] date_arr_split = date_str.split("-");
				int month_int  = Integer.valueOf(date_arr_split[1]);
				if(month_int<=6){
					result_Date = date_arr_split[0]+""+"-01-01";
				}else{
					result_Date = date_arr_split[0]+""+"-07-01";
				}
				if(null!=result_Date&&!StringUtils.isEmpty(result_Date)){
					date =  java.sql.Date.valueOf(result_Date);
				}
			}
			return date;
    }
	


	/**
	* 将日期类型转换为指定格式的日期类型
	* @param date
	* 			日期类型
	* @param frm
	* 			 需要转换的格式,具体格式参加java说明
	* @return Date
	* @description:
	*/
	public static java.sql.Date date2date(Date date,String frm){
		return string2Date(date2String(date), frm);
	}

	/**
	 * 得到一个指定日期n个月后的日期
	 * @param startDate
	 * @param n
	 * @return
	 */
	public static Date getNMonthLastDate(Date startDate,int n){
		Calendar c = Calendar.getInstance();//获得一个日历的实例
		c.setTime(startDate);//设置日历时间
		c.add(Calendar.MONTH, Integer.valueOf(n));//在日历的月份上增加n个月
		return c.getTime();
	}
	/**
	 * 得到一个指定日期n天后的日期
	 * @param startDate
	 * @param n
	 * @return
	 */
	public static Date getNDayDate(Date startDate,int n){
		Calendar c = Calendar.getInstance();//获得一个日历的实例
		c.setTime(startDate);//设置日历时间
		c.add(Calendar.DATE, n);
		return c.getTime();
	}
	
	/** 
	* 得到某年的最后一天
	* @param date 
	* 			要得年的最后一天的年中任意月份
	* @return Date
	* 			一年的最后一天 例：2014-1-1
	* @description:
	*/
	public static Date getLastDayOfYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.set(Calendar.MONTH, 12);
		c.set(Calendar.DAY_OF_MONTH, 0);
		return date2date(c.getTime(), "yyyy-MM-dd");
	}
	
	/** 
	* 得到某年的第一天
	* @param date 
	* 			要得年的第一天的年中任意月份
	* @return Date
	* 			一年的第一天 例：2014-1-1
	* @description:
	*/
	public static Date getFirstDayOfYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return date2date(c.getTime(), "yyyy-MM-dd");
	}
	/**
	 * 将Timestamp的日期转换成yyyy-MM-dd HH:mm:ss格式
	 * @param time
	 * @return
	 */
	public static String timeStamp2str(Timestamp time){
		Date date = new Date(time.getTime());
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return clsFormat.format(date);
	}
	
	/**
	 * 核心的方法
     * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔 author: HST
     * <p><b>Example: </b><p>
     * <p>参照calInterval(String  cstartDate, String  cendDate, String unit)，前两个变量改为日期型即可<p>
     * @param startDate 起始日期，Date变量
     * @param endDate 终止日期，Date变量
     * @param unit 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
     * @return 时间间隔,整形变量int
     */
    public static int calInterval(Date startDate, Date endDate, String unit)
    {
        int interval = 0;

        GregorianCalendar sCalendar = new GregorianCalendar();
        sCalendar.setTime(startDate);
        int sYears = sCalendar.get(Calendar.YEAR);
        int sMonths = sCalendar.get(Calendar.MONTH);
        int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar eCalendar = new GregorianCalendar();
        eCalendar.setTime(endDate);
        int eYears = eCalendar.get(Calendar.YEAR);
        int eMonths = eCalendar.get(Calendar.MONTH);
        int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

        if ("Y".equals(unit))
        {
            interval = eYears - sYears;
            if (eMonths < sMonths)
            {
                interval--;
            }
            else
            {
                if (eMonths == sMonths && eDays < sDays)
                {
                    interval--;
                    if (eMonths == 1)
                    { //如果同是2月，校验润年问题
                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
                        { //如果起始年是润年，终止年不是润年
                            if (eDays == 28)
                            { //如果终止年不是润年，且2月的最后一天28日，那么补一
                                interval++;
                            }
                        }
                    }
                }
            }
        }
        if ("M".equals(unit))
        {
            interval = eYears - sYears;
            interval *= 12;
            interval += eMonths - sMonths;
            if (eDays < sDays)
            {
                interval--;
                //eDays如果是月末，则认为是满一个月
                int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
                if (eDays == maxDate)
                {
                    interval++;
                }
            }
        }
        if ("D".equals(unit))
        {
            sCalendar.set(sYears, sMonths, sDays);
            eCalendar.set(eYears, eMonths, eDays);
            long lInterval = (eCalendar.getTime().getTime() -
                              sCalendar.getTime().getTime()) / 86400000;
            interval = (int) lInterval;
        }
        return interval;
    }


	/**
	 * 核心的方法
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔 author: HST
	 * 当大于一个月或者一年的四舍五入
	 * <p><b>Example: </b><p>
	 * <p>参照calInterval(String  cstartDate, String  cendDate, String unit)，前两个变量改为日期型即可<p>
	 * @param startDate 起始日期，Date变量
	 * @param endDate 终止日期，Date变量
	 * @param unit 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @return 时间间隔,整形变量int
	 */
	public static int calRoundInterval(Date startDate, Date endDate, String unit)
	{
		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

		if ("Y".equals(unit))
		{
			interval = eYears - sYears;
			if (eMonths < sMonths)
			{
				interval--;
			} else if(eMonths > sMonths){
				interval++;
			}
			else
			{
				if (eMonths == sMonths && eDays < sDays)
				{
					interval--;
					if (eMonths == 1)
					{ //如果同是2月，校验润年问题
						if ((sYears % 4) == 0 && (eYears % 4) != 0)
						{ //如果起始年是润年，终止年不是润年
							if (eDays == 28)
							{ //如果终止年不是润年，且2月的最后一天28日，那么补一
								interval++;
							}
						}
					}
				}else if(eMonths == sMonths && eDays > sDays){
					interval++;
					//如果同是2月，校验润年问题
					if ((sYears % 4) != 0 && (eYears % 4) == 0)
					{ //如果起始年不是润年，终止年是润年
						if (eDays == 29)
						{ //如果终止年不是润年，且2月的最后一天28日，那么减一
							interval--;
						}
					}
				}
			}
		}
		if ("M".equals(unit))
		{
			interval = eYears - sYears;
			interval *= 12;
			interval += eMonths - sMonths;
			if (eDays < sDays)
			{
				//eDays如果是月末，则认为是满一个月
				int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
				if (eDays == maxDate)
				{
					interval--;
				}
			}else if((eDays > sDays)){//日期大于
				interval++;
				//eDays如果是月末，则认为是满一个月
				int emaxDate = eCalendar.getActualMaximum(Calendar.DATE);
				int smaxDate = sCalendar.getActualMaximum(Calendar.DATE);
				if (eDays == emaxDate&&smaxDate == sDays)
				{
					interval--;
				}
			}
		}
		if ("D".equals(unit))
		{
			sCalendar.set(sYears, sMonths, sDays);
			eCalendar.set(eYears, eMonths, eDays);
			long lInterval = (eCalendar.getTime().getTime() -
					sCalendar.getTime().getTime()) / 86400000;
			interval = (int) lInterval;
		}
		return interval;
	}
    /**
    * @Title: formatInsStartDate
    * @Description:格式化保单开始时间
    * @param @param time
    * @param @return    
    * @return String
    * @throws
     */
    public static String formatInsStartDate(LocalDateTime time){
    	if(null==time){
    		return "";
    	}else{
    		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    		return time.format(format) + "零时";
    	}
    	
    }
    /**
    * @Title: formatInsEndDate
    * @Description: 格式化保单结束时间
    * @param @param time
    * @param @return    
    * @return String
    * @throws
     */
    public static String formatInsEndDate(LocalDateTime time){
    	if(null==time){
    		return "终身";
    	}else{
    		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    		return time.plusDays(-1).format(format) + "二十四时";
    	}
    }

	/**
	 * @Title: formatInsEndDateRuzhu
	 * @Description: 长安入驻险格式化保单结束时间
	 * @param @param time
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String formatInsEndDateRuzhu(LocalDateTime insEndDate) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		return insEndDate.plusDays(0).format(format) + "二十三时五十九分五十九秒";
	}



	public static String stringReplace(String string) {
		if (StringUtil.isNull(string)) {
			return "";
		}
		char[] array = string.toCharArray();
		int size = array.length;
		char[] arrayPlace = new char[array.length - 1];
		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				break;
			}

			if (array[i] == 'T') {
				arrayPlace[i] = ' ';
			} else {
				arrayPlace[i] = array[i];
			}
		}
		return new String(arrayPlace);
	}



	/**
	 * 获取当前日期
	 * @return yyyyMMdd
	 */
	public static String newDate(Date date,String dateType){
		SimpleDateFormat df = new SimpleDateFormat(dateType);
		return df.format(date);
	}

	/**
	 * 根据当前时间获取本周的第一天
	 * @return
	 */
	public static Date getWeekStartDate(){
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		if(dayWeek==1){
			dayWeek = 8;
		}
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		Date mondayDate = cal.getTime();
		return mondayDate;
	}

    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
			day_of_week = 7;
		}
        c.add(Calendar.DATE, -day_of_week + 7);
        return newDate(c.getTime(),"yyyyMMdd");
    }

	//时间字符串截取拼接
	public static String getDate(String date){
		return date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
	}

	/**
	       * 指定日期加上天数后的日期
	       * @param num 为增加的天数
	       * @param newDate 创建时间
	       * @return
	       * @throws ParseException
	       */
    public static String plusYearMonthDay(int num,String newDate,String type){
    	try{
    		if(StringUtils.isNotEmpty(newDate)){
				Calendar c1 = Calendar.getInstance();
				c1.setTime(DateUtil.string2Date(newDate));
				if("D".equals(type)){
					c1.set(Calendar.DATE, c1.get(Calendar.DATE) + num);
				}else if("M".equals(type)){
					c1.set(Calendar.MONTH, c1.get(Calendar.MONTH) + num);
				}else if("Y".equals(type)){
					c1.set(Calendar.YEAR, c1.get(Calendar.YEAR) + num);
				}else if("-1".equals(type)){
					c1.set(Calendar.DATE, c1.get(Calendar.DATE) - num);
				}
				return DateUtil.newDate(c1.getTime(),"yyyy-MM-dd");
			}
			return "";
		}catch (Exception e){
    		logger.error(""+e);
			return null;
		}finally {

		}

    }
	/**
	 * String 转DATE
	 * @param time
	 * @param type
	 * @return
	 */
    public static Date stringOrDate(String time,String type){
    	try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
			return simpleDateFormat.parse(time);
		}catch (Exception e){
			return null;
		}finally {

		}

	}
	public static Date timeOrString(String time){
		try{
			Date endTime = null;
			if (time.length() == 14){
				//获得年月日
				String yearMonDay = time.substring(0, 8);
				//年
				String year = yearMonDay.substring(0, 4);
				//月
				String mon = yearMonDay.substring(4, 6);
				//日
				String day = yearMonDay.substring(6, 8);
				//获得时分秒
				String hourMinSec = time.substring(8);
				//时
				String hour = hourMinSec.substring(0, 2);
				//分
				String minute = hourMinSec.substring(2, 4);
				//秒
				String second = hourMinSec.substring(4, 6);
				time = year+ "-" + mon + "-" + day +" " + hour + ":" + minute + ":" +second;
				endTime = DateUtil.stringOrDate(time, "yyyy-MM-dd HH:ss:mm");
			}else if (time.length() == 10){
				endTime = DateUtil.stringOrDate(time, "yyyy-MM-dd");
			}else {
				endTime = DateUtil.stringOrDate(time, "yyyy-MM-dd HH:ss:mm");
			}
			return endTime;
		}catch (Exception e){
			return null;
		}finally {

		}

	}

	public static  Date parse(String strDate){
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(strDate);
		}catch (Exception e){
			return null;
		}
		finally {

		}
	}

	//由出生日期获得年龄
	public static  int getAge(Date birthDay)  {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {age--;}
			}else{
				age--;
			}
		}
		return age;
	}
	/**
	 * 根据传入的时间获取当前时间的前几个小时的时间
	 * @param hour
	 * @return
	 */
	public static String getTimeByHour(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)
				- hour);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar
				.getTime());
	}

	/**
	 * 获取当前年月
	 * 202010
	 * @return
	 */
	public static String getNowDate(String separator) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy"+ separator +"MM");
		String result = format.format(today);
		return result;
	}

}
