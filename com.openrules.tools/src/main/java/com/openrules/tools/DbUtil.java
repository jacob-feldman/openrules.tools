package com.openrules.tools;

/*
 * Copyright (c) 2005-2007 OpenRules, Inc. 	
 */

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DbUtil {

    static HashMap map = new HashMap();

    static public DbUtil getDBInstance(String dbName) {
        return getDBInstance(dbName, null, null, null);
    }

    static synchronized public DbUtil getDBInstance(
            String dbName,
            String user,
            String pwd,
            String driver) {
        DbEnv env = new DbEnv();
        env.driver = driver;
        env.dsn = dbName;
        env.user_name = user;
        env.password = pwd;

        DbUtil dbu = (DbUtil) map.get(env);
        if (dbu == null) {
            dbu = new DbUtil(env);
            map.put(env, dbu);
        }
        return dbu;
    }

    Connection connection;
    DbEnv env;

    public DbUtil(DbEnv env) {
        this.env = env;
    }

    public synchronized int count(String tableName) throws Exception {
        String sql = "SELECT count(*) from " + tableName;

        ResultSet rs = executeQuery(sql);

        rs.next();

        int count = rs.getInt(1);

        rs.close();
        return count;
    }

    public synchronized ResultSet selectAll(String tableName) throws Exception, Exception {
        String sql = "SELECT * from " + tableName;
        ResultSet rs = executeQuery(sql);
        return rs;
    }

    public synchronized String[] getArray(String tableName, String columnName)
            throws Exception {
        int n = count(tableName);
        String[] ary = new String[n];

        ResultSet rs = executeQuery("SELECT " + columnName + " from " + tableName);

        try {
            int columnIndex = rs.findColumn(columnName);

            for (int i = 0; i < n; i++) {
                rs.next();
                ary[i] = rs.getString(columnIndex);
            }
        } finally {
            rs.getStatement().close();
//			rs.close();
        }
        return ary;
    }

    public String[] getArrayFromDB(String dbName, String tableName, String columnName)
            throws Exception {
        return getDBInstance(dbName).getArray(tableName, columnName);
    }

    public synchronized ResultSet executeQuery(String sql) throws Exception {
        Statement stmt = getConnection().createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        return rs;
    }

    protected synchronized void initConnection() throws Exception {
        String driverName = env.driver;

        if (driverName == null)
            driverName = "sun.jdbc.odbc.JdbcOdbcDriver";

        Class.forName(driverName); // loads the driver
        String dsnName = env.dsn;
        String usrName = env.user_name;
        String usrPwd = env.password;

        out().println(
                "Connecting to '" + dsnName + "' using '" + driverName + "' ...");

        connection = DriverManager.getConnection(
                "jdbc:odbc:" + dsnName,
                usrName,
                usrPwd);

        connection.setAutoCommit(false);

        out().println(
                "Connecting to '"
                        + dsnName
                        + "' using '"
                        + driverName
                        + "' ... Done");
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

    static public class DbEnv {
        public String driver, dsn, user_name, password;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((driver == null) ? 0 : driver.hashCode());
            result = prime * result + ((dsn == null) ? 0 : dsn.hashCode());
            result = prime * result + ((password == null) ? 0 : password.hashCode());
            result = prime * result + ((user_name == null) ? 0 : user_name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            DbEnv other = (DbEnv) obj;
            if (driver == null) {
                if (other.driver != null)
                    return false;
            } else if (!driver.equals(other.driver))
                return false;
            if (dsn == null) {
                if (other.dsn != null)
                    return false;
            } else if (!dsn.equals(other.dsn))
                return false;
            if (password == null) {
                if (other.password != null)
                    return false;
            } else if (!password.equals(other.password))
                return false;
            if (user_name == null) {
                if (other.user_name != null)
                    return false;
            } else if (!user_name.equals(other.user_name))
                return false;
            return true;
        }

    }

}