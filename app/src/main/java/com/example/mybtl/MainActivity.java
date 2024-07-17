package com.example.mybtl;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView rcvMovie;
    private ArrayList<Movie> movies;
    private MovieAdapter movieAdapter;
    private BottomNavigationView bottomNavigationView;
    private TabLayout tabCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        rcvMovie = findViewById(R.id.rcv_movie);
        movies = new ArrayList<>();
        movies.addAll(MovieDAO.getAll(this));
        movieAdapter = new MovieAdapter(this, movies, email);
        rcvMovie.setAdapter(movieAdapter);
        // thiết lập hiển thị 2 cột
        rcvMovie.setLayoutManager(new GridLayoutManager(this, 2));

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
             if(id == R.id.history){
                 //kiểm tra người dùng đã đăng nhập hay chưa
                if(email == null || email.isEmpty()){
                    showDialogLogin();
                }else {
                    Intent intent2 = new Intent(MainActivity.this, BookingHistoryActivity.class);
                    intent2.putExtra("email", email);
                    startActivity(intent2);
                }
                return false;
            }else if(id == R.id.account){
                if(email == null || email.isEmpty()){
                    showDialogLogin();
                }else {
                    Intent intent3 = new Intent(MainActivity.this, ProfileActivity.class);
                    intent3.putExtra("email", email);
                    startActivity(intent3);
                }
                return false;
            }else {
                return false;
            }
        });

        tabCategories = findViewById(R.id.tab_categories);
        String[] tabItems = getResources().getStringArray(R.array.category_items);
        for (String label : tabItems) {
            tabCategories.addTab(tabCategories.newTab().setText(label));
        }

        tabCategories.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabItem = tab.getText().toString();
                movies.clear();
                if (tabItem.equals("Tất cả")) {
                    movies.addAll(MovieDAO.getAll(MainActivity.this));
                } else {
                    movies.addAll(MovieDAO.filterByCategory(MainActivity.this, tabItem));
                }
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void showDialogLogin(){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_login, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button dialogButtonLogin = dialogView.findViewById(R.id.btnLogin);
        dialogButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });

        Button dialogButtonRegister = dialogView.findViewById(R.id.btnRegister);
        dialogButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent2);
            }
        });
    }

    public void resetData(){
        movies.clear();
        movies.addAll(MovieDAO.getAll(MainActivity.this));
        movieAdapter.notifyDataSetChanged();
    }
}

