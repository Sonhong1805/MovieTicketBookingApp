package com.example.mybtl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    EditText edtFullname, edtEmail, edtPassword, edtPhoneNumber,edtConfirmPassword;

    Button btnRegister, btnReset,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtFullname = findViewById(R.id.edtFullname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnReset = findViewById(R.id.btnReset);
        btnLogin = findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = edtFullname.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();;
                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                if(fullname.equals("") || email.equals("") || password.equals("") || phoneNumber.equals("") || confirmPassword.equals("")){
                    Toast.makeText(RegisterActivity.this, "Các trường không được bỏ trống !!!", Toast.LENGTH_SHORT).show();
                }else {
                    if (!confirmPassword.equals(password)) {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu chưa trùng khớp !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(RegisterActivity.this, "Email không hợp lệ !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(UserDAO.checkEmail(RegisterActivity.this,email)){
                        Toast.makeText(RegisterActivity.this, "Tên đăng nhập đã tồn tại !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(UserDAO.register(RegisterActivity.this, fullname, email,password,phoneNumber)){
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công !!!", Toast.LENGTH_LONG).show();
                        resetField();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Đăng nhập không thành công !!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetField();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
    public void resetField(){
        edtFullname.setText("");
        edtEmail.setText("");
        edtPassword.setText("");
        edtPhoneNumber.setText("");
        edtConfirmPassword.setText("");
    }
}