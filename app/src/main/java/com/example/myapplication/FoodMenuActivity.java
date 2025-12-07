package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.provider.MediaStore;

import java.io.IOException;

public class FoodMenuActivity extends AppCompatActivity {

    TextView tvTotalPrice;
    CheckBox cbDelivery;
    String roomType;

    EditText etRoomNumber, etFloor;
    Button btnConfirmOrder, btnPayKaspi, btnGoBack, btnUploadReceipt;
    ImageView ivReceipt;

    // Барлық тағам CheckBox-тар
    CheckBox cbOmelet, cbBuckwheat, cbSandwich, cbFriedEgg, cbOatmeal, cbPancake;
    CheckBox cbBorscht, cbChickenSoup, cbSolyanka, cbRassolnik, cbMushroomSoup, cbPumpkinSoup;
    CheckBox cbFriedChicken, cbPilaf, cbSteamedFish, cbBeefStroganoff, cbStewedBeef, cbLasagna;
    CheckBox cbCaesar, cbGreekSalad, cbVinaigrette, cbOlivie, cbTunaSalad, cbCarrotApple;
    CheckBox cbMedovik, cbCarrotCake, cbKartoshka, cbCheesecake, cbChocolateMousse, cbFruitSalad;
    CheckBox cbMayonnaise, cbTomatoSauce, cbGarlicSauce, cbSourCreamSauce, cbMustardSauce;
    CheckBox cbTea, cbLatte, cbOrangeJuice, cbCompote, cbWater, cbLemonade;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri receiptUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        cbDelivery = findViewById(R.id.cbDelivery);

        etRoomNumber = findViewById(R.id.etRoomNumber);
        etFloor = findViewById(R.id.etFloor);

        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        btnPayKaspi = findViewById(R.id.btnPayKaspi);
        btnGoBack = findViewById(R.id.btnGoBack);
        btnUploadReceipt = findViewById(R.id.btnUploadReceipt);
        ivReceipt = findViewById(R.id.ivReceipt);

        // Intent-тен бөлме түрін алу
        roomType = getIntent().getStringExtra("ROOM_TYPE");
        Button btnPayHalyk = findViewById(R.id.btnPayHalyk);

