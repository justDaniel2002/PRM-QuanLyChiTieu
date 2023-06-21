package com.example.prm392_personalexpensetracking.model;

public class CategoryReport {
    private Category category;
    private int count;
    private int sum;
    private double percent;

    public CategoryReport(Category category, int count, int sum, double percent) {
        this.category = category;
        this.count = count;
        this.sum = sum;
        this.percent = percent;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
