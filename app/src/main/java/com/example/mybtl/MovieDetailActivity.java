package com.example.mybtl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
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
    private TextView tvName, tvContent, tvCategory, tvTime, tvPrice;

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
        tvTime = findViewById(R.id.tv_time);
        tvPrice = findViewById(R.id.tv_price);

        Intent intent = getIntent();
        if (intent != null) {
            int image = intent.getIntExtra("image", -1);
            String name = intent.getStringExtra("name");
            String content = intent.getStringExtra("content");
            String category = intent.getStringExtra("category");
            String time = intent.getStringExtra("time");
            int price = intent.getIntExtra("price", -1);
            int trailer = intent.getIntExtra("trailer",-1);

            imvImage.setImageResource(image);
            tvName.setText(name);
            tvContent.setText(content);
            tvCategory.setText(category);
            tvTime.setText(time);
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
        }


    }
}