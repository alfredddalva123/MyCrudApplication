package com.example.mycrudapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText etRegUsername, etRegPassword, etRegConfirmPassword;
    private Button btnRegister;
    private TextView tvBackToLoginText;
    private ImageButton btnBackArrow;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Database Helper
        dbHelper = new DatabaseHelper(this);

        // Bind Views - Ensure IDs match XML exactly
        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegConfirmPassword = findViewById(R.id.etRegConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvBackToLoginText = findViewById(R.id.tvBackToLogin);
        btnBackArrow = findViewById(R.id.btnBackToLogin);

        // Back Arrow Action: Simply closes current activity
        btnBackArrow.setOnClickListener(v -> finish());

        // Text Link Action: Simply closes current activity
        tvBackToLoginText.setOnClickListener(v -> finish());

        // Sign Up Button Action
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etRegUsername.getText().toString().trim();
                String pass = etRegPassword.getText().toString().trim();
                String confirmPass = etRegConfirmPassword.getText().toString().trim();

                // 1. Validation: Empty Fields
                if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. Validation: Passwords Match
                if (!pass.equals(confirmPass)) {
                    etRegConfirmPassword.setError("Passwords do not match");
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. Database: Check if user exists, then register
                if (dbHelper.isUsernameExists(user)) {
                    etRegUsername.setError("Username already taken");
                    Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    long result = dbHelper.registerUser(user, pass);
                    if (result > 0) {
                        Toast.makeText(SignupActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        finish(); // Returns to the Login screen (MainActivity)
                    } else {
                        Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}