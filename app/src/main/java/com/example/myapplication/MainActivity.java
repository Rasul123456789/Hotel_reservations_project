package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteCities, autoCompleteRooms;
    Button btnSearch, btnFoodOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteCities = findViewById(R.id.autoCompleteCities);
        autoCompleteRooms = findViewById(R.id.autoCompleteRooms);
        btnSearch = findViewById(R.id.btnSearch);
        btnFoodOrder = findViewById(R.id.btnFoodOrder);

        // Қалалар тізімі (қалдырдық бұрынғысын)
        String[] cities = {"Семей", "Көкшетау", "Ақтөбе", "Алматы", "Атырау", "Орал",
                "Тараз", "Талдықорған", "Қарағанды", "Қостанай", "Қызылорда",
                "Ақтау", "Павлодар", "Петропавл", "Түркістан",
                "Жезқазған", "Өскемен", "Астана қаласы",
                "Алматы қаласы", "Шымкент қаласы"};
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cities);
        autoCompleteCities.setAdapter(cityAdapter);
        autoCompleteCities.setOnClickListener(v -> autoCompleteCities.showDropDown());

        // Бөлме түрлері
        String[] rooms = {"Эконом", "Стандарт", "Комфорт", "Бизнес", "Люкс", "Президенттік люкс"};
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, rooms);
        autoCompleteRooms.setAdapter(roomAdapter);
        autoCompleteRooms.setOnClickListener(v -> autoCompleteRooms.showDropDown());

        // Іздеу — бөлмеге байланысты Activity ашу
        btnSearch.setOnClickListener(v -> {
            String selectedRoom = autoCompleteRooms.getText().toString().trim();

            if (selectedRoom.isEmpty()) {
                Toast.makeText(MainActivity.this, "Бөлме түрін таңдаңыз!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent;
            switch (selectedRoom) {
                case "Эконом":
                    intent = new Intent(MainActivity.this, EconomActivity.class);
                    break;
                case "Стандарт":
                    intent = new Intent(MainActivity.this, StandardActivity.class);
                    break;
                case "Комфорт":
                    intent = new Intent(MainActivity.this, ComfortActivity.class);
                    break;
                case "Бизнес":
                    intent = new Intent(MainActivity.this, BusinessActivity.class);
                    break;
                case "Люкс":
                    intent = new Intent(MainActivity.this, LuxeActivity.class);
                    break;
                case "Президенттік люкс":
                    intent = new Intent(MainActivity.this, Prezident_LuxeActivity.class);
                    break;
                default:
                    Toast.makeText(MainActivity.this, "Бөлме түрі табылмады!", Toast.LENGTH_SHORT).show();
                    return;
            }
            startActivity(intent);
        });

        // Тамақ батырмасы бұрынғыдай
        btnFoodOrder.setOnClickListener(v -> {
            String selectedRoom = autoCompleteRooms.getText().toString().trim();
            Intent intent = new Intent(MainActivity.this, FoodMenuActivity.class);
            intent.putExtra("ROOM_TYPE", selectedRoom);
            startActivity(intent);
        });
    }
}
