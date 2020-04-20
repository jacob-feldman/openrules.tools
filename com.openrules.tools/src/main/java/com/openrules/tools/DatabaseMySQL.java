package com.openrules.tools;

/*
 * Copyright (C) 2006-2011 OpenRules, Inc. 
 */

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.beanutils.ResultSetDynaClass;


//FIXME merge with Database class

public class DatabaseMySQL {

    public String driver, dsn, user_name, password;
    Connection connection;

    public DatabaseMySQL(String dbName) {
        this(dbName, null, null, null);
    }

    public DatabaseMySQL(String dbName, String user, String pwd, String driver) {
        this.driver = driver;
        this.dsn = dbName;
        this.user_name = user;
        this.password = pwd;
        try {
            initConnection();
        } catch (Exception e) {
            throw new RuntimeException("Open connection failed", e );
        }
    }

    public synchronized ResultSet executeQuery(String sql) throws Exception {
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    protected synchronized void initConnection() throws Exception {
        String driverName = driver;

        if (driverName == null)
            driverName = "sun.jdbc.odbc.JdbcOdbcDriver";

        out().println("Connecting to '" + dsn + "' using '" + driverName + "' ...");
//			Class.forName(driverName); // loads the driver
        String dsnName = dsn;
        String usrName = user_name;
        String usrPwd = password;

        Class driverClass = Class.forName(driverName);
        DriverManager.registerDriver((Driver) driverClass.newInstance());

        String url = "jdbc:mysql://localhost:3306/" + dsnName;
        // String url = "jdbc:odbc:" + dsnName;
        connection = DriverManager.getConnection(url, usrName, usrPwd);

        connection.setAutoCommit(false);

        out().println("Connected");
    }

    public synchronized void freeConnection() throws Exception {
        out().println("Free Connection ...");
        if (connection != null) {
            out().println("Close Connection");
            connection.close();
            connection = null;
        }
        out().println("Free Connection ...Done");
    }

    public synchronized Connection getConnection() throws Exception {
        if (connection == null) {
            initConnection();
        }
        return connection;
    }

    PrintStream out() {
        return System.out;
    }

    public synchronized ResultSet createAndExecuteBriefQuery(String tableName) {
        try {
            String sql = "SELECT * from " + tableName;
            ResultSet rs = executeQuery(sql);
            System.out.println("OK for createAndExecuteBriefQuery");
            return rs;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Database: SQL error in createAndExecuteBriefQuery: " + e);
        }
    }

    public synchronized ResultSetDynaClass readDataInstances(String tableName) {
        try {
            ResultSet rs = createAndExecuteBriefQuery(tableName);
            System.out.println("Reading from DB to ResultSetDynaClass...");
            ResultSetDynaClass rows = new ResultSetDynaClass(rs);
            // rs.close();
            return rows;
        } catch (Exception e) {
            // e.printStackTrace();
            throw new RuntimeException("ERROR in Database.readDataInstances1", e);
        }

    }

}