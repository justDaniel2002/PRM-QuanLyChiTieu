package com.example.personal_manager_expenses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_personalexpensetracking.ui.home.HomeFragment;
import com.example.prm392_personalexpensetracking.ui.settings.SettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    Button signUpBtn;
    TextView suggestLoginBtn;

    FirebaseFirestore fStore;
    FirebaseUser fUser;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.editTextTextPersonName);
        emailInput = findViewById(R.id.editTextTextEmailAddress);
        passwordInput = findViewById(R.id.editTextTextPassword);
        signUpBtn = findViewById(R.id.signUpBtn);
        suggestLoginBtn = findViewById(R.id.suggestLoginBtn);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        suggestLoginBtn.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        signUpBtn.setOnClickListener(view -> {
            createAccount();
        });
    }
    @Override
    public void onBackPressed() {}

    private void createAccount() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(TextUtils.isEmpty(name)){
            nameInput.setError("Name cannot be empty!");
            nameInput.requestFocus();
        } else if(TextUtils.isEmpty(email)){
            emailInput.setError("Email cannot be empty!");
            emailInput.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password cannot be empty!");
            passwordInput.requestFocus();
        } else  if (!isValidPassword(password.trim())) {
            Toast.makeText(this, "Choose a stronger password!", Toast.LENGTH_SHORT).show();
        } else{
            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Map<String, Object> info = new HashMap<>();
                                info.put("displayName", name);
                                info.put("currency", "$");

                                String uid = fAuth.getUid();
                                fStore.collection("Data").document(uid).set(info).addOnSuccessListener(unused2 -> {
//                                sendEmailVerification();

                                    Toast.makeText(RegisterActivity.this, "Register successfully!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("FRAGMENT_ID", 2);
//                                intent.putExtra("methodName", "chooseCurrency");
                                    startActivity(intent);
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    private void sendEmailVerification() {
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fUser != null) {
            fUser.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Email sent!",
                                        Toast.LENGTH_SHORT).show();
                                showVerifyEmailDialog();
                            } else {
                                // Failed to send verification email
                            }
                        }
                    });
        }
    }

    private void showVerifyEmailDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.faq_dialog);

        TextView faqHeader = dialog.findViewById(R.id.forgotPasswordHeader);
        TextView faqContent = dialog.findViewById(R.id.faqContent);

        faqHeader.setText("Notice!");
        faqContent.setText("Please verify the account before login!");

        Button submitButton = dialog.findViewById(R.id.saveBtn);
        submitButton.setOnClickListener(view -> {
            dialog.dismiss();
            startActivity(new Intent(   RegisterActivity.this, LoginActivity.class));
        });

        dialog.show();
    }
}


//Forgot password

/*
FirebaseAuth auth = FirebaseAuth.getInstance();
String emailAddress = "user@example.com";

auth.sendPasswordResetEmail(emailAddress)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Email sent.");
                }
            }
        });
 */
