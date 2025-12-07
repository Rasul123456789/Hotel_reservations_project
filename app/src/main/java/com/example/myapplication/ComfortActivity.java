package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.Calendar;

public class ComfortActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView tvImageIndicator;
    private ImageButton btnPrevImage, btnNextImage;

    private ScrollView scrollView;

    private Button btnCheckIn, btnCheckOut, btnBook, btnBack;
    private TextView tvTotalPrice, tvCharacteristics;

    private Calendar checkInCal = Calendar.getInstance();
    private Calendar checkOutCal = Calendar.getInstance();
    private boolean isCheckInSet = false;
    private boolean isCheckOutSet = false;

    private final int pricePerDay = 18000; // бұрын 12000 болатын

    private final String[] russianDays = {"Пн","Вт","Ср","Чт","Пт","Сб","Вс"};
    private final String[] russianMonths = {"Янв","Фев","Мар","Апр","Май","Июн","Июл","Авг","Сен","Окт","Ноя","Дек"};

    // Бірнеше фото
    int[] roomImages = {R.drawable.inter_room, R.drawable.inter_room1, R.drawable.inter_room2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comfort);

        // ---------------- VIEWPAGER ----------------
        viewPager = findViewById(R.id.viewPager);
        tvImageIndicator = findViewById(R.id.tvImageIndicator);
        btnPrevImage = findViewById(R.id.btnPrevImage);
        btnNextImage = findViewById(R.id.btnNextImage);

        ImageSliderAdapter adapter = new ImageSliderAdapter(this, roomImages);
        viewPager.setAdapter(adapter);

        tvImageIndicator.setText("1 / " + roomImages.length);

        // Scale эффект (орталық сурет үлкейеді)
        viewPager.setPageTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setAlpha(0.5f + (1 - Math.abs(position)) * 0.5f);
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tvImageIndicator.setText((position+1) + " / " + roomImages.length);
            }
        });

        // Prev / Next батырмалары
        btnPrevImage.setOnClickListener(v -> {
            int pos = viewPager.getCurrentItem();
            if(pos > 0) viewPager.setCurrentItem(pos-1);
        });

        btnNextImage.setOnClickListener(v -> {
            int pos = viewPager.getCurrentItem();
            if(pos < roomImages.length-1) viewPager.setCurrentItem(pos+1);
        });

        // ---------------- PARALLAX EFFECT ----------------
        scrollView = findViewById(R.id.scrollView);
        viewPager.setTranslationZ(10);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY();
            viewPager.setTranslationY(scrollY*0.4f);
        });

        // ---------------- БАСҚА КОМПОНЕНТТЕР ----------------
        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnBook = findViewById(R.id.btnBook);
        btnBack = findViewById(R.id.btnBack);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvCharacteristics = findViewById(R.id.tvCharacteristics);

        tvCharacteristics.setText(
                "📏 Квадратура: 48 м²\n" +
                        "🚪 Балкон: Бар (шағын)\n" +
                        "🌅 Терезе: Қала көрінісі\n" +
                        "🚿 Санузел: Душ кабина\n" +
                        "🛏 Төсек: King Size\n" +
                        "📺 Smart TV\n" +
                        "📶 Wi-Fi\n" +
                        "❄️ Кондиционер\n" +
                        "🔥 Жылыту\n" +
                        "🍽 Мини-бар\n" +
                        "🧴 Гигиеналық құралдар\n" +
                        "🧹 Тазалау: күнделікті\n" +
                        "🔒 Сейф\n" +
                        "🚭 Темекі шегуге болмайды"
        );

        btnCheckIn.setOnClickListener(v -> showCustomDatePicker(true));
        btnCheckOut.setOnClickListener(v -> showCustomDatePicker(false));

        btnBook.setOnClickListener(v -> {
            if(!isCheckInSet || !isCheckOutSet){
                Toast.makeText(this,"Келу және кету күнін таңдаңыз",Toast.LENGTH_SHORT).show();
            }else{
                startActivity(new Intent(this, BookingActivity.class));
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    // ---------------- FULLSCREEN IMAGE ----------------
    public void showFullScreenImage(int imageRes){
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fullscreen_image);

        PhotoView photoView = dialog.findViewById(R.id.photoView);
        ImageView btnClose = dialog.findViewById(R.id.btnClose);

        photoView.setImageResource(imageRes);

        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // ---------------- DATE PICKER ----------------
    private void showCustomDatePicker(boolean isCheckIn){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_datepicker);

        TextView tvMonthYear = dialog.findViewById(R.id.tvMonthYear);
        GridLayout gridDays = dialog.findViewById(R.id.gridDays);
        Button btnPrevMonth = dialog.findViewById(R.id.btnPrevMonth);
        Button btnNextMonth = dialog.findViewById(R.id.btnNextMonth);

        final Calendar cal = isCheckIn && isCheckInSet ? (Calendar) checkInCal.clone() :
                !isCheckIn && isCheckOutSet ? (Calendar) checkOutCal.clone() : Calendar.getInstance();

        updateCalendar(gridDays, tvMonthYear, cal, isCheckIn, dialog);

        btnPrevMonth.setOnClickListener(v -> {
            cal.add(Calendar.MONTH,-1);
            updateCalendar(gridDays, tvMonthYear, cal, isCheckIn, dialog);
        });

        btnNextMonth.setOnClickListener(v -> {
            cal.add(Calendar.MONTH,1);
            updateCalendar(gridDays, tvMonthYear, cal, isCheckIn, dialog);
        });

        dialog.show();
    }

    private void updateCalendar(GridLayout gridDays, TextView tvMonthYear, Calendar cal, boolean isCheckIn, Dialog dialog){
        gridDays.removeAllViews();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        tvMonthYear.setText(russianMonths[month]+" "+year);

        for(String day: russianDays){
            TextView tvDay = new TextView(this);
            tvDay.setText(day);
            tvDay.setGravity(Gravity.CENTER);
            tvDay.setPadding(8,8,8,8);
            gridDays.addView(tvDay);
        }

        Calendar tempCal = (Calendar) cal.clone();
        tempCal.set(Calendar.DAY_OF_MONTH,1);
        int firstDayOfWeek = (tempCal.get(Calendar.DAY_OF_WEEK)+5)%7;

        for(int i=0;i<firstDayOfWeek;i++){
            TextView tvEmpty = new TextView(this);
            gridDays.addView(tvEmpty);
        }

        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int day=1;day<=daysInMonth;day++){
            final int selectedDay = day;
            TextView tvDate = new TextView(this);
            tvDate.setText(String.valueOf(day));
            tvDate.setGravity(Gravity.CENTER);
            tvDate.setPadding(20,20,20,20);

            Calendar dateCal = Calendar.getInstance();
            dateCal.set(year,month,day);

            boolean isPast = dateCal.before(Calendar.getInstance());
            if(isPast) tvDate.setAlpha(0.3f);

            tvDate.setOnClickListener(v -> {
                if(isPast){
                    Toast.makeText(this,"Прошлые даты недоступны",Toast.LENGTH_SHORT).show();
                    return;
                }

                int dayOfWeek = dateCal.get(Calendar.DAY_OF_WEEK);
                int rusDayIndex = (dayOfWeek+5)%7;
                String dateStr = russianDays[rusDayIndex]+", "+selectedDay+" "+russianMonths[month]+" "+year;

                if(isCheckIn){
                    checkInCal = dateCal;
                    btnCheckIn.setText(dateStr);
                    isCheckInSet = true;
                }else{
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

    private void calculatePrice(){
        if(isCheckInSet && isCheckOutSet){
            long diff = checkOutCal.getTimeInMillis()-checkInCal.getTimeInMillis();
            long days = diff/(24*60*60*1000);
            if(days<=0){
                tvTotalPrice.setText("0 ₸");
                Toast.makeText(this,"Кету күні келу күнінен кейін болуы керек",Toast.LENGTH_SHORT).show();
                return;
            }
            tvTotalPrice.setText(days*pricePerDay+" ₸");
        }
    }

}
