package com.example.asm.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm.DAO.UserDAO;
import com.example.asm.Login;
import com.example.asm.R;
import com.example.asm.model.User;

import java.util.List;

public class ChangePasswordFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etOldPass = view.findViewById(R.id.et_old_password);
        EditText etNewPass = view.findViewById(R.id.et_new_password);
        EditText etConfirmNewPass = view.findViewById(R.id.et_confirm_new_password);
        Button btnChangePass = view.findViewById(R.id.btn_change_password);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDAO userDAO = new UserDAO(view.getContext());

                List<User> list = userDAO.GetAllListUser();

                String oldPass = etOldPass.getText().toString();
                String newPass = etNewPass.getText().toString();
                String confirmNewPass = etConfirmNewPass.getText().toString();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
                String username = sharedPreferences.getString("USERNAME", "");

                if (oldPass.trim().equals("") || newPass.trim().equals("") || confirmNewPass.trim().equals("")) {
                    Toast.makeText(view.getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).getUsername().equals(username)) {

                        if (!oldPass.equals(list.get(i).getPassword())) {
                            Toast.makeText(view.getContext(), "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (newPass.equals(oldPass)) {
                            Toast.makeText(view.getContext(), "Mật khẩu mới không được trùng với mật khẩu cũ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!newPass.equals(confirmNewPass)) {
                            Toast.makeText(view.getContext(), "Mật khẩu mới không trùng khớp, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        userDAO.UpdateUser(new User(list.get(i).getId(), username, newPass, "kkk"), list.get(i).getId());
                        Toast.makeText(view.getContext(), "Cập nhật mật khẩu thành công, đăng nhập lại để tiếp tục", Toast.LENGTH_SHORT).show();

                        logout(false);

                        Intent intent = new Intent(ChangePasswordFragment.this.getContext(), Login.class);
                        startActivity(intent);
                        getActivity().finishAffinity();
                    }
                }
            }
        });

    }

    public void logout(boolean isLogin) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isLogin", isLogin);

        editor.apply();
    }
}