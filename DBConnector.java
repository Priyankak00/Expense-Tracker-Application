package com.expensetracker;
import java.sql.*;

public class DBConnector {
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root", "gotohell");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
