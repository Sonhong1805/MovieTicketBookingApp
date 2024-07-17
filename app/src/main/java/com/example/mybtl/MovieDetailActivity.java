package com.example.mybtl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    private VideoView videoView;

    private ImageView imvImage;
    private TextView tvName, tvContent, tvCategory, tvPremiere, tvPrice;

    private Button btnBooking;
    private Spinner spnDate,spnTime;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private List<String> allTimes;

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
        spnDate = findViewById(R.id.spn_date);
        spnTime = findViewById(R.id.spn_time);

        Intent intent = getIntent();
        int idMovie = intent.getIntExtra("id",-1);
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
        tvCategory.setText("Thể loại: " + category);
        tvPremiere.setText("Ngày khởi chiếu: " + premiere);
        // định dạng tiền Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormatter.format(price);
        tvPrice.setText("Giá vé: " + formattedPrice);

        videoView = findViewById(R.id.vv_trailer);
        String videoPath = "android.resource://" + getPackageName() + "/" + trailer;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        ArrayList<String> dates = new ArrayList<>();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            dates.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDate.setAdapter(adapter);


        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        allTimes = new ArrayList<>();
        String[] timesArray01 = getResources().getStringArray(R.array.time_items);
        for (String time : timesArray01) {
            allTimes.add(time);
        }

        spnDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDate = (String) parent.getItemAtPosition(position);
                updateTimesSpinner(selectedDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateTimesSpinner(dateFormat.format(new Date()));

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email == null || email.isEmpty()) {
                    showDialogLogin();
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date premiereDate;
                    try {
                        premiereDate = dateFormat.parse(premiere);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return;
                    }

                    Calendar calendar = Calendar.getInstance();
                    Date today = calendar.getTime();
                    calendar.add(Calendar.DAY_OF_YEAR, 3);
                    if (premiereDate != null &&  premiereDate.after(today)) {
                        Toast.makeText(MovieDetailActivity.this, "Chưa đến ngày khởi chiếu", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent2 = new Intent(MovieDetailActivity.this, BookingActivity.class);
                        intent2.putExtra("idMovie", idMovie);
                        intent2.putExtra("name", name);
                        intent2.putExtra("premiere", premiere);
                        intent2.putExtra("price", price);
                        intent2.putExtra("email", email);
                        String dateOrder = (String) spnDate.getSelectedItem();
                        String timeOrder = (String) spnTime.getSelectedItem();
                        intent2.putExtra("dateOrder", dateOrder);
                        intent2.putExtra("timeOrder", timeOrder);
                        startActivity(intent2);
                    }
                }
            }
        });
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

    private void updateTimesSpinner(String selectedDate) {
        List<String> validTimes = new ArrayList<>();
        try {
            Date selectedDateParsed = dateFormat.parse(selectedDate);
            Date currentDate = new Date();

            // Nếu ngày được chọn là ngày hiện tại, chỉ hiển thị các giờ chưa qua
            if (selectedDateParsed != null && dateFormat.format(selectedDateParsed).equals(dateFormat.format(currentDate))) {
                Calendar currentCalendar = Calendar.getInstance();
                int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentCalendar.get(Calendar.MINUTE);

                for (String time : allTimes) {
                    Date itemTime = timeFormat.parse(time);
                    if (itemTime != null) {
                        Calendar itemCalendar = Calendar.getInstance();
                        itemCalendar.setTime(itemTime);
                        int itemHour = itemCalendar.get(Calendar.HOUR_OF_DAY);
                        int itemMinute = itemCalendar.get(Calendar.MINUTE);

                        // So sánh giờ và phút
                        if (itemHour > currentHour || (itemHour == currentHour && itemMinute > currentMinute)) {
                            validTimes.add(time);
                        }
                    }
                }
            } else {
                // Nếu ngày được chọn là ngày trong tương lai, hiển thị tất cả các giờ
                validTimes.addAll(allTimes);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (validTimes != null && !validTimes.isEmpty()) {
            // Tạo adapter mới cho Spinner thời gian với danh sách đã lọc
            ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, validTimes);
            timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnTime.setAdapter(timeAdapter); // Đảm bảo đối tượng không null ở đây
        } else {
            // Xử lý trường hợp validTimes rỗng hoặc null
            spnTime.setAdapter(null);
        }
    }
}

