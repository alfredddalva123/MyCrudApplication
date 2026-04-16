package com.example.mycrudapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPhone;
    private MaterialButton btnSave, btnCancel;
    private TextView tvFormTitle;
    private DatabaseHelper dbHelper;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. FORCE THE THEME BEFORE SETCONTENTVIEW
        setTheme(R.style.Theme_MyCrudApplication);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        tvFormTitle = findViewById(R.id.tvFormTitle);

        if (getIntent().hasExtra("ID")) {
            id = getIntent().getIntExtra("ID", -1);
            etName.setText(getIntent().getStringExtra("NAME"));
            etEmail.setText(getIntent().getStringExtra("EMAIL"));
            etPhone.setText(getIntent().getStringExtra("PHONE"));

            // REMOVE OR COMMENT OUT THESE TWO LINES IF YOU WANT
            // THE XML TEXT ("SAVE RECORD") TO STAY THE SAME
            // tvFormTitle.setText(R.string.edit_record);
            // btnSave.setText(R.string.update_record);
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phone.length() != 11) {
                etPhone.setError("Must be 11 digits");
                return;
            }

            if (id == -1) {
                dbHelper.addPersonalInfo(new PersonalInfo(name, email, phone));
                Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.updatePersonalInfo(new PersonalInfo(id, name, email, phone));
                Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}