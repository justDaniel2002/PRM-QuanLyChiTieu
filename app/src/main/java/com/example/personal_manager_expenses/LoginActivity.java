package com.example.prm392_personalexpensetracking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    EditText emailInput;
    EditText passwordInput;
    Button loginBtn;
    TextView suggestSignUpBtn, forgotPasswordBtn;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        if(fUser != null)
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        emailInput = findViewById(R.id.loginEmailText);
        passwordInput = findViewById(R.id.loginPasswordText);
        loginBtn = findViewById(R.id.loginBtn);
        suggestSignUpBtn = findViewById(R.id.suggestRegisterBtn);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);

        suggestSignUpBtn.setOnClickListener(view ->
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );

        forgotPasswordBtn.setOnClickListener(view -> showResetDialog());

        loginBtn.setOnClickListener(view -> {
            signIn();
        });
    }
    @Override
    public void onBackPressed() {}

    private void signIn() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email cannot be empty!");
            emailInput.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password cannot be empty!");
            passwordInput.requestFocus();
        } else {
            fAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                saveProfileInfo();
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(LoginActivity.this, "Login successfully!.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void showResetDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.forgot_password_dialog);

        TextInputEditText emailInput = dialog.findViewById(R.id.resetEmailTextInput);
        Button sendResetMailBtn = dialog.findViewById(R.id.sendResetMailBtn);
        Button submitButton = dialog.findViewById(R.id.saveBtn);

        sendResetMailBtn.setOnClickListener(view -> {
            String emailInputText = emailInput.getText().toString();
            sendResetMail(emailInputText);
        });

        submitButton.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void sendResetMail(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Email sent!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}