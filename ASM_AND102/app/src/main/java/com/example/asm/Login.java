package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.DAO.UserDAO;
import com.example.asm.model.User;

import java.util.List;

public class Login extends AppCompatActivity {

    UserDAO userDAO;
    List<User> list;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    TextView tvForgotPassword;
    TextView tvSignUpLogin;
    CheckBox cbRemember;
    public static String USER_FILE = "USER_FILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username_login);
        etPassword = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_login);
        tvForgotPassword = findViewById(R.id.tv_forgot_pass);
        tvSignUpLogin = findViewById(R.id.tv_signup);
        cbRemember = findViewById(R.id.cb_remember_pass);

        userDAO = new UserDAO(this);
        list = userDAO.GetAllListUser();

        checkRemember();

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, FogotPass.class);
                startActivity(intent);
            }
        });

        tvSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                boolean check = false;

                for (int i = 0; i < list.size(); i++) {
                    if (username.equals(list.get(i).getUsername())) {
                        if (!password.equals(list.get(i).getPassword())) {
                            Toast.makeText(Login.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        check = true;
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        remember(username, password, cbRemember.isChecked(), true);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                if(!check){
                    Toast.makeText(Login.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void remember(String user, String pass, boolean chkRemember, boolean isLogin) {
        SharedPreferences sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isLogin", isLogin);
        editor.putString("user", user);
        editor.putString("pass", pass);
        editor.putBoolean("chkRemember", chkRemember);

        editor.apply();
    }

    public void checkRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "");
        String pass = sharedPreferences.getString("pass", "");
        boolean chkRemember = sharedPreferences.getBoolean("chkRemember", false);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);

        this.cbRemember.setChecked(chkRemember);
        if (this.cbRemember.isChecked()) {
            etUsername.setText(user);
            etPassword.setText(pass);
        }
        if(isLogin){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }


//    public void rememberUser(String u, String p ) {
//        SharedPreferences pref = getSharedPreferences(USER_FILE, MODE_PRIVATE);
//        SharedPreferences.Editor edit = pref.edit();
//        edit.putString("USERNAME", u);
//        edit.putString("PASSWORD", p);
//        edit.commit();
//    }
}