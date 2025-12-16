package com.expensetracker;
import java.sql.*;
import java.util.*;

public class ExpenseDAO {
    public static void addExpense(Expense exp) {
        try {
            Connection conn = DBConnector.connect();
            String sql = "INSERT INTO expenses (amount, category, note, date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, exp.amount);
            ps.setString(2, exp.category);
            ps.setString(3, exp.note);
            ps.setDate(4, exp.date);
            ps.executeUpdate();
            System.out.println("Expense added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Map<String, Double> getExpenseSummaryByCategory() {
        Map<String, Double> map = new HashMap<>();
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT category, SUM(amount) AS total FROM expenses GROUP BY category"
             );
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                map.put(rs.getString("category"), rs.getDouble("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();
        try {
            Connection conn = DBConnector.connect();
            String sql = "SELECT * FROM expenses";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Expense e = new Expense(
                    rs.getInt("id"),
                    rs.getDouble("amount"),
                    rs.getString("category"),
                    rs.getString("note"),
                    rs.getDate("date")
                );
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