        btnPayHalyk.setOnClickListener(v -> {
            int totalAmount = getTotalAmount();
            if (totalAmount == 0) {
                Toast.makeText(this, "Алдымен тағам таңдаңыз!", Toast.LENGTH_SHORT).show();
                return;
            }

            String halykUrl = "halyk://pay?amount=" + totalAmount;
            Intent halykIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(halykUrl));

            if (halykIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(halykIntent);
            } else {
                String webUrl = "https://halykbank.kz/kz/payment?amount=" + totalAmount;
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                startActivity(webIntent);
            }
        });

        // Барлық CheckBox байланыстыру
        cbOmelet = findViewById(R.id.cbOmelet);
        cbBuckwheat = findViewById(R.id.cbBuckwheat);
        cbSandwich = findViewById(R.id.cbSandwich);
        cbFriedEgg = findViewById(R.id.cbFriedEgg);
        cbOatmeal = findViewById(R.id.cbOatmeal);
        cbPancake = findViewById(R.id.cbPancake);
        cbBorscht = findViewById(R.id.cbBorscht);
        cbChickenSoup = findViewById(R.id.cbChickenSoup);
        cbSolyanka = findViewById(R.id.cbSolyanka);
        cbRassolnik = findViewById(R.id.cbRassolnik);
        cbMushroomSoup = findViewById(R.id.cbMushroomSoup);
        cbPumpkinSoup = findViewById(R.id.cbPumpkinSoup);
        cbFriedChicken = findViewById(R.id.cbFriedChicken);
        cbPilaf = findViewById(R.id.cbPilaf);
        cbSteamedFish = findViewById(R.id.cbSteamedFish);
        cbBeefStroganoff = findViewById(R.id.cbBeefStroganoff);
        cbStewedBeef = findViewById(R.id.cbStewedBeef);
        cbLasagna = findViewById(R.id.cbLasagna);
        cbCaesar = findViewById(R.id.cbCaesar);
        cbGreekSalad = findViewById(R.id.cbGreekSalad);
        cbVinaigrette = findViewById(R.id.cbVinaigrette);
        cbOlivie = findViewById(R.id.cbOlivie);
        cbTunaSalad = findViewById(R.id.cbTunaSalad);
        cbCarrotApple = findViewById(R.id.cbCarrotApple);
        cbMedovik = findViewById(R.id.cbMedovik);
        cbCarrotCake = findViewById(R.id.cbCarrotCake);
        cbKartoshka = findViewById(R.id.cbKartoshka);
        cbCheesecake = findViewById(R.id.cbCheesecake);
        cbChocolateMousse = findViewById(R.id.cbChocolateMousse);
        cbFruitSalad = findViewById(R.id.cbFruitSalad);
        cbMayonnaise = findViewById(R.id.cbMayonnaise);
        cbTomatoSauce = findViewById(R.id.cbTomatoSauce);
        cbGarlicSauce = findViewById(R.id.cbGarlicSauce);
        cbSourCreamSauce = findViewById(R.id.cbSourCreamSauce);
        cbMustardSauce = findViewById(R.id.cbMustardSauce);
        cbTea = findViewById(R.id.cbTea);
        cbLatte = findViewById(R.id.cbLatte);
        cbOrangeJuice = findViewById(R.id.cbOrangeJuice);
        cbCompote = findViewById(R.id.cbCompote);
        cbWater = findViewById(R.id.cbWater);
        cbLemonade = findViewById(R.id.cbLemonade);

        CheckBox[] foods = {
                cbOmelet, cbBuckwheat, cbSandwich, cbFriedEgg, cbOatmeal, cbPancake,
                cbBorscht, cbChickenSoup, cbSolyanka, cbRassolnik, cbMushroomSoup, cbPumpkinSoup,
                cbFriedChicken, cbPilaf, cbSteamedFish, cbBeefStroganoff, cbStewedBeef, cbLasagna,
                cbCaesar, cbGreekSalad, cbVinaigrette, cbOlivie, cbTunaSalad, cbCarrotApple,
                cbMedovik, cbCarrotCake, cbKartoshka, cbCheesecake, cbChocolateMousse, cbFruitSalad,
                cbMayonnaise, cbTomatoSauce, cbGarlicSauce, cbSourCreamSauce, cbMustardSauce,
                cbTea, cbLatte, cbOrangeJuice, cbCompote, cbWater, cbLemonade
        };

        for (CheckBox cb : foods) {
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());
        }
        cbDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());

        // Тапсырысты растау
        btnConfirmOrder.setOnClickListener(v -> {
            String room = etRoomNumber.getText().toString().trim();
            String floor = etFloor.getText().toString().trim();
            String total = tvTotalPrice.getText().toString();

            if (room.isEmpty() || floor.isEmpty()) {
                Toast.makeText(this, "Бөлме және этаж нөмірін енгізіңіз!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Тапсырыс қабылданды!\nБөлме: " + room + ", Этаж: " + floor + "\n" + total, Toast.LENGTH_LONG).show();
            }
        });

        // Kaspi төлемі
        btnPayKaspi.setOnClickListener(v -> {
            int totalAmount = getTotalAmount();
            if (totalAmount == 0) {
                Toast.makeText(this, "Алдымен тағам таңдаңыз!", Toast.LENGTH_SHORT).show();
                return;
            }
            String paymentUrl = "https://kaspi.kz/shop/pay/?amount=" + totalAmount;
            Intent kaspiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
            startActivity(kaspiIntent);
        });

        // Чек фотосын жүктеу
        btnUploadReceipt.setOnClickListener(v -> openImageChooser());

        // Артқа қайту батырмасы
        btnGoBack.setOnClickListener(v -> {
            Intent intent = new Intent(FoodMenuActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        updateTotal();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Чекті таңдаңыз"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            receiptUri = data.getData();
            try {
                ivReceipt.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), receiptUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ... getFoodTotal(), updateTotal(), getTotalAmount() функциялары сол қалпында қалады ...
    private int getFoodTotal() {
        int total = 0;
        if (cbOmelet.isChecked()) total += 1200;
        if (cbBuckwheat.isChecked()) total += 1000;
        if (cbSandwich.isChecked()) total += 900;
        if (cbFriedEgg.isChecked()) total += 1300;
        if (cbOatmeal.isChecked()) total += 1100;
        if (cbPancake.isChecked()) total += 1400;
        if (cbBorscht.isChecked()) total += 1500;
        if (cbChickenSoup.isChecked()) total += 1400;
        if (cbSolyanka.isChecked()) total += 1800;
        if (cbRassolnik.isChecked()) total += 1600;
        if (cbMushroomSoup.isChecked()) total += 1700;
        if (cbPumpkinSoup.isChecked()) total += 1600;
        if (cbFriedChicken.isChecked()) total += 2500;
        if (cbPilaf.isChecked()) total += 2800;
        if (cbSteamedFish.isChecked()) total += 2700;
        if (cbBeefStroganoff.isChecked()) total += 3000;
        if (cbStewedBeef.isChecked()) total += 3200;
        if (cbLasagna.isChecked()) total += 3000;
        if (cbCaesar.isChecked()) total += 1600;
        if (cbGreekSalad.isChecked()) total += 1500;
        if (cbVinaigrette.isChecked()) total += 1200;
        if (cbOlivie.isChecked()) total += 1400;
        if (cbTunaSalad.isChecked()) total += 1700;
        if (cbCarrotApple.isChecked()) total += 1200;
        if (cbMedovik.isChecked()) total += 1300;
        if (cbCarrotCake.isChecked()) total += 1200;
        if (cbKartoshka.isChecked()) total += 1000;
        if (cbCheesecake.isChecked()) total += 1500;
        if (cbChocolateMousse.isChecked()) total += 1400;
        if (cbFruitSalad.isChecked()) total += 1200;
        if (cbMayonnaise.isChecked()) total += 300;
        if (cbTomatoSauce.isChecked()) total += 250;
        if (cbGarlicSauce.isChecked()) total += 350;
        if (cbSourCreamSauce.isChecked()) total += 300;
        if (cbMustardSauce.isChecked()) total += 300;
        if (cbTea.isChecked()) total += 400;
        if (cbLatte.isChecked()) total += 800;
        if (cbOrangeJuice.isChecked()) total += 900;
        if (cbCompote.isChecked()) total += 500;
        if (cbWater.isChecked()) total += 300;
        if (cbLemonade.isChecked()) total += 600;
        return total;
    }

    private void updateTotal() {
        int foodTotal = getFoodTotal();
        double deliveryFee = 0;
        String deliveryText;
        int finalTotal;

        if (roomType.equals("Люкс") || roomType.equals("Президенттік люкс")) {
            finalTotal = (int) Math.round(foodTotal * 0.7);
            if (cbDelivery.isChecked()) {
                deliveryText = "Доставка: БЕСПЛАТНО";
            } else {
                deliveryText = "Доставка таңдалмады";
            }
            tvTotalPrice.setText(deliveryText + "\nТамақтың бағасы: " + finalTotal + " ₸ (30% жеңілдік)");
            return;
        }

        if (cbDelivery.isChecked()) {
            if (roomType.equals("Эконом") || roomType.equals("Стандарт")) {
                deliveryFee = foodTotal * 0.20;
                deliveryText = "Доставка: " + (int) deliveryFee + " ₸ (20%)";
            } else if (roomType.equals("Комфорт") || roomType.equals("Бизнес")) {
                deliveryText = "Доставка: БЕСПЛАТНО";
            } else {
                deliveryText = "Доставка таңдалмады";
            }
        } else {
            deliveryText = "Доставка таңдалмады";
        }

        finalTotal = (int) Math.round(foodTotal + deliveryFee);
        tvTotalPrice.setText(deliveryText + "\nТамақтың бағасы: " + finalTotal + " ₸");
    }

    private int getTotalAmount() {
        int foodTotal = getFoodTotal();
        double deliveryFee = 0;

        if (roomType.equals("Люкс") || roomType.equals("Президенттік люкс")) {
            return 0;
        }

        if (cbDelivery.isChecked() && (roomType.equals("Эконом") || roomType.equals("Стандарт"))) {
            deliveryFee = foodTotal * 0.20;
        }

        return (int) Math.round(foodTotal + deliveryFee);
    }
}
