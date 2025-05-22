package com.rinsey24.needasavior;

import android.content.Intent; // Добавлен импорт Intent
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> countries = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Spinner countriesList;
    AutoCompleteTextView autoCompleteCity, autoCompleteCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        countries.addAll(Arrays.asList("Россия", "Грузия", "США", "Япония", "Франция", "Москва", "Санкт-Петербург", "Токио", "Париж", "Нью-Йорк"));

        countriesList = findViewById(R.id.spinnerCountry);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countriesList.setAdapter(arrayAdapter);
        countriesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getSelectedItem();
                if (view != null) {
                    Snackbar.make(view, selectedItem, 2500).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        autoCompleteCity = findViewById(R.id.autocompleteCity);
        List<String> cityList = countries.stream().filter(country -> country.equals("Москва") || country.equals("Санкт-Петербург") || country.equals("Токио") || country.equals("Париж") || country.equals("Нью-Йорк")).collect(Collectors.toList());
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cityList);
        autoCompleteCity.setAdapter(cityAdapter);

        autoCompleteCountry = findViewById(R.id.autocompleteCountry);
        List<String> countryList = countries.stream().filter(country -> !country.equals("Москва") && !country.equals("Санкт-Петербург") && !country.equals("Токио") && !country.equals("Париж") && !country.equals("Нью-Йорк")).collect(Collectors.toList());
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countryList);
        autoCompleteCountry.setAdapter(countryAdapter);
    }

    public void add(View view) {
        String newItem = autoCompleteCountry.getText().toString();
        if (!newItem.isEmpty()) {
            if (!countries.contains(newItem)) {
                countries.add(newItem);
                arrayAdapter.notifyDataSetChanged();
                autoCompleteCity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countries));
                autoCompleteCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countries));
            } else {
                Snackbar.make(view, "Такой элемент уже существует", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Введите страну", Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(View view) {
        if (!countries.isEmpty()) {
            int position = countriesList.getSelectedItemPosition();
            if (position != Spinner.INVALID_POSITION) {
                countries.remove(position);
                arrayAdapter.notifyDataSetChanged();
                autoCompleteCity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countries));
                autoCompleteCountry.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countries));
            } else {
                Toast.makeText(this, "Выберите элемент для удаления", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Список пуст", Toast.LENGTH_SHORT).show();
        }
    }

    // Добавленный метод для перехода на ProductActivity
    public void goToProductActivity(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    // Добавленный метод для перехода на CartActivity
    public void goToCartActivity(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}