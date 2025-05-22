package com.rinsey24.needasavior;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class CardProductActivity extends AppCompatActivity {

    TextView productNameTextView, productPriceTextView, productDescriptionTextView;
    ImageView productImageView;
    Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayoutProductDetails), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        productImageView = findViewById(R.id.productImageView);
        addToCartButton = findViewById(R.id.addToCartButton);
        loadInfo();
    }
    private void loadInfo(){
        Bundle arguments = getIntent().getExtras();
        Product product = (Product)  arguments.getSerializable(Product.class.getSimpleName());
        productNameTextView.setText("Наименование" + product.getTitle());
        productPriceTextView.setText("Цена: " + Double.toString(product.getPrice()) + " Р");
        productDescriptionTextView.setText("Описание: " + product.getDescription());

    }

}