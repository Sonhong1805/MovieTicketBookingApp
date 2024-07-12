package com.example.mybtl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    private VideoView videoView;

    private ImageView imvImage;
    private TextView tvName, tvContent, tvCategory, tvPremiere, tvPrice;

    private Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imvImage = findViewById(R.id.image_movie);
        tvName = findViewById(R.id.tv_name);
        tvCategory = findViewById(R.id.tv_category);
        tvContent = findViewById(R.id.tv_content);
        tvPremiere = findViewById(R.id.tv_premiere);
        tvPrice = findViewById(R.id.tv_price);
        btnBooking = findViewById(R.id.btn_booking);

        Intent intent = getIntent();
        if (intent != null) {
            int image = intent.getIntExtra("image", -1);
            String name = intent.getStringExtra("name");
            String content = intent.getStringExtra("content");
            String category = intent.getStringExtra("category");
            String premiere = intent.getStringExtra("premiere");
            int price = intent.getIntExtra("price", -1);
            int trailer = intent.getIntExtra("trailer",-1);
            String email = intent.getStringExtra("email");

            // đổ dữ liệu nhận được từ intent
            imvImage.setImageResource(image);
            tvName.setText(name);
            tvContent.setText(content);
            tvCategory.setText(category);
            tvPremiere.setText(premiere);
            // định dạng tiền Việt Nam
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedPrice = currencyFormatter.format(price);
            tvPrice.setText(formattedPrice);

            videoView = findViewById(R.id.vv_trailer);
            String videoPath = "android.resource://" + getPackageName() + "/" + trailer;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);

            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);

            btnBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (email == null || email.isEmpty()) {
                        showDialogLogin();
                    }else {
                        Intent intent2 = new Intent(MovieDetailActivity.this, BookingActivity.class);
                        intent2.putExtra("name", name);
                        intent2.putExtra("premiere", premiere);
                        intent2.putExtra("price", price);
                        intent2.putExtra("email", email);
                        startActivity(intent2);
                    }
                }
            });
        }
    }
    private void showDialogLogin(){
        LayoutInflater inflater = LayoutInflater.from(MovieDetailActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_login, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button dialogButtonLogin = dialogView.findViewById(R.id.btnLogin);
        dialogButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}