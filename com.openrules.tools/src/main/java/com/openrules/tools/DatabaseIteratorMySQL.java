package com.openrules.tools;

/*
 * Copyright (C) 2006-2011 OpenRules, Inc. 
 */

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;  

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.ResultSetDynaClass;

public class DatabaseIteratorMySQL {
	String dbName;
	Database db;
	String tableName;
	Statement stmt;
	ResultSet rs;
	Iterator<DynaBean> iter;
	
	/**
	 * Uses the default driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
	 * 
	 * @param dbName db name
	 * @param tableName table name
	 */
	public DatabaseIteratorMySQL(String dbName, String tableName) {
		this(dbName,tableName,null,null,null);
	}

	
	// MySQL: driver = "com.mysql.jdbc.Driver"
	public DatabaseIteratorMySQL(String dbName, String tableName, String user, String pwd, String driver) {
		this.dbName = dbName;
		this.tableName = tableName;
		db = new Database(dbName,user,pwd,driver);
		String sql = "SELECT * from " + tableName;
		try {
			stmt = db.getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println("Reading "+tableName+"...");
			ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
			iter = rsdc.iterator();
		} catch (Exception e) {
			System.out.print(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public boolean hasNext() {
		return iter.hasNext();
	}

	public DynaBean next() {
		DynaBean bean = (DynaBean) iter.next();
		return bean;
	}

	public void close() {
		try {
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.print(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param args parameters
	 */
	public static void mainClient(String[] args) {
		// OpenRulesEngine engine = new OpenRulesEngine("file:war/rules/data/Test.xls");
		String dbName = "RAS";
		String tableName = "2006ALL";
		DatabaseIteratorMySQL iter = new DatabaseIteratorMySQL(dbName, tableName,
				"OpenRules",null,"com.mysql.jdbc.Driver");
		int n = 0;
		while (iter.hasNext()) {
			DynaBean bean = iter.next();
			if (n == 0) {
				DynaClass dynaClass = bean.getDynaClass();
				DynaProperty properties[] = dynaClass.getDynaProperties();
				for (int i = 0; i < properties.length; i++) {
					System.out.println("Property: " + properties[i].toString());
				}
			}
			n++;
			if ((n > 0 && n % 1000 == 0)) // || i > 11000)
				System.out.println("Read " + n + " records from " + dbName
						+ " table " + tableName);
		}
		System.out.print("\nRead total " + n + " records");
		iter.close();
		
		tableName = "2006WI";
		iter = new DatabaseIteratorMySQL(dbName, tableName);
		n = 0;
		while (iter.hasNext()) {
			DynaBean bean = iter.next();
			n++;
		}
		System.out.print("\nRead total " + n + " records");
		iter.close();
	}
	
	public static void main(String[] args) {
		// OpenRulesEngine engine = new OpenRulesEngine("file:war/rules/data/Test.xls");
		String dbName = "MySQLOpenRules";
		String tableName = "customers";
//		DatabaseIterator iter = new DatabaseIterator(dbName, tableName);
		DatabaseIteratorMySQL iter = new DatabaseIteratorMySQL(dbName, tableName,
				"OpenRules",null,"com.mysql.jdbc.Driver");
		int n = 0;
		while (iter.hasNext()) {
			DynaBean bean = iter.next();
			if (n == 0) {
				DynaClass dynaClass = bean.getDynaClass();
				DynaProperty properties[] = dynaClass.getDynaProperties();
				for (int i = 0; i < properties.length; i++) {
					System.out.println("Property: " + properties[i].toString());
				}
			}
			n++;
			if ((n > 0 && n % 1000 == 0)) // || i > 11000)
				System.out.println("Read " + n + " records from " + dbName
						+ " table " + tableName);
		}
		System.out.print("\nRead total " + n + " records");
		iter.close();
		
		tableName = "customers";
		iter = new DatabaseIteratorMySQL(dbName, tableName);
		n = 0;
		while (iter.hasNext()) {
			DynaBean bean = iter.next();
			n++;
		}
		System.out.print("\nRead total " + n + " records");
		iter.close();
		
		
	}

}
