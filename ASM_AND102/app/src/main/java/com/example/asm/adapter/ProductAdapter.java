package com.example.asm.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.DAO.ProductDAO;
import com.example.asm.R;
import com.example.asm.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<Product> list;
    Context context;
    ProductDAO productDAO;

    public ProductAdapter(Context context) {
        this.context = context;
        productDAO = new ProductDAO(context);
        this.list = productDAO.GetAllListProduct();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = list.get(position);

        holder.tvName.setText(product.getName());
        holder.tvCost.setText("Giá: " + product.getCost() + " VNĐ");
        holder.tvQuantity.setText("Số lượng: " + product.getQuantity());

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Thông báo");
                alert.setMessage("Bạn có muốn xóa " + list.get(holder.getAdapterPosition()).getName() + "?");

                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productDAO.DeleteProduct(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        list.clear();
                        list = productDAO.GetAllListProduct();
                    }
                });
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

            }
        });


        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_product);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                EditText etName = dialog.findViewById(R.id.et_name_add);
                EditText etCost = dialog.findViewById(R.id.et_cost_add);
                EditText etQuantity = dialog.findViewById(R.id.et_quantity_add);
                Button btnUpdate = dialog.findViewById(R.id.btn_add_add);
                TextView kkkk = dialog.findViewById(R.id.kkkk);

                kkkk.setText("Chỉnh sửa thông tin sản phẩm");

                Product product1 = list.get(holder.getAdapterPosition());

                etName.setText(product1.getName());
                etCost.setText(product1.getCost() + "");
                etQuantity.setText(product1.getQuantity() + "");
                btnUpdate.setText("Cập nhật");

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etName.getText().toString();
                        String cost = etCost.getText().toString();
                        String quantity = etQuantity.getText().toString();

                        if(!validate(name, cost, quantity)){
                            return;
                        }

                        productDAO.UpdateProduct(new Product(0, name, Integer.parseInt(cost), Integer.parseInt(quantity)), list.get(holder.getAdapterPosition()).getId());

                        notifyDataSetChanged();
                        list.clear();
                        list = productDAO.GetAllListProduct();

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    public boolean validate(String name, String cost, String quantity){

        if(name.trim().equals("") || cost.trim().equals("") || quantity.trim().equals("")){
            Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!cost.matches("[0-9]+")){
            Toast.makeText(context, "Giá là số nguyên dương", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!quantity.matches("[0-9]+")){
            Toast.makeText(context, "Số lượng không chính xác", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvCost;
        TextView tvQuantity;
        TextView tvEdit;
        TextView tvDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCost = itemView.findViewById(R.id.tv_cost);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvDelete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
