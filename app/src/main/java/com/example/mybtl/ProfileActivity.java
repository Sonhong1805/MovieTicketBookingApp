package com.example.mybtl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    TextView tvFullname, tvEmail, tvPhoneNumber;
    Button btn_logout,btn_changePassword;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent= getIntent();
        String email = intent.getStringExtra("email");
        User user = UserDAO.getDetails(ProfileActivity.this, email);

        tvFullname = findViewById(R.id.tv_fullname);
        tvEmail = findViewById(R.id.tv_email);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);

        tvFullname.setText("Họ tên: " + user.getFullname());
        tvEmail.setText("Email: " + user.getEmail());
        tvPhoneNumber.setText("Số điện thoại: " + user.getPhoneNumber());

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent2);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setSelectedItemId(R.id.account);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.home){
                Intent intent2 = new Intent(ProfileActivity.this, MainActivity.class);
                intent2.putExtra("email", email);
                startActivity(intent2);
                return true;
            }else if(id == R.id.history){
                Intent intent3 = new Intent(ProfileActivity.this, BookingHistoryActivity.class);
                intent3.putExtra("email", email);
                startActivity(intent3);
                return true;
            }else {
                return false;
            }
        });
        btn_changePassword = findViewById(R.id.btn_changepw);
        btn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}