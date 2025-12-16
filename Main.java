package com.expensetracker;
import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter note: ");
        String note = sc.nextLine();

        System.out.print("Enter date (yyyy-mm-dd): ");
        String dateStr = sc.nextLine();
        Date date = Date.valueOf(dateStr);

        Expense e = new Expense(0, amount, category, note, date);
        ExpenseDAO.addExpense(e);

        System.out.println("All Expenses:");
        for (Expense exp : ExpenseDAO.getAllExpenses()) {
            System.out.println(exp.id + " | " + exp.amount + " | " + exp.category + " | " + exp.note + " | " + exp.date);
        }
    }
}
