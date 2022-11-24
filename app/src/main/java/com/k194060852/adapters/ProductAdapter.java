package com.k194060852.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.k194060852.models.Product;
import com.k194060852.sqlite_ex2.MainActivity;
import com.k194060852.sqlite_ex2.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    MainActivity activity;
    int item_layout;
    List<Product> products;

    public ProductAdapter(MainActivity activity, int item_layout, List<Product> products) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);

            holder.txtProductInf = view.findViewById(R.id.txt_ProductInf);
            holder.imvEdit = view.findViewById(R.id.imv_Edit);
            holder.imvDelete = view.findViewById(R.id.imv_Delete);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Product p = products.get(i);
        holder.txtProductInf.setText(p.getProductName() + " - " + p.getProductPrice());

        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Updating data
                activity.openEditDialog(p);
            }
        });

        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Deleting data
                activity.deleteItem(p);
            }
        });

        return view;
    }

    public static class ViewHolder {
        TextView txtProductInf;
        ImageView imvEdit, imvDelete;
    }
}
