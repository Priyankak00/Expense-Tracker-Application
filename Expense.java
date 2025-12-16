package com.expensetracker;
import java.sql.Date;

public class Expense {
    int id;
    double amount;
    String category;
    String note;
    Date date;

    public Expense(int id, double amount, String category, String note, Date date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }
}