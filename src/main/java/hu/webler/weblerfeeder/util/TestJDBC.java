package hu.webler.weblerfeeder.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/webler_feeder?serverTimezone=UTC&useSSL=false";
        String user = "webler_feeder";
        String password = "webler_feeder";
        String driver = "com.mysql.jdbc.Driver";

        try {

            System.out.println("Connect to db"+ url);
            //Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
