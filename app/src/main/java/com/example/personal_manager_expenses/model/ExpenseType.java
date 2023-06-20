package com.example.prm392_personalexpensetracking.model;

import java.util.ArrayList;

public class ExpenseType {
    private static final ArrayList<ExpenseType> expenseTypeList = new ArrayList<>();
    private int expenseTypeId;
    private String expenseTypeTitle;

    public ExpenseType(int expenseTypeId, String expenseTypeTitle) {
        this.expenseTypeId = expenseTypeId;
        this.expenseTypeTitle = expenseTypeTitle;
    }

    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getExpenseTypeTitle() {
        return expenseTypeTitle;
    }

    public void setExpenseTypeTitle(String expenseTypeTitle) {
        this.expenseTypeTitle = expenseTypeTitle;
    }

    public static void initExpenseType(){
        expenseTypeList.clear();

        expenseTypeList.add(new ExpenseType(1, "Outcome"));
        expenseTypeList.add(new ExpenseType(2, "Income"));
//        expenseTypeList.add(new ExpenseType(3, "Loan / Debt"));
    }

    public static ArrayList<ExpenseType> getExpenseTypeList(){
        return expenseTypeList;
    }
}
