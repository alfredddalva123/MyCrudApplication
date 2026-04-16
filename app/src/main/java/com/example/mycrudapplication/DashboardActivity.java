package com.example.mycrudapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements PersonalInfoAdapter.OnItemAction {

    private ListView lvPersonalInfo;
    private DatabaseHelper dbHelper;
    private PersonalInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dbHelper = new DatabaseHelper(this);
        lvPersonalInfo = findViewById(R.id.lvPersonalInfo);
        ImageButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnAddRecord = findViewById(R.id.btnAddRecord);

        btnBack.setOnClickListener(v -> finish());

        btnAddRecord.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AddEditActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Refresh list every time we return to this screen
    }

    private void loadData() {
        List<PersonalInfo> infoList = dbHelper.getAllPersonalInfo();
        // Pass 'this' as the third argument because this Activity now implements OnItemAction
        adapter = new PersonalInfoAdapter(this, infoList, this);
        lvPersonalInfo.setAdapter(adapter);
    }

    // --- Interface Methods from PersonalInfoAdapter.OnItemAction ---

    @Override
    public void onEdit(PersonalInfo info) {
        Intent intent = new Intent(DashboardActivity.this, AddEditActivity.class);
        intent.putExtra("ID", info.getId());
        intent.putExtra("NAME", info.getName());
        intent.putExtra("EMAIL", info.getEmail());
        intent.putExtra("PHONE", info.getPhone());
        startActivity(intent);
    }

    @Override
    public void onDelete(PersonalInfo info) {
        dbHelper.deletePersonalInfo(info.getId());
        Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
        loadData(); // Refresh the list after deleting
    }
}