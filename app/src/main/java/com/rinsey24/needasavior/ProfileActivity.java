package com.rinsey24.needasavior;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void loadInfo() {
        TextView textViewLogin = findViewById(R.id.textViewLogin);
        TextView textViewPassword = findViewById(R.id.textViewPass);
        TextView textViewFirstName = findViewById(R.id.textViewFirstName);
        TextView textViewLastName = findViewById(R.id.textViewLastName);
        TextView textViewAge = findViewById(R.id.textViewAge);
        TextView textViewPhone = findViewById(R.id.textViewPhone);

        // Получение объекта User из Bundle
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            User user = (User) arguments.getSerializable(User.class.getSimpleName());
            if (user != null) {
                textViewLogin.setText(user.getLogin());
                textViewPassword.setText(user.getPassword());
                textViewFirstName.setText(user.getFirstName());
                textViewLastName.setText(user.getLastName());
                textViewAge.setText(String.valueOf(user.getAge()));
                textViewPhone.setText(user.getPhoneNumber());
            }
        }
    }
}