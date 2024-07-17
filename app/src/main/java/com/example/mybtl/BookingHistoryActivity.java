package com.example.mybtl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {

    private RecyclerView rvBillHistory;
    private ArrayList<Bill> billList = new ArrayList<>();
    private BillAdapter billAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booking_history), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvBillHistory = findViewById(R.id.rv_bill_history);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        billList = BillDAO.filterByEmail(this, email);
        billAdapter = new BillAdapter(this,billList);
        rvBillHistory.setAdapter(billAdapter);
        rvBillHistory.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setSelectedItemId(R.id.history);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.home){
                Intent intent2 = new Intent(BookingHistoryActivity.this, MainActivity.class);
                intent2.putExtra("email", email);
                startActivity(intent2);
                return true;
            }else if(id == R.id.account){
                Intent intent3 = new Intent(BookingHistoryActivity.this, ProfileActivity.class);
                intent3.putExtra("email", email);
                startActivity(intent3);
                return true;
            }else {
                return false;
            }
        });
    }

}