package com.openrules.tools;

import java.text.DateFormat;
import java.util.Date;

/*
 * Copyright (c) 2005-2010 OpenRules, Inc. 	
 */

public class Operator {
	public Operator(String op) {
//		System.out.println("Operator: "+op);
		String s = op.replaceAll(" ", "");
//		System.out.println("Compressed Operator: "+s);
		if (s.equals("<") 
				|| s.equalsIgnoreCase("IsLess") 
				|| s.equalsIgnoreCase("IsLessThan"))
			operator = LT;
		else if (s.equals("<=") 
				|| s.equalsIgnoreCase("IsLessOrEqualTo")
				|| s.equalsIgnoreCase("IsLessThanOrEqualTo"))
			operator = LE;
		else if (s.equals("==") || s.equals("=")
				|| s.equalsIgnoreCase("Is"))
			operator = EQ;
		else if (s.equals("!=") 
				|| s.equalsIgnoreCase("IsNot")
				|| s.equalsIgnoreCase("IsNotEqualTo")
				|| s.equalsIgnoreCase("Not")
				|| s.equalsIgnoreCase("NotEqualTo")
				|| s.equalsIgnoreCase("NotEqual"))
			operator = NE;
		else if (s.equals(">") 
				|| s.equalsIgnoreCase("IsMore")
				|| s.equalsIgnoreCase("IsMoreThan"))
			operator = GT;
		else if (s.equals(">=") 
				|| s.equalsIgnoreCase("IsMoreOrEqualTo")
				|| s.equalsIgnoreCase("IsMoreThanOrEqualTo"))
			operator = GE;
		else
			throw (new RuntimeException("Operator " + s + " is not defined"));
	}

	ComparableOperator operator;

	public boolean compare(Comparable c1, Comparable c2) {
		return operator.compare(c1, c2);
	}

	public boolean compare(int i1, int i2) {
		return operator.compare(new Integer(i1), new Integer(i2));
	}

	public boolean compare(long l1, long l2) {
		return operator.compare(new Long(l1), new Long(l2));
	}

	public boolean compare(double i1, double i2) {
		return operator.compare(new Double(i1), new Double(i2));
	}

	public boolean compare(Date d1, Date d2) {
		// We need this to avoid milliseconds that could fail an equality
		// comparison of two dates
		// Otherwise the entire method could be omitted
		Date dd1 = new Date(d1.getYear(), d1.getMonth(), d1.getDate(), d1
				.getHours(), d1.getMinutes());
		Date dd2 = new Date(d2.getYear(), d2.getMonth(), d2.getDate(), d2
				.getHours(), d2.getMinutes());

		boolean result = operator.compare(dd1, dd2);
		// String ft = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		// System.out.println("Compare " +
		// " d1=" + Methods.format(dd1,ft) + " d2=" + Methods.format(dd2,ft) +
		// " result=" +result);
		return result;
	}

	static interface ComparableOperator {
		public boolean compare(Comparable c1, Comparable c2);
	}

	static ComparableOperator LT = new ComparableOperator() {
		public boolean compare(Comparable c1, Comparable c2) {
			return c1.compareTo(c2) < 0;
		}
	};

	static ComparableOperator LE = new ComparableOperator() {
		public boolean compare(Comparable c1, Comparable c2) {
			return c1.compareTo(c2) <= 0;
		}
	};

	static ComparableOperator GE = new ComparableOperator() {
		public boolean compare(Comparable c1, Comparable c2) {
			return c1.compareTo(c2) >= 0;
		}
	};

	static ComparableOperator GT = new ComparableOperator() {
		public boolean compare(Comparable c1, Comparable c2) {
			return c1.compareTo(c2) > 0;
		}
	};

	static ComparableOperator EQ = new ComparableOperator() {
		public boolean compare(Comparable c1, Comparable c2) {
			return c1.compareTo(c2) == 0;
		}
	};

	static ComparableOperator NE = new ComparableOperator() {
		public boolean compare(Comparable c1, Comparable c2) {
			return c1.compareTo(c2) != 0;
		}
	};

	public static void main(String[] args) {
		
		Date date1 = new Date(2007, 5, 25, 5, 30);
		Date date2 = new Date(2007, 5, 15, 6, 30);
		Operator eq = new Operator("==");
		boolean result = eq.compare(date1, date2);
		String sign = (result) ? " == " : " != ";
		System.out.println(date1.toString() + sign + date2.toString());

		Operator le = new Operator("<=");
		result = le.compare(date1, date2);
		sign = (result) ? " <= " : " > ";
		System.out.println(date1.toString() + sign + date2.toString());

		Operator ne = new Operator("!=");
		result = ne.compare(date1, date2);
		sign = (result) ? " != " : " == ";
		System.out.println(date1.toString() + sign + date2.toString());

		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		try {
			String dd = "2/15/2007 13:30";
			Date d = df.parse(dd);
			System.out.println("Parse " + dd + " = " + d);

		} catch (Exception e) {
			System.out.println("DateFormat exception: " + e);
			e.getStackTrace();
		}
		
		Operator eq1 = new Operator("Is");
		String s1 = "Mama";
		String s2 = "Mom";
		result = eq1.compare(s1, s2);
		sign = (result) ? " = " : " != ";
		System.out.println(s1 + sign + s2);
		
		Operator eq1not = new Operator("is not");
		result = eq1not.compare(s1, s2);
		sign = (result) ? " = " : " != ";
		System.out.println(s1 + sign + s2);
		
		String op = "Is Less Than Or Equal To";
		Operator eq2 = new Operator(op);
		int i1 = 25;
		int i2 = 12;
		result = eq2.compare(i1, i2);
		System.out.println("" + i1 + " " + op + " " + i2 + ((result) ? ": true" : ": false"));
		

	}

}
