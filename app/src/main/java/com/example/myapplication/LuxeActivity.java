package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class LuxeActivity extends AppCompatActivity {

    private ImageView ivRoomImage;
    private ScrollView scrollView;

    private Button btnCheckIn, btnCheckOut, btnBook, btnBack;
    private TextView tvTotalPrice;

    private Calendar checkInCal = Calendar.getInstance();
    private Calendar checkOutCal = Calendar.getInstance();
    private boolean isCheckInSet = false;
    private boolean isCheckOutSet = false;

    private final int pricePerDay = 85000; // Luxe бағасы

    private final String[] russianDays = {"Пн","Вт","Ср","Чт","Пт","Сб","Вс"};
    private final String[] russianMonths = {"Янв","Фев","Мар","Апр","Май","Июн","Июл","Авг","Сен","Окт","Ноя","Дек"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luxe);

        ivRoomImage = findViewById(R.id.ivRoomImage);
        scrollView = findViewById(R.id.scrollView);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY();
            ivRoomImage.setTranslationY(scrollY * 0.4f);
        });

        // 📌 Фотоға басқанда YouTube-қа кіреді
        ivRoomImage.setOnClickListener(v -> openYoutubeVideo());

        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnBook = findViewById(R.id.btnBook);
        btnBack = findViewById(R.id.btnBack);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        btnCheckIn.setOnClickListener(v -> showCustomDatePicker(true));
        btnCheckOut.setOnClickListener(v -> showCustomDatePicker(false));

        btnBook.setOnClickListener(v -> {
            if(!isCheckInSet || !isCheckOutSet){
                Toast.makeText(this, "Келу және кету күнін таңдаңыз", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(LuxeActivity.this, BookingActivity.class));
            }
        });

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(LuxeActivity.this, MainActivity.class));
            finish();
        });
    }

    // 📌 YouTube ашу функциясы
    private void openYoutubeVideo() {
        String url = "https://www.youtube.com/shorts/h7cy4B-eAG8";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void showCustomDatePicker(boolean isCheckIn) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_datepicker);

        TextView tvMonthYear = dialog.findViewById(R.id.tvMonthYear);
        GridLayout gridDays = dialog.findViewById(R.id.gridDays);
        Button btnPrevMonth = dialog.findViewById(R.id.btnPrevMonth);
        Button btnNextMonth = dialog.findViewById(R.id.btnNextMonth);

        final Calendar cal = isCheckIn && isCheckInSet ? (Calendar) checkInCal.clone() :
                !isCheckIn && isCheckOutSet ? (Calendar) checkOutCal.clone() : Calendar.getInstance();

        updateCalendar(gridDays, tvMonthYear, cal, dialog, isCheckIn);

        btnPrevMonth.setOnClickListener(v -> {
            cal.add(Calendar.MONTH, -1);
            updateCalendar(gridDays, tvMonthYear, cal, dialog, isCheckIn);
        });

        btnNextMonth.setOnClickListener(v -> {
            cal.add(Calendar.MONTH, 1);
            updateCalendar(gridDays, tvMonthYear, cal, dialog, isCheckIn);
        });

        dialog.show();
    }

    private void updateCalendar(GridLayout gridDays, TextView tvMonthYear, Calendar cal, Dialog dialog, boolean isCheckIn) {
        gridDays.removeAllViews();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        tvMonthYear.setText(russianMonths[month] + " " + year);

        for (String day : russianDays) {
            TextView tvDay = new TextView(this);
            tvDay.setText(day);
            tvDay.setGravity(Gravity.CENTER);
            tvDay.setPadding(8,8,8,8);
            gridDays.addView(tvDay);
        }

        Calendar tempCal = (Calendar) cal.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = (tempCal.get(Calendar.DAY_OF_WEEK) + 5) % 7;

        for(int i = 0; i < firstDayOfWeek; i++){
            gridDays.addView(new TextView(this));
        }

        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int day = 1; day <= daysInMonth; day++){
            final int selectedDay = day;
            Calendar dateCal = Calendar.getInstance();
            dateCal.set(year, month, day);

            TextView tvDate = new TextView(this);
            tvDate.setText(String.valueOf(day));
            tvDate.setGravity(Gravity.CENTER);
            tvDate.setPadding(16,16,16,16);

            boolean isPast = dateCal.before(Calendar.getInstance());
            if(isPast) tvDate.setAlpha(0.3f);

            tvDate.setOnClickListener(v -> {
                if(isPast){
                    Toast.makeText(this, "Прошлые даты недоступны", Toast.LENGTH_SHORT).show();
                    return;
                }

                int dow = dateCal.get(Calendar.DAY_OF_WEEK);
                int rusDay = (dow + 5) % 7;

                String dateStr = russianDays[rusDay] + ", " + selectedDay + " " + russianMonths[month] + " " + year;

                if(isCheckIn){
                    checkInCal = dateCal;
                    btnCheckIn.setText(dateStr);
                    isCheckInSet = true;
                } else {
                    checkOutCal = dateCal;
                    btnCheckOut.setText(dateStr);
                    isCheckOutSet = true;
                }

                calculatePrice();
                dialog.dismiss();
            });

            gridDays.addView(tvDate);
        }
    }

    private void calculatePrice() {
        if(isCheckInSet && isCheckOutSet){
            long diff = checkOutCal.getTimeInMillis() - checkInCal.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            if(days <= 0){
                tvTotalPrice.setText("0 ₸");
                Toast.makeText(this, "Кету күні келу күнінен кейін болуы керек", Toast.LENGTH_SHORT).show();
                return;
            }
            tvTotalPrice.setText(days * pricePerDay + " ₸");
        }
    }
}
