/*
 * Copyright (c) 2005-2007 OpenRules, Inc. 	
 */
package com.openrules.tools;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {
	static public void out(String output) {
		System.out.println(output);
	}

	static public boolean contains(Object[] ary, Object obj) {
		if (obj == null)
			return false;

		for (int i = 0; i < ary.length; i++) {
			if (ary[i].equals(obj))
				return true;
		}

		return false;

	}

	static public boolean contains(String[] ary1, String[] ary2) {
		for (int i = 0; i < ary2.length; i++) {
			if (!contains(ary1, ary2[i]))
				return false;
		}

		return true;
	}
	
	static public boolean contains(String[] array, String s) {
		for (int i = 0; i < array.length; i++) {
			if (s.equalsIgnoreCase(array[i]))
				return true;
		}
		return false;
	}

	static public boolean matches(String text, String regex) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	static public String oneString(String[] ary, String devider) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < ary.length; ++i) {
			if (i != 0)
				result.append(devider);
			result.append(ary[i]);
		}
		return result.toString();
	}

	static public String[] intersection(String[] ary1, String[] ary2) {
		Vector v = new Vector();
		for (int j = 0; j < ary2.length; ++j) {
			if (contains(ary1, ary2[j]))
				v.add(ary2[j]);
		}
		return (String[]) v.toArray(new String[v.size()]);
	}

	static public double round(double value, double precision) {
		return ((double) Math.round(value / precision)) * precision;
	}
	
	static public double decimal(double value, int digitalPlaces) {
        double precision = Math.pow(10,digitalPlaces);
        return Math.round(value * precision)/ precision;
    }

	static public String format(double d, String fmt) {
		DecimalFormat df = new DecimalFormat(fmt);
		return df.format(d);
	}

	static public String format(double d) {
		return format(d, DEFAULT_DOUBLE_FORMAT);
	}

	static final String DEFAULT_DOUBLE_FORMAT = "#,##0.00";

	static public double parseFormattedDouble(String s, String fmt)
			throws ParseException {
		DecimalFormat df = new DecimalFormat(fmt);
		return df.parse(s).doubleValue();
	}

	static public double parseFormattedDouble(String s) throws ParseException {
		return parseFormattedDouble(s, DEFAULT_DOUBLE_FORMAT);
	}

	

	static public String extractFrom(String s, String left, String right) {
		if (s == null)
			return null;
		if (!s.startsWith(left))
			return null;
		if (!s.endsWith(right))
			return null;
		int beginIndex = left.length();
		int endIndex = s.indexOf(right);
		return s.substring(beginIndex, endIndex);
	}

	static public int extractNumberFrom(String s, char left) {
		int beginIndex = s.indexOf(left) + 1;
		int endIndex = beginIndex;
		for (int i = beginIndex; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isWhitespace(c))
				continue;
			if (Character.isDigit(c))
				endIndex++;
			else
				break;
		}
		String number = s.substring(beginIndex, endIndex);
		return Integer.parseInt(number.trim());
	}

	static public String replaceTildeBound(String s, char tilde, int bound) {
		try {
			StringBuffer r = new StringBuffer(s.length());
			r.setLength(2 * s.length());
			String newBound = String.valueOf(bound);
			int current = 0;
			boolean skip = false;
			int i = 0;
			while (i < s.length()) {
				char cur = s.charAt(i);
				if (skip) {
					if (!Character.isDigit(cur)) {
						r.setCharAt(current++, cur);
						if (!Character.isWhitespace(cur))
							skip = false;
					}
				} else if (cur != tilde) {
					r.setCharAt(current++, cur);
				} else { // found tilde
					for (int j = 0; j < newBound.length(); j++) {
						r.setCharAt(current++, newBound.charAt(j));
					}
					skip = true;
				}
				i++;
			}
			return r.toString().trim();
		} catch (Exception e) {
			return null;
		}
	}

	public static String removeChar(String s, char c) {
		StringBuffer r = new StringBuffer(s.length());
		r.setLength(s.length());
		int current = 0;
		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			if (cur != c)
				r.setCharAt(current++, cur);
		}
		return r.toString();
	}
	
	public static void sort(List<String> list) {
		Collections.sort(list);
	}
	
	public static void sortIgnoreCase(List<String> list) {
		Collections.sort(list, new Comparator<String>() {
		    public int compare(String o1, String o2) {              
		        return o1.compareToIgnoreCase(o2);
		    }
		});
	}
	
	// Dates manipulations
	static public int date2Age(Timestamp date) {
		Calendar now = Calendar.getInstance();
		Calendar dob = Calendar.getInstance();
		dob.setTime(date);
		if (dob.after(now)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}
		int year1 = now.get(Calendar.YEAR);
		int year2 = dob.get(Calendar.YEAR);
		int age = year1 - year2;
		int month1 = now.get(Calendar.MONTH);
		int month2 = dob.get(Calendar.MONTH);
		if (month2 > month1) {
			age--;
		} else if (month1 == month2) {
			int day1 = now.get(Calendar.DAY_OF_MONTH);
			int day2 = dob.get(Calendar.DAY_OF_MONTH);
			if (day2 > day1) {
				age--;
			}
		}
		return age;
	}

	/**
	 * Formats the date defined in the first parameter according to the format
	 * defined by java.text.DateFormat.SHORT.
	 * 
	 * @param date
	 * @return
	 */
	static public String format(Date date) {
		return format(date, null);
	}

	/**
	 * Formats the date defined in the first parameter according to the format
	 * defined by the second parameter. If the second parameter is null, uses
	 * the default format java.text.DateFormat.SHORT. The second parameter
	 * format allows you to choose any user-defined patterns for date-time
	 * formatting as it is specified by the java,text.SimpleDateFormat class.
	 * The following examples show how date and time formats are interpreted in
	 * the U.S. locale. The given date and time are 2001-07-04 12:08:56 local
	 * time in the U.S. Pacific Time time zone:
	 * 
	 * <pre>
	 * Date and Time Format            => Result  
	 * "yyyy.MM.dd G 'at' HH:mm:ss z"  => 2001.07.04 AD at 12:08:56 PDT 
	 * "EEE, MMM d, ''yy"              => Wed, Jul 4, '01  
	 * "h:mm a"                        => 12:08 PM  
	 * "hh 'o''clock' a, zzzz"         => 12 o'clock PM, Pacific Daylight Time 
	 * "K:mm a, z"                     => 0:08 PM, PDT
	 * "yyyyy.MMMMM.dd GGG hh:mm aaa"  => 02001.July.04 AD 12:08 PM  
	 * "EEE, d MMM yyyy HH:mm:ss Z"    => Wed, 4 Jul 2001 12:08:56 -0700 
	 * "yyMMddHHmmssZ"                 => 010704120856-0700 
	 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ"    => 2001-07-04T12:08:56.235-0700
	 * </pre>
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	static public String format(Date date, String format) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		if (format != null)
			df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	static public String formatMedium(Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		return df.format(date);
	}
	
	static public String today(String format) {
		Calendar cal = Calendar.getInstance();
		//return format(cal.getTime());
		return format(cal.getTime());
	}
	
	static public String today() {
		Calendar cal = Calendar.getInstance();
		//return format(cal.getTime());
		return cal.getTime().toString();
	}
	
	public static long years(Date d1, Date d2) { 
		long milliseconds1 = d1.getTime();
		long milliseconds2 = d2.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		long diffMinutes = diffSeconds / 60;
		long diffHours = diffMinutes / 60;
		long diffDays = diffHours / 24;
		long diffYears = (long)(Math.floor(diffDays / 365));
		return diffYears;
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
	
	public static void main(String[] args) throws ParseException {
		System.out.println(today());
		System.out.println(today(null));
		/** The date at the end of 1980 */
		Date d1 = new GregorianCalendar(1979, 11, 31, 23, 59).getTime();

		/** Today's date */
		Date d2 = new Date();
		
		System.out.println("Years since " + d1 +": " + years(d1,d2));
		
		Date d3 = new GregorianCalendar(2014, 11, 31, 23, 59).getTime();
		System.out.println("Days between " + d2 +" and " + d3 + ": " + days(d2,d3));
		
		
		String s = format(33234.555 * 1.34 / 2.6);
		System.out.println(format(33234.555 * 1.34 / 2.6));
		double d = parseFormattedDouble(s);
		System.out.println(format(d));
		double x = 345.14578;
		System.out.println("round(" + x + ",0.01) produces " + round(x, 0.01));
		double y = -345.14499;
		System.out.println("round(" + y + ",0.01) produces " + round(y, 0.01));

		String text = "ABCDEFG";
		String regex = "A.*EFG";
		String match = "SUCCESS";
		if (!matches(text, regex))
			match = "FAILURE";
		System.out.println("Match " + text + " with regex " + regex + ": "
				+ match);

		String criterion = "> ~ 800";
		String clean = removeChar(criterion, '~');
		System.out.println("criterion=" + criterion + " clean=" + clean);

		System.out.println(extractNumberFrom("aaa > ~ 8000 bbb", '~'));

		System.out.println(replaceTildeBound("aaa > ~ 8000 bbb", '~', 7200));

		System.out.println(replaceTildeBound("aaa > ~0 bbb", '~', -10));
		
		List<String> fruits = new ArrayList<String>(7);
		fruits.add("Pineapple");
		fruits.add("apple");
		fruits.add("apricot");
		fruits.add("Banana");
		fruits.add("mango");
		fruits.add("melon");        
		fruits.add("peach");
		sort(fruits);
		System.out.println("Sorted: " + fruits);
		sortIgnoreCase(fruits);
		System.out.println("Sorted Ignoring Case: " + fruits);
		
		double double1 = 123.5638;
        int digitalPlaces = 2;
        double precision = Math.pow(10,digitalPlaces);
        double double2 = Math.round(double1 * precision)/ precision;
        System.out.println("\ndouble2="+double2);
        
        double double3 = decimal(double1, 2);
        System.out.println("\ndouble3="+double3);
        
        

	}

}
