package com.example.personal_manager_expenses.model;

import com.example.personal_manager_expenses.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Category {
    public static ArrayList<Category> categoriesList = new ArrayList<>();
    private int cateId;
    private String cateName;
    private int parentCateId;
    private String iconName;
    private int type;
    /*
        1. Income
        2. Outcome
        3. Loan / Debt
     */

    public Category() {
        initCategory();
    }

    public Category(String cateName) {
        this.cateName = cateName;
    }

    public Category(int cateId, String cateName, int parentCateId, int type) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.parentCateId = parentCateId;
        this.type = type;;
        this.iconName = iconName;
//        this.child_cate = child_cate;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public int getParentCateId() {
        return parentCateId;
    }

    public void setParentCateId(int parentCateId) {
        this.parentCateId = parentCateId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Category getCategoryById(int id){
        return categoriesList.stream().filter((item) -> item.getCateId() == id).findFirst().get();
    }

    public static void initCategory(){
        categoriesList.clear();
        if(categoriesList.isEmpty()){
            categoriesList.add(new Category(102, "Food & Beverage", -1, 1));
            categoriesList.add(new Category(115, "Party", 2, 1));
            categoriesList.add(new Category(107, "Cafe", 2, 1));

            categoriesList.add(new Category(116, "Self Development", -1, 1));
            categoriesList.add(new Category(109, "Education", 16, 1));
            categoriesList.add(new Category(117, "Sport", 16, 1));
            categoriesList.add(new Category(113, "Health", 16, 1));

            categoriesList.add(new Category(101, "Shopping", -1, 1));
            categoriesList.add(new Category(110, "Electronics", 1, 1));

            categoriesList.add(new Category(103, "Transport", -1, 1));
            categoriesList.add(new Category(114, "Maintenance", 3, 1));
            categoriesList.add(new Category(111, "Fuel", 3, 1));

            categoriesList.add(new Category(104, "Bills & Utilities", -1, 1));
            categoriesList.add(new Category(108, "Donate", -1, 1));
            categoriesList.add(new Category(118, "Savings", -1, 1));
            categoriesList.add(new Category(105, "Other", -1, 1));

            categoriesList.add(new Category(206, "Salary", -1, 2));
            categoriesList.add(new Category(212, "Gifts", -1, 2));
            categoriesList.add(new Category(219, "Other", -1, 2));
        }
    }

    public int getImage(){
        switch (getCateId()){
            case 101:
                return R.drawable.groceries;
            case 102:
                return R.drawable.restaurant;
            case 103:
                return R.drawable.transportation;
            case 104:
                return R.drawable.laundry;
            case 105:
            case 219:
                return R.drawable.money;
            case 206:
                return R.drawable.institute;
            case 107:
                return R.drawable.cafe;
            case 108:
                return R.drawable.donate;
            case 109:
                return R.drawable.education;
            case 110:
                return R.drawable.electronics;
            case 111:
                return R.drawable.fuel;
            case 212:
                return R.drawable.gifts;
            case 113:
                return R.drawable.health;
            case 114:
                return R.drawable.maintenance;
            case 115:
                return R.drawable.party;
            case 116:
                return R.drawable.self_development;
            case 117:
                return R.drawable.sport;
            case 118:
                return R.drawable.savings;
        }
//        return R.drawable.groceries;
        return 0;
    }
    public static ArrayList<Category> getCategoryList(int cateTypeId){
        return new ArrayList<Category>(categoriesList.stream().filter(category -> category.getType() == cateTypeId).collect(Collectors.toList()));
    }
}
