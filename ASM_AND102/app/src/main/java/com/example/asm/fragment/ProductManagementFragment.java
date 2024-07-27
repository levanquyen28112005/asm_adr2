package com.example.asm.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm.DAO.ProductDAO;
import com.example.asm.R;
import com.example.asm.adapter.ProductAdapter;
import com.example.asm.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductManagementFragment extends Fragment {
    ProductDAO productDAO;
    RecyclerView recyclerView;
    List<Product> list;
    ProductAdapter adapter;
    FloatingActionButton btnAdd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView = view.findViewById(R.id.rv_main);
        btnAdd = view.findViewById(R.id.btn_add);

        recyclerView.setHasFixedSize(true);

        productDAO = new ProductDAO(getContext());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        GetData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_product);

                EditText etName = dialog.findViewById(R.id.et_name_add);
                EditText etCost = dialog.findViewById(R.id.et_cost_add);
                EditText etQuantity = dialog.findViewById(R.id.et_quantity_add);
                Button btnAddAdd = dialog.findViewById(R.id.btn_add_add);

                btnAddAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etName.getText().toString();
                        String cost = etCost.getText().toString();
                        String quantity = etQuantity.getText().toString();

                        if(!validate(name, cost, quantity)){
                            return;
                        }

                        productDAO.AddProduct(new Product(0, name, Integer.parseInt(cost), Integer.parseInt(quantity)));
                        adapter.notifyDataSetChanged();
                        GetData();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        return view;
    }

    public boolean validate(String name, String cost, String quantity){

        if(name.trim().equals("") || cost.trim().equals("") || quantity.trim().equals("")){
            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!cost.matches("[0-9]+")){
            Toast.makeText(getContext(), "Giá là số nguyên dương", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!quantity.matches("[0-9]+")){
            Toast.makeText(getContext(), "Số lượng không chính xác", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }

    public void GetData(){
        adapter = new ProductAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }
}