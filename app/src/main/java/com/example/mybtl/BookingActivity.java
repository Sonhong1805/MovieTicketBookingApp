package com.example.mybtl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    Button btnConfirm;
    TextView tvName, tvPrice, tvPremiere;
    EditText edtDrink,edtFood;
    String foodResult = "";
    Button chair1, chair2, chair3,chair4,chair5,chair6,chair7,chair8,chair9,chair10;
    List<Button> selectedChairs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booking), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvName = findViewById(R.id.tv_name);
        tvPrice = findViewById(R.id.tv_price);
        tvPremiere = findViewById(R.id.tv_premiere);
        edtDrink = findViewById(R.id.edt_drink);
        edtFood = findViewById(R.id.edt_food);

        chair1 = findViewById(R.id.chair1);
        chair2 = findViewById(R.id.chair2);
        chair3 = findViewById(R.id.chair3);
        chair4 = findViewById(R.id.chair4);
        chair5 = findViewById(R.id.chair5);
        chair6 = findViewById(R.id.chair6);
        chair7 = findViewById(R.id.chair7);
        chair8 = findViewById(R.id.chair8);
        chair9 = findViewById(R.id.chair9);
        chair10 = findViewById(R.id.chair10);

        chair1.setOnClickListener(chairClickListener);
        chair2.setOnClickListener(chairClickListener);
        chair3.setOnClickListener(chairClickListener);
        chair4.setOnClickListener(chairClickListener);
        chair5.setOnClickListener(chairClickListener);
        chair6.setOnClickListener(chairClickListener);
        chair7.setOnClickListener(chairClickListener);
        chair8.setOnClickListener(chairClickListener);
        chair9.setOnClickListener(chairClickListener);
        chair10.setOnClickListener(chairClickListener);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int price = intent.getIntExtra("price", -1);
        String premiere = intent.getStringExtra("premiere");

        tvName.setText(name);
        tvPremiere.setText(premiere);
        // định dạng tiền Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormatter.format(price);
        tvPrice.setText(formattedPrice);

        btnConfirm = findViewById(R.id.btnConfirm);
        // mở hộp thoại xác nhận vé
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(BookingActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_booking, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();
                dialog.show();

                TextView tvName,tvPrice,tvPremiere,tvSelectedFood,tvQuantity, tvSelectedChair,tvTotalPrice;
                Button btnCancel,btnAgree;

                // chọn số lượng đồ ăn/uống
                int drinkQuantity = Integer.parseInt(edtDrink.getText().toString());
                int foodQuantity = Integer.parseInt(edtFood.getText().toString());
                foodResult = "";

                if (drinkQuantity == 0 && foodQuantity == 0) {
                    foodResult = "0";
                } else {
                    if (drinkQuantity > 0) {
                        foodResult += "Coca cola (15.000 đ) x" + drinkQuantity;
                    }
                    if (foodQuantity > 0) {
                        if (!foodResult.isEmpty()) {
                            foodResult += ", ";
                        }
                        foodResult += "Bỏng ngô Caramel (50.000 đ) x" + foodQuantity;
                    }
                }

                tvName = dialogView.findViewById(R.id.tv_name);
                tvPrice = dialogView.findViewById(R.id.tv_price);
                tvPremiere = dialogView.findViewById(R.id.tv_premiere);
                tvSelectedFood = dialogView.findViewById(R.id.tv_selected_food);
                tvQuantity = dialogView.findViewById(R.id.tv_quantity);
                tvSelectedChair = dialogView.findViewById(R.id.tv_selected_chair);
                tvTotalPrice = dialogView.findViewById(R.id.tv_total_price);

                tvName.setText(name);
                tvPremiere.setText(premiere);
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedPrice = currencyFormatter.format(price);
                tvPrice.setText(formattedPrice);
                tvSelectedFood.setText(foodResult);

                int selectedQuantity = selectedChairs.size();
                tvQuantity.setText(String.valueOf(selectedQuantity));

                // hiển thị những ghế đã đặt chỗ
                StringBuilder selectedChairsText = new StringBuilder();
                if (selectedChairs.isEmpty()) {
                    selectedChairsText.append("Bạn chưa chọn ghế ngồi !!!");
                } else {
                    for (Button button : selectedChairs) {
                        selectedChairsText.append(button.getText().toString()).append(", ");
                    }
                    selectedChairsText.setLength(selectedChairsText.length() - 2);
                }
                tvSelectedChair.setText(selectedChairsText.toString());

                // tính tổng giá tiền
                int totalPrice = 0;
                for (Button button : selectedChairs) {
                    int chairNumber = Integer.parseInt(button.getText().toString());
                    if (chairNumber == 9 || chairNumber == 10) {
                        totalPrice += (2 * price);
                    }else {
                        totalPrice += price;
                    }
                }
                if(drinkQuantity > 0){
                    totalPrice += (15000 * drinkQuantity);
                }
                if(foodQuantity > 0){
                    totalPrice += (50000 * foodQuantity);
                }
                NumberFormat totalFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String total = totalFormatter.format(totalPrice);
                tvTotalPrice.setText(total);

                btnAgree = dialogView.findViewById(R.id.btn_agree);
                btnAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedChairs.isEmpty()) {
                            Toast.makeText(BookingActivity.this, "Vui lòng chọn ghế ngồi !!!", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent2 = new Intent(BookingActivity.this, BookingHistoryActivity.class);
                            startActivity(intent2);
                        }
                    }
                });

                btnCancel = dialogView.findViewById(R.id.btn_cancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private View.OnClickListener chairClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            // toggle ghế đã chọn
            if (button.isSelected()) {
                button.setSelected(false);
                button.setBackgroundColor(getResources().getColor(R.color.green));
                selectedChairs.remove(button);
            } else {
                button.setSelected(true);
                button.setBackgroundColor(getResources().getColor(R.color.orange));
                selectedChairs.add(button);
            }
        }
    };
}