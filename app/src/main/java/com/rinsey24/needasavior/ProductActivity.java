package com.rinsey24.needasavior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rinsey24.needasavior.Adapter.ProductAdapter;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        uploadProducts();
        RecyclerView recyclerView = findViewById(R.id.RecycleViewProduct);
        ProductAdapter productAdapter = new ProductAdapter(products, this);
        recyclerView.setAdapter(productAdapter);
    }

    void uploadProducts() {
        products.add(new Product(1, "Корм Whiskas", "Для котят", 200.0, 20, "whiskas_kitten.jpg"));
        products.add(new Product(2, "Coca-Cola", "Газированный напиток", 1.50, 50, "image.jpg"));
        products.add(new Product(3, "Сыр голландский красная цена", "Хрень полная", 7.99, 10, "fakeholland.jpg"));
        products.add(new Product(4, "Сметана 20%", "Кисломолочный продукт 20% жирности", 2.20, 30, "smetana.jpg"));
    }
    public void buttonToCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}