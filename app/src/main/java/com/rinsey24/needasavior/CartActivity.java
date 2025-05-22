package com.rinsey24.needasavior;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rinsey24.needasavior.Adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemActionListener {
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.RecycleViewCart);
        setupCartRecyclerView();
    }

    private void setupCartRecyclerView() {
        uploadCart();
        cartAdapter = new CartAdapter(cartItemList, this, this);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void uploadCart() {
        Product whiskasProduct = new Product(1, "Корм Whiskas", "Для котят", 200.0, 20, "whiskas_kitten.jpg");
        cartItemList.add(new CartItem(whiskasProduct, 2));

        Product cocaColaProduct = new Product(2, "Coca-Cola", "Газированный напиток", 1.50, 50, "image.jpg");
        cartItemList.add(new CartItem(cocaColaProduct, 1));
    }

    // Реализация методов интерфейса OnCartItemActionListener
    @Override
    public void onQuantityChange(CartItem item, int newQuantity) {
        item.setQuantity(newQuantity);
        cartAdapter.updateDisplayedListAndNotify();
    }

    @Override
    public void onItemRemoved(CartItem item) {
        item.setEffectivelyVisible(false);
        cartAdapter.updateDisplayedListAndNotify();
    }
}