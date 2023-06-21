package com.example.prm392_personalexpensetracking.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Expense implements Serializable {
    public static ArrayList<Expense> expensesList = new ArrayList<>();
    private String expenseId;
    private int cateId;
    private String description;
    private int amount;

    private Date createAt;

    public Expense() {
    }

    public Expense(String expense_id, int cateId, String description, int amount, Date createAt) {
        this.expenseId = expense_id;
        this.cateId = cateId;
        this.description = description;
        this.amount = amount;
        this.createAt = createAt;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

//    public static void addExpense(Expense ex){
//        expensesList.add(ex);
//        expensesList.sort((Expense ex1, Expense ex2) -> ex2.getCreateAt().compareTo(ex1.getCreateAt()));
//    }

//    public static void initExpenseList(){
//        expensesList.clear();
//        if (expensesList.isEmpty()){
//            Calendar c = Calendar.getInstance();
//
//            expensesList.add(new Expense("1", 6, "CMC Global tháng 2", 15000000, new Date()));
//            expensesList.add(new Expense("2", 2, "Bún đậu", 40000, new Date()));
//
//
//            c.set(2024, 1, 1);
//            expensesList.add(new Expense("3", 2, "Le Monde Steak", 650000, c.getTime()));
//            expensesList.add(new Expense("4", 3, "Bus", 18000, new Date()));
//
//            c.set(2022, 10, 19);
//            expensesList.add(new Expense("5", 4, "Thuê trọ tháng 2", 1892000, c.getTime()));
//            expensesList.add(new Expense("6", 1, "Denim jacket", 499000, new Date()));
//            expensesList.add(new Expense("7", 2, "Bún đậu", 40000, new Date()));
//            expensesList.add(new Expense("8", 2, "Le Monde Steak", 650000, new Date()));
//            expensesList.add(new Expense("9", 3, "Bus", 18000, new Date()));
//            expensesList.add(new Expense("10", 4, "Thuê trọ tháng 2", 1892000, new Date()));
//            expensesList.add(new Expense("11", 1, "Denim jacket", 499000, new Date()));
//
//            expensesList.sort((Expense ex1, Expense ex2) -> ex2.getCreateAt().compareTo(ex1.getCreateAt()));
//        }
//    }

//    public static ArrayList<Expense> getExpensesList(){
//        return expensesList;
//    }
}
