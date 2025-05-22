package com.rinsey24.needasavior.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rinsey24.needasavior.CardProductActivity;
import com.rinsey24.needasavior.Product;
import com.rinsey24.needasavior.R;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final List<Product> productList;
    private final LayoutInflater inflater;
    private final Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
        this.context = context; // Инициализация context
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.imageProduct.setImageResource(R.drawable.gradient_button);

        holder.titleProduct.setText(product.getTitle());

        if (product.getPrice() == -1.0) {
            holder.priceProduct.setText("Цена: недоступна");
        } else {
            String priceText = String.format(new Locale("ru", "RU"), "Цена: %.2f руб.", product.getPrice());
            holder.priceProduct.setText(priceText);
        }
        if (product.getCount() == -1) {
            holder.countProduct.setText("В наличии: нет данных");
        } else {
            String countText = String.format(new Locale("ru", "RU"), "В наличии: %d шт.", product.getCount());
            holder.countProduct.setText(countText);
        }
        holder.descriptionProduct.setText(product.getDescription());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CardProductActivity.class);
            intent.putExtra(Product.class.getSimpleName(), product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageProduct;
        public final TextView titleProduct, priceProduct, countProduct, descriptionProduct;

        ViewHolder(View view) {
            super(view);
            imageProduct = view.findViewById(R.id.product1_image);
            titleProduct = view.findViewById(R.id.product_title);
            priceProduct = view.findViewById(R.id.product1_price);
            countProduct = view.findViewById(R.id.product1_count);
            descriptionProduct = view.findViewById(R.id.product1_description);
        }
    }
}