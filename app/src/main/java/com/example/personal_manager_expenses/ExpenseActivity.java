package com.example.personal_manager_expenses;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.personal_manager_expenses.adapter.CategoryAdapter;
import com.example.personal_manager_expenses.adapter.ExpenseTypeAdapter;
import com.example.personal_manager_expenses.model.Category;
import com.example.personal_manager_expenses.model.Expense;
import com.example.personal_manager_expenses.model.ExpenseType;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExpenseActivity extends AppCompatActivity {

    Button dateButton, saveBtn, deleteBtn;
    Calendar cal = Calendar.getInstance();
    int day, month, year;
    Spinner expenseTypeSpinner, categorySpinner;
    TextInputEditText amount, description;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    Bundle bundle;
    Expense currentExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.addExpenseTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        amount = findViewById(R.id.textAddExpenseAmount);
        description = findViewById(R.id.textAddExpenseDescription);

        clear();

        int catType = 1;
        Category cat = null;

        btnsSetup();

        bundle = getIntent().getExtras();
        if(bundle != null){
            currentExpense = (Expense) bundle.get("selected_expense");

            amount.setText(Integer.toString(currentExpense.getAmount()));
            description.setText(currentExpense.getDescription());

            catType = Category.getCategoryById(currentExpense.getCateId()).getType();
            cat = Category.getCategoryById(currentExpense.getCateId());

            cal.setTime(currentExpense.getCreateAt());

            deleteBtn.setVisibility(View.VISIBLE);
        }

        spinnerSetup(catType, cat);
        datePickerSetup();
    }

    private void spinnerSetup(int catType, Category cat){
        expenseTypeSpinner = findViewById(R.id.expenseTypeSpinner);
        ExpenseTypeAdapter expenseTypeAdapter = new ExpenseTypeAdapter(this, R.layout.expense_type_spinner_adapter, ExpenseType.getExpenseTypeList());
        expenseTypeSpinner.setAdapter(expenseTypeAdapter);
        expenseTypeSpinner.setSelection(catType - 1);

        ArrayList<Category> catList = Category.getCategoryList(catType);
        categorySpinner = findViewById(R.id.categorySpinner);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, R.layout.category_spinner_adapter, catList);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setSelection(catList.indexOf(cat));
        expenseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categorySpinner.setAdapter(new CategoryAdapter(ExpenseActivity.this, R.layout.category_spinner_adapter, Category.getCategoryList(i+1)));
                categorySpinner.setSelection(catList.indexOf(cat));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void datePickerSetup(){
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        dateButton = findViewById(R.id.btnDatePicker);
        dateButton.setText(dateToString(day, month+1, year));

        dateButton.setOnClickListener(view -> showDatePickerDialog(day, month, year));
    }

    private void btnsSetup(){
        saveBtn = findViewById(R.id.saveExpenseBtn);
        saveBtn.setOnClickListener(view -> saveExpenseHandled());

        deleteBtn = findViewById(R.id.deleteExpenseBtn);
        deleteBtn.setOnClickListener(view -> deleteExpenseHandled());
    }

    private void deleteExpenseHandled() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseActivity.this);
        builder.setMessage("Do you really want to delete this transaction?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String expenseId = currentExpense.getExpenseId();
                        fStore.collection("Data").document(fAuth.getUid()).collection("Expenses").document(expenseId).delete().addOnSuccessListener(unused -> {
                            Toast.makeText(ExpenseActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> Toast.makeText(ExpenseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());

                        Intent intent = new Intent(ExpenseActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }

    private void saveExpenseHandled(){
        ExpenseType currentExpenseType = ExpenseType.getExpenseTypeList().get(expenseTypeSpinner.getSelectedItemPosition());
        Category currentCategory = Category.getCategoryList(currentExpenseType.getExpenseTypeId()).get(categorySpinner.getSelectedItemPosition());

        if(amount.getText().length() == 0){
            showErrorAmountRequiredAlert();
            return;
        }
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        String id = bundle == null ? UUID.randomUUID().toString() : currentExpense.getExpenseId();
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("expenseId", id);
        transaction.put("cateId", currentCategory.getCateId());
        transaction.put("description", description.getText().toString());
        transaction.put("amount", Integer.parseInt(amount.getText().toString()));
        transaction.put("createAt", c.getTime());

        if(bundle == null){
            fStore.collection("Data").document(fAuth.getUid()).collection("Expenses").document(id).set(transaction).addOnSuccessListener(unused -> {
                Toast.makeText(ExpenseActivity.this, "Added", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> Toast.makeText(ExpenseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }
        else{
            fStore.collection("Data").document(fAuth.getUid()).collection("Expenses").document(id).update(transaction).addOnSuccessListener(unused -> {
                Toast.makeText(ExpenseActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> Toast.makeText(ExpenseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showErrorAmountRequiredAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Amount is required!");

        alertDialog.setMessage("The amount must not be empty!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());

        alertDialog.show();
    }

    private String dateToString(int day, int month, int year){
        LocalDate date = null;
        date = LocalDate.of(year, month, day);
        return  date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private void showDatePickerDialog(int day, int month, int year){
        DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            dateButton.setText(dateToString(i2, i1+1, i));
            this.day = i2;
            this.month = i1;
            this.year = i;
        }, year, month, day);

        dialog.show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clear();
    }

    private void clear(){
        amount.setText("");
        description.setText("");
        cal = Calendar.getInstance();
    }
}