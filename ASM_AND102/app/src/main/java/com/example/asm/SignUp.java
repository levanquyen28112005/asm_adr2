package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm.DAO.UserDAO;
import com.example.asm.model.User;

import java.util.List;

public class SignUp extends AppCompatActivity {

    EditText etUsername;
    EditText etPass;
    EditText etConfirmPas;
    Button btnSignUp;
    TextView tvLogin;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.et_username_signup);
        etPass = findViewById(R.id.et_password_signup);
        etConfirmPas = findViewById(R.id.et_confirm_password_signup);
        btnSignUp = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.tv_login);

        userDAO = new UserDAO(this);
        
        List<User> list = userDAO.GetAllListUser();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                String confirmPass = etConfirmPas.getText().toString();
                
                if(username.trim().equals("") || pass.trim().equals("") || confirmPass.trim().equals("")){
                    Toast.makeText(SignUp.this, "Không được để trống hoặc nhập khoảng trắng", Toast.LENGTH_SHORT).show();
                    return;
                }

                
                for(int i = 0; i < list.size(); i++){
                    if (list.get(i).getUsername().equals(username)){
                        Toast.makeText(SignUp.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(!pass.equals(confirmPass)){
                    Toast.makeText(SignUp.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pass.equals(confirmPass)){
                    userDAO.AddUser(new User(0, username, pass, "kkkk"));
                    Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    tvLogin.callOnClick();
                    finishAffinity();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }
}