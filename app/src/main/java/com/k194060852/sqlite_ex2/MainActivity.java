package com.k194060852.sqlite_ex2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.k194060852.adapters.ProductAdapter;
import com.k194060852.models.Product;
import com.k194060852.sqlite_ex2.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseHelper db;

    ProductAdapter adapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDb();
        loadData();
    }

    private void createDb() {
        db = new DatabaseHelper(MainActivity.this);
        db.createSampleData();
    }

    private void loadData() {
        products = new ArrayList<>();
        // Init data
        Cursor c = db.getData("SELECT * FROM " + DatabaseHelper.TBL_NAME);
        while (c.moveToNext()){
            products.add(new Product(c.getInt(0), c.getString(1), c.getDouble(2)));
        }
        c.close();

        adapter = new ProductAdapter(MainActivity.this, R.layout.item_list, products);
        binding.lvProduct.setAdapter(adapter);
    }

    //===== MENU =====

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mn_Add){
            // Open dialog - inserting data
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_add_product);

            EditText edtName, edtPrice;
            edtName = dialog.findViewById(R.id.edt_name);
            edtPrice = dialog.findViewById(R.id.edt_price);

            Button btnOk = dialog.findViewById(R.id.btn_Ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.execSql("INSERT INTO " + DatabaseHelper.TBL_NAME + " VALUES(null, '" +
                            edtName.getText().toString() + "', " +
                            Double.parseDouble(edtPrice.getText().toString()) + ")");
                    dialog.dismiss();
                    loadData();
                }
            });

            Button btnCancel = dialog.findViewById(R.id.btn_Cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void openEditDialog(Product p) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_edit_product);

        EditText edtName, edtPrice;
        Button btnOk, btnCancel;

        edtName = dialog.findViewById(R.id.edt_name);
        edtName.setText(p.getProductName());

        edtPrice = dialog.findViewById(R.id.edt_price);
        edtPrice.setText(String.valueOf(p.getProductPrice()));

        btnOk = dialog.findViewById(R.id.btn_Ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSql("UPDATE " + DatabaseHelper.TBL_NAME + " SET " +
                        DatabaseHelper.COL_NAME + "='" + edtName.getText().toString() + "', " +
                        DatabaseHelper.COL_PRICE + "=" + Double.parseDouble(edtPrice.getText().toString()) +
                        " WHERE " + DatabaseHelper.COL_ID + "=" + p.getProductId());
                // UPDATE Product SET ProductName='Tiger', ProductPrice=18000 WHERE
                // ProductId=2
                loadData();
                dialog.dismiss();
            }
        });

        btnCancel = dialog.findViewById(R.id.btn_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}