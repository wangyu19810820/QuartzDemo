package com.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useUnicode=true&characterEncoding=utf-8", "root", "1111");

    }
}
