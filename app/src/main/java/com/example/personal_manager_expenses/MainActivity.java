package com.example.personal_manager_expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.personal_manager_expenses.databinding.ActivityMainBinding;
import com.example.personal_manager_expenses.model.Category;
import com.example.personal_manager_expenses.model.ExpenseType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static String displayName = "";
    public static String email = "";
    public static String currency = "đ";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    public static String intToMoneyFormat(int amount){
        if(currency == "đ")
            return (String.format("%,d", amount) + " " + currency);
        else
            return (currency + " " + String.format("%,d", amount));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        AndroidThreeTen.init(this);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        int fragmentId = getIntent().getIntExtra("FRAGMENT_ID", 0);

//        if (fragmentId == 2 && savedInstanceState == null){
//
//        }

        setContentView(binding.getRoot());

        ExpenseType.initExpenseType();
        Category.initCategory();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_report, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public static String getMonthTitle(Calendar cur){
        return new SimpleDateFormat("MMM, yyyy").format(cur.getTime());
    }
}