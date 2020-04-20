package com.openrules.tools;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Dates {
	
	public static int years(Date d1, Date d2) { 
		long milliseconds1 = d1.getTime();
		long milliseconds2 = d2.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		long diffMinutes = diffSeconds / 60;
		long diffHours = diffMinutes / 60;
		long diffDays = diffHours / 24;
		int diffYears = (int)(Math.floor(diffDays / 365));
		return diffYears;
	}
	
	public static int yearsToday(Date d1) {
		return years(d1,new Date());
	}
	
	public static long days(Date d1, Date d2) { 
		long milliseconds1 = d1.getTime();
		long milliseconds2 = d2.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		long diffMinutes = diffSeconds / 60;
		long diffHours = diffMinutes / 60;
		long diffDays = diffHours / 24;
		//long diffYears = (long)(Math.floor(diffDays / 365));
		return diffDays;
	}
	
	public static long hours(Date d1, Date d2) { 
		long milliseconds1 = d1.getTime();
		long milliseconds2 = d2.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		long diffMinutes = diffSeconds / 60;
		long diffHours = diffMinutes / 60;
		//long diffDays = diffHours / 24;
		//long diffYears = (long)(Math.floor(diffDays / 365));
		return diffHours;
	}
	
	public static long minutes(Date d1, Date d2) { 
		long milliseconds1 = d1.getTime();
		long milliseconds2 = d2.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		long diffMinutes = diffSeconds / 60;
		//long diffHours = diffMinutes / 60;
		//long diffDays = diffHours / 24;
		//long diffYears = (long)(Math.floor(diffDays / 365));
		return diffMinutes;
	}
	
	public static long daysToday(Date d1) { 
		return days(d1,new Date());
	}
	
	public static long months(Date d1, Date d2) { 
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(d1);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(d2);
		int diffYears = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonths = diffYears * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
		int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
		if (endDay-startDay+1>15)
			diffMonths++;
		return diffMonths;
		//return (long)(Math.floor(days(d1,d2)/30));
	}
	
	public static long monthsToday(Date d1) {
		return months(d1,new Date());
	}
	
	public static Date addDays(Date date, int days) { 
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, days); 
		return c.getTime();
	}
	
	public static Date addHours(Date date, int hours) { 
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.HOUR, hours); 
		return c.getTime();
	}
	
	public static Date addMonths(Date date, int months) { 
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.MONTH, months); 
		return c.getTime();
	}
	
	public static Date addYears(Date date, int years) { 
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.YEAR, years); 
		return c.getTime();
	}
	
	public static int currentHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	
	public static Date today() {
		return new Date();
	}
	
	public static Date unknown() {
		Calendar cc = Calendar.getInstance();
		cc.set(1970, 0, 1, 0, 0, 0);
		return cc.getTime();
	}
	
	public static int getYear(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	public static Date setYear(Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.set(Calendar.YEAR, year);
		return c.getTime();
		//Date d = date.setYear(year - 1900);
	}
	
	static final String[] daysOfWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
    public static String getDayOfWeek(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        return daysOfWeek[dow];
    }
	
	public static int getMonth(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public static Date setMonth(Date date, int month) {
		//date.setMonth(month - 1);
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.set(Calendar.MONTH, month);
		return c.getTime();
	}
	
	public static int getDay(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Date setDay(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}
	
	public static Date newDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	public static Date newDate(String yyyy_mm_dd) { // e.g. "2017-01-01"
		try {
			String[] elements = yyyy_mm_dd.split("-");
			if (elements.length != 3) {
				throw new RuntimeException(); 
			}
			int year = Integer.parseInt(elements[0]);
			int month = Integer.parseInt(elements[1]);
			int day = Integer.parseInt(elements[2]);
			return newDate(year, month, day);
		} catch (Exception e) {
			System.out.println("Error in Dates.newDate(yyyy-mm-dd): expected date format YYYY-MM-DD");
			e.getStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		/** The date at the end of 1980 */
		Date d1 = new GregorianCalendar(1979, 11, 31, 23, 59).getTime();

		/** Today's date */
		Date d2 = new Date();
		
		System.out.println("Years since " + d1 +": " + Dates.years(d1,d2));
		
		Date d3 = new GregorianCalendar(2014, 11, 31, 23, 59).getTime();
		System.out.println("Days between " + d2 +" and " + d3 + ": " + Dates.days(d2,d3));
		
		Date d4 = new GregorianCalendar(2012, 1, 1, 0, 0).getTime();
		System.out.println(d4 + " + 30 days = " + Dates.addDays(d4,30));
		
		Date d5 = new GregorianCalendar(2012, 1, 29, 0, 0).getTime();
		System.out.println("d5="+d5);
		d5 = Dates.addYears(d5,3);
		System.out.println("d5 + 3 years = " + d5);
		
		System.out.println("Year=" + Dates.getYear(d5) + " Month=" + Dates.getMonth(d5) + " Day=" + Dates.getDay(d5));
		
		d5 = Dates.setYear(d5, 2017);
		d5 = Dates.setMonth(d5, 10);
		d5 = Dates.setDay(d5, 19);
		System.out.println("New Year=" + Dates.getYear(d5)+ " Month=" + Dates.getMonth(d5) + " Day=" + Dates.getDay(d5)); 
		System.out.println(d5);
		
		Date d6 = Dates.newDate(2017, 11, 25);
		System.out.println("d6="+d6);
		
		Date d7 = Dates.newDate("2017-11-25");
		System.out.println("d7="+d7);
		
		/*
		2/1/2018              12/31/2018          11
		2/1/2018              11/30/2018          10
		2/1/2018              10/30/2018           9
		2/1/2018              9/30/2018            8
		*/
		
		Date d8 = new GregorianCalendar(2018, 01, 01, 0, 0).getTime();
		Date d9 = new GregorianCalendar(2018, 11, 16, 23,59).getTime();
		System.out.println("Months between " + d8 +" and " + d9 + ": " + Dates.months(d8,d9));
		
		System.out.println("Unknown Date: " + Dates.unknown());
	}
 
}
