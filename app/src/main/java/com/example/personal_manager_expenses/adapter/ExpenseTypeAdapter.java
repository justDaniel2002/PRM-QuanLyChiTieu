package com.example.prm392_personalexpensetracking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.prm392_personalexpensetracking.R;
import com.example.prm392_personalexpensetracking.model.ExpenseType;

import java.util.List;

public class ExpenseTypeAdapter extends ArrayAdapter<ExpenseType> {
    LayoutInflater layoutInflater;

    public ExpenseTypeAdapter(@NonNull Context context, int resource, @NonNull List<ExpenseType> expenseTypeList) {
        super(context, resource, expenseTypeList);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.expense_type_spinner_adapter, null, true);
        ExpenseType expenseType = getItem(position);

        TextView expenseTypeTitle = rowView.findViewById(R.id.expenseTypeTitle);
        expenseTypeTitle.setText(expenseType.getExpenseTypeTitle());

        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.expense_type_spinner_adapter, parent, false);

        ExpenseType expenseType = getItem(position);
        TextView expenseTypeTitle = convertView.findViewById(R.id.expenseTypeTitle);
        expenseTypeTitle.setText(expenseType.getExpenseTypeTitle());

        return convertView;
    }
}
