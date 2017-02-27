package com.szh.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zhihaosong on 17-2-27.
 */
public class TestDeby {
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    String dbName = "/home/zhihaosong/workspace/com-szh-concurrent/src/main/java/com/szh/demo/qrcode/deby.DB";

    public static void loadDriver() {
        try {
            Class.forName(driver).newInstance();
            System.out.println("Loadedtheappropriatedriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doIt() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        System.out.println("starting");
        try {
            conn = DriverManager.getConnection(protocol + dbName + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(dbName);
        try {
            s = conn.createStatement();
            rs = s.executeQuery("select * from firsttable");
            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestDeby t = new TestDeby();
        loadDriver();
        t.doIt();
    }
}
