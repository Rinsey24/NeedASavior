package com.rinsey24.needasavior;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_item);

        ImageView productImage = findViewById(R.id.product1_image);
        TextView productTitle = findViewById(R.id.product_title);
        TextView productPrice = findViewById(R.id.product1_price);
        TextView productCount = findViewById(R.id.product1_count);
        TextView productDescription = findViewById(R.id.product1_description);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);
        TextView characteristicsTitle = findViewById(R.id.product1_characteristics_title);
        TextView characteristics = findViewById(R.id.product1_characteristics);
        TextView detailsButton = findViewById(R.id.product1_details_button);

        Product product = new Product(
                1,
                "Brit 8кг Premium Cat Sterilized Salmon and Chicken",
                "Сухой корм brit premium...",
                4784.0,
                10,
                "Не указан"
        );

        productTitle.setText(product.getTitle());
        productPrice.setText("Цена: " + product.getPrice() + " руб.");
        productCount.setText("В наличии: " + product.getCount() + " шт.");
        productDescription.setText(product.getDescription());

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characteristicsTitle.getVisibility() == View.VISIBLE) {

                    characteristicsTitle.setVisibility(View.GONE);
                    characteristics.setVisibility(View.GONE);
                    detailsButton.setText("Подробнее");
                } else {

                    characteristicsTitle.setVisibility(View.VISIBLE);
                    characteristics.setVisibility(View.VISIBLE);
                    detailsButton.setText("Скрыть");
                }
            }
        });


        String characteristicsText = "Код товара: 1000355209\n" +
                "Артикул: 5049868\n" +
                "Класс корма: премиум\n" +
                "Вкус корма: курица, лосось\n" +
                "Возрастная группа: для взрослых\n" +
                "Назначение: для стерилизованных и кастрированных, полнорационный\n" +
                "Форма выпуска: гранулы\n" +
                "Упаковка: пакет\n" +
                "Размер упаковки: средняя\n" +
                "Вес, г: 8000\n" +
                "Длина упаковки, см: 35\n" +
                "Ширина упаковки, см: 12\n" +
                "Высота упаковки, см: 64\n" +
                "Страна производства: Россия";
        characteristics.setText(characteristicsText);
    }
}