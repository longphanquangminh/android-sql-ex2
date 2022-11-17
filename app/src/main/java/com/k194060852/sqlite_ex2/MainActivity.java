package com.k194060852.sqlite_ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

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

    private void createDb() {
        db = new DatabaseHelper(MainActivity.this);
        db.createSampleData();
    }
}