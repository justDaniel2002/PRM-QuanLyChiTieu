package com.example.prm392_personalexpensetracking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_personalexpensetracking.ExpenseActivity;
import com.example.prm392_personalexpensetracking.MainActivity;
import com.example.prm392_personalexpensetracking.R;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.CategoryReport;
import com.example.prm392_personalexpensetracking.model.Expense;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CategoryReportAdapter extends RecyclerView.Adapter<CategoryReportAdapter.ViewHolder> {

    private final ArrayList<CategoryReport> categoryReportArrayList;
    private Context context;
    private Calendar calendar;

    public CategoryReportAdapter(ArrayList<CategoryReport> expenseList, Context context) {
        this.categoryReportArrayList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        calendar = Calendar.getInstance();
        View mExpensesItemView = mInflater.inflate(R.layout.dashboard_expense_item, parent, false);
        return new ViewHolder(mExpensesItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat formater = new DecimalFormat("#.##");

        CategoryReport categoryReport = categoryReportArrayList.get(position);
        Category category = categoryReport.getCategory();

        holder.catTitle.setText(category.getCateName());
        holder.description.setText(categoryReport.getCount() + " transaction" + (categoryReport.getCount() > 1 ? "s" : ""));
        holder.price.setText(MainActivity.intToMoneyFormat(categoryReport.getSum()));
        holder.catIcon.setImageResource(category.getImage());
        holder.expenseDate.setText(formater.format(categoryReport.getPercent()) + "%");

        if(category.getType() == 2){
            holder.price.setTextColor(ContextCompat.getColor(context, R.color.green_income));
        }
        else if(category.getType() == 1) {
                holder.price.setTextColor(ContextCompat.getColor(context, R.color.red_expense));
            }

//        holder.dashboardExpenseItem.setOnClickListener(view -> {
//            Intent intent = new Intent(context, ExpenseActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("selected_expense", categoryReport);
//            intent.putExtras(bundle);
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return categoryReportArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final CategoryReportAdapter mAdapter;
        private ConstraintLayout dashboardExpenseItem;
        private ImageView catIcon;
        private TextView catTitle, description, price, expenseDate;

        public ViewHolder(@NonNull View itemView, CategoryReportAdapter expensesDayAdapter) {
            super(itemView);
            this.mAdapter = expensesDayAdapter;
            dashboardExpenseItem = itemView.findViewById(R.id.dashboard_expense_item);
            catIcon = itemView.findViewById(R.id.expenseCatIcon);
            catTitle = itemView.findViewById(R.id.expenseCatTitle);
            description = itemView.findViewById(R.id.expenseDes);
            price = itemView.findViewById(R.id.expensePrice);
            expenseDate = itemView.findViewById(R.id.expenseDate);
        }
    }
}
