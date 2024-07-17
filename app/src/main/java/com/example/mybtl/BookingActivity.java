package com.example.mybtl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    Button btnConfirm;
    TextView tvName, tvPrice, tvPremiere,tvDateOrder,tvTimeOrder;
    EditText edtDrink,edtFood;
    String selectedFood = "",movieName = "",selectedMethod = "",dateOrder = "", timeOrder = "";
    Button chair1, chair2, chair3,chair4,chair5,chair6,chair7,chair8,chair9,chair10;
    List<Button> selectedChairs = new ArrayList<>();
    Spinner spnMethods;
    int idMovie,totalPrice = 0;

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
        tvDateOrder = findViewById(R.id.tv_date_order);
        tvTimeOrder = findViewById(R.id.tv_time_order);

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

        // hiển thị từng phương thức thanh toán
        spnMethods = findViewById(R.id.spn_method);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BookingActivity.this,R.array.payment_method_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_method_item);
        spnMethods.setAdapter(adapter);

        //chọn phương thức thanh toán
        spnMethods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMethod = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Intent intent = getIntent();
        idMovie = intent.getIntExtra("idMovie", -1);
        movieName = intent.getStringExtra("name");
        int price = intent.getIntExtra("price", -1);
        String premiere = intent.getStringExtra("premiere");
        String email = intent.getStringExtra("email");
        dateOrder = intent.getStringExtra("dateOrder");
        timeOrder = intent.getStringExtra("timeOrder");

        tvName.setText(movieName);
        tvPremiere.setText(premiere);
        // định dạng tiền Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormatter.format(price);
        tvPrice.setText(formattedPrice);
        tvDateOrder.setText(dateOrder);
        tvTimeOrder.setText(timeOrder);

        // ghế được chọn sẽ có màu đỏ
        List<String> listChairs = BillDAO.getSelectedChairs(this, idMovie,dateOrder,timeOrder);
        int[] chairIds = {
                R.id.chair1, R.id.chair2, R.id.chair3, R.id.chair4, R.id.chair5,
                R.id.chair6, R.id.chair7, R.id.chair8, R.id.chair9, R.id.chair10
        };

        for (String chairs : listChairs) {
            String[] chairArray = chairs.split(", ");
            for (String chair : chairArray) {
                int chairNumber = Integer.parseInt(chair.trim());
                if (chairNumber >= 1 && chairNumber <= chairIds.length) {
                    findViewById(chairIds[chairNumber - 1]).setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        }

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

                TextView tvName,tvPrice,tvPremiere,tvSelectedFood,tvQuantity, tvSelectedChair,tvTotalPrice,tvMethodPayment,tvDateOrder,tvTimeOrder;
                Button btnCancel,btnAgree;

                selectedFood = "";
                // chọn số lượng đồ ăn/uống
                int drinkQuantity = Integer.parseInt(edtDrink.getText().toString());
                int foodQuantity = Integer.parseInt(edtFood.getText().toString());

                if (drinkQuantity == 0 && foodQuantity == 0) {
                    selectedFood = "0";
                } else {
                    if (drinkQuantity > 0) {
                        selectedFood += "Coca cola (15.000 đ) x" + drinkQuantity;
                    }
                    if (foodQuantity > 0) {
                        if (!selectedFood.isEmpty()) {
                            selectedFood += ", ";
                        }
                        selectedFood += "Bỏng ngô Caramel (50.000 đ) x" + foodQuantity;
                    }
                }

                tvName = dialogView.findViewById(R.id.tv_name);
                tvPrice = dialogView.findViewById(R.id.tv_price);
                tvPremiere = dialogView.findViewById(R.id.tv_premiere);
                tvSelectedFood = dialogView.findViewById(R.id.tv_selected_food);
                tvQuantity = dialogView.findViewById(R.id.tv_quantity);
                tvSelectedChair = dialogView.findViewById(R.id.tv_selected_chair);
                tvTotalPrice = dialogView.findViewById(R.id.tv_total_price);
                tvMethodPayment = dialogView.findViewById(R.id.tv_method_payment);
                tvDateOrder = dialogView.findViewById(R.id.tv_date_order);
                tvTimeOrder = dialogView.findViewById(R.id.tv_time_order);

                tvName.setText(movieName);
                tvPremiere.setText(premiere);
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedPrice = currencyFormatter.format(price);
                tvPrice.setText(formattedPrice);
                tvSelectedFood.setText(selectedFood);
                tvMethodPayment.setText(selectedMethod);
                tvDateOrder.setText(dateOrder);
                tvTimeOrder.setText(timeOrder);

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
                String selectedChair = selectedChairsText.toString();
                tvSelectedChair.setText(selectedChair);

                totalPrice = 0;
                // tính tổng giá tiền
                // ghế 9 và 10 là ghế đôi nên gấp đôi giá tiền
                for (Button button : selectedChairs) {
                    int chairNumber = Integer.parseInt(button.getText().toString());
                    if (chairNumber == 9 || chairNumber == 10) {
                        totalPrice += (2 * price);
                    } else {
                        totalPrice += price;
                    }
                }

                if (drinkQuantity > 0) {
                    totalPrice += (15000 * drinkQuantity);
                }

                if (foodQuantity > 0) {
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
                            if(BillDAO.insert(BookingActivity.this,idMovie,movieName,premiere,price,dateOrder,timeOrder,selectedChair,selectedFood,selectedMethod,email,totalPrice)){
                                Toast.makeText(BookingActivity.this, "Thêm Hoá đơn thành công !", Toast.LENGTH_SHORT).show();
                                sendNotification();
                                Intent intent2 = new Intent(BookingActivity.this, BookingHistoryActivity.class);
                                intent2.putExtra("email", email);
                                startActivity(intent2);
                            }else {
                                Toast.makeText(BookingActivity.this, "Thêm Hoá đơn thất bại !", Toast.LENGTH_SHORT).show();
                            }
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

    private final View.OnClickListener chairClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;

            int chairNumber = Integer.parseInt(button.getText().toString());

            // nếu ghế đc chọn thì ko đc click và hiện thông báo
            boolean isChairSelected = BillDAO.isChairSelected(BookingActivity.this,idMovie,dateOrder,timeOrder, chairNumber);

            if (isChairSelected) {
                Toast.makeText(getApplicationContext(), "Ghế " + chairNumber + " đã được chọn!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Toggle ghế đã chọn
            if (selectedChairs.contains(button)) {
                selectedChairs.remove(button);
                button.setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                selectedChairs.add(button);
                button.setBackgroundColor(getResources().getColor(R.color.orange));
            }
        }
    };

    private void sendNotification() {
        // tạo chức năng thông báo khi đến giờ
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date selectedDate;
        try {
            selectedDate = dateFormat.parse(dateOrder);
            String[] timeParts = timeOrder.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            Intent intent = new Intent(this, NotificationReceiver.class);
            intent.putExtra("movieName", movieName);
            intent.putExtra("dateOrder", dateOrder);
            intent.putExtra("timeOrder", timeOrder);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) calendar.getTimeInMillis(), intent, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}