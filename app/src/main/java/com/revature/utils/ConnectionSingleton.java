package com.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSingleton {

    private static ConnectionSingleton cs;
    private static Properties prop = new Properties();

    private ConnectionSingleton() {}

    public static synchronized ConnectionSingleton getConnectionSingleton() {
        if (cs == null) {
            cs = new ConnectionSingleton();
        }
        return cs;
    }

    public Connection getConnection() {
        ClassLoader classloader = getClass().getClassLoader();
        InputStream is = classloader.getResourceAsStream("jdbc.properties");
        String url = "";
        String username = "";
        String password = "";
        Connection con;

        try {
            prop.load(is);
            url = (String) prop.getProperty("url");
            username = (String) prop.getProperty("username");
            password = (String) prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
