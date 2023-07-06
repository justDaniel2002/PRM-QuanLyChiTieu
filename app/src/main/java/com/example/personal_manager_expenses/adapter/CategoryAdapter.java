package com.example.personal_manager_expenses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.personal_manager_expenses.R;
import com.example.personal_manager_expenses.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    LayoutInflater layoutInflater;

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> categoryListList) {
        super(context, resource, categoryListList);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.category_spinner_adapter, null, true);
        Category category = getItem(position);

        TextView categorySpinnerTitle = rowView.findViewById(R.id.categorySpinnerTitle);
        categorySpinnerTitle.setText(category.getCateName());

        ImageView categorySpinnerIcon = rowView.findViewById(R.id.categorySpinnerIcon);
        categorySpinnerIcon.setImageResource(category.getImage());

        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.category_spinner_adapter, parent, false);

        Category category = getItem(position);

        TextView categorySpinnerTitle = convertView.findViewById(R.id.categorySpinnerTitle);
        categorySpinnerTitle.setText(category.getCateName());

        ImageView categorySpinnerIcon = convertView.findViewById(R.id.categorySpinnerIcon);
        categorySpinnerIcon.setImageResource(category.getImage());

        return convertView;
    }
}
