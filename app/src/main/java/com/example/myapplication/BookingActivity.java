package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {

    private EditText etFullName, etPhone, etEmail, etAdults, etKids, etRequest;
    private Button btnConfirmBooking, btnBackBooking, btnKaspiPay, btnHalykPay, btnUploadCheck;

    private Uri selectedCheckUri = null;

    private ActivityResultLauncher<String> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_form);

        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAdults = findViewById(R.id.etAdults);
        etKids = findViewById(R.id.etKids);
        etRequest = findViewById(R.id.etRequest);

        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        btnBackBooking = findViewById(R.id.btnBackBooking);
        btnKaspiPay = findViewById(R.id.btnKaspiPay);
        btnHalykPay = findViewById(R.id.btnHalykPay);
        btnUploadCheck = findViewById(R.id.btnUploadCheck);

        btnBackBooking.setOnClickListener(v -> finish());

        // 🔹 Чек жүктеу үшін файл таңдағыш дайындау
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedCheckUri = uri;
                        Toast.makeText(this, "Чек жүктелді!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // 🟠 Чек жүктеу батырмасы
        btnUploadCheck.setOnClickListener(v -> {
            if (!validateForm()) {
                Toast.makeText(this, "Алдымен анкетаны толтырыңыз!", Toast.LENGTH_SHORT).show();
                return;
            }
            filePickerLauncher.launch("image/*");
        });

        // 🔵 Kaspi Pay
        btnKaspiPay.setOnClickListener(v -> {
            if (!validateForm()) {
                Toast.makeText(this, "Алдымен барлық өрісті толтырыңыз!", Toast.LENGTH_SHORT).show();
                return;
            }
            openKaspiPayment();
        });

        // 🟡 Halyk Pay
        btnHalykPay.setOnClickListener(v -> {
            if (!validateForm()) {
                Toast.makeText(this, "Алдымен барлық өрісті толтырыңыз!", Toast.LENGTH_SHORT).show();
                return;
            }
            openHalykPayment();
        });

        // ✔ Брондауды аяқтау
        btnConfirmBooking.setOnClickListener(v -> {
            if (!validateForm()) {
                Toast.makeText(this, "Барлық өрісті толтырыңыз!", Toast.LENGTH_SHORT).show();
                return;
            }



            showSuccessDialog();
        });
    }

    // 🔵 Kaspi QR
    private void openKaspiPayment() {
        String kaspiUrl = "https://kaspi.kz/pay/your_shop_code_here";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(kaspiUrl)));
    }

    // 🟡 Halyk QR
    private void openHalykPayment() {
        String halykUrl = "https://pay.halykbank.kz/pay/your_qr_code_here";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(halykUrl)));
    }

    // 🧾 Форманы тексеру
    private boolean validateForm() {
        return !etFullName.getText().toString().trim().isEmpty()
                && !etPhone.getText().toString().trim().isEmpty()
                && !etEmail.getText().toString().trim().isEmpty()
                && !etAdults.getText().toString().trim().isEmpty();
    }

    // ✔ Сәтті аяқталды
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("✔ Брондау аяқталды")
                .setMessage("Бөлме сәтті брондалды!\nМенеджер сізге хабарласады.")
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }
}
