package com.example.lessonschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.color.MaterialColors;

import java.lang.reflect.Array;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static ArrayList<ClassInfo> allClasses;
    private static int semesterIndex, grade;
    private static String teachWeekStartDate;

    private GridLayout allClassGrid;
    private TextView semesterTextView, weekIndexTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allClassGrid = findViewById(R.id.main_grid_layout_all_class);
        semesterTextView = findViewById(R.id.main_text_view_semester);
        weekIndexTextView = findViewById(R.id.main_text_view_week_index);
        findViewById(R.id.main_float_btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        if (allClasses == null) getStoredData();
        addAllClass();
        setSemesterWeek();
    }

    private void setSemesterWeek() {
        final String[] num2ChineseNum = new String[]{null, "一", "二", "三"};
        if (grade > 0 && grade <= 3 && semesterIndex >= 0 && semesterIndex <= 1)
            semesterTextView.setText(String.format("研%s%s", num2ChineseNum[grade], (semesterIndex == 0 ? "上" : "下")));

        int dayOfWeek = GregorianCalendar.getInstance().get(Calendar.DAY_OF_WEEK);
        // 周日是第一天
        if (dayOfWeek >= 2 && dayOfWeek <= 6) {
            LinearLayout linearLayout = findViewById(R.id.main_linear_weekdays);
            linearLayout.getChildAt(dayOfWeek - 2).setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
        }
        try {
            Date now = new Date();
            Date start = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(teachWeekStartDate);
            if (start != null) {
                Log.e(TAG, "setSemesterWeek: " + start + ", now: " + now + " dif: " + (now.getTime() - start.getTime()));
                long weekIndex = (now.getTime() - start.getTime()) / (7L * 24 * 60 * 60 * 1000) + 1;
                weekIndexTextView.setText(String.format(Locale.CHINA, "第%d周", weekIndex));
            }
        } catch (Exception e) {
            Log.e(TAG, "getStoredData: incorrect date format");
        }
    }

    private void addAllClass() {
        int k = 0;
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 13; ) {
                if (k < allClasses.size() && allClasses.get(k).getWeekdayIndex() == i && allClasses.get(k).getClassStartIndex() == j) {
                    ClassInfo tmpClassInfo = allClasses.get(k);
                    TextView textView = new TextView(new ContextThemeWrapper(this, R.style.TextViewClassInfo));
                    textView.setText(tmpClassInfo.toClassInfoString());
                    textView.setLayoutParams(new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
                    CardView cardView = new CardView(new ContextThemeWrapper(this, R.style.CardViewClass));
                    cardView.addView(textView);
                    cardView.setCardBackgroundColor(tmpClassInfo.getBgColor());
                    GridLayout.Spec rowSpec = GridLayout.spec(j - 1, tmpClassInfo.getClassPeriod());
                    GridLayout.Spec columnSpec = GridLayout.spec(i, 1.0f);
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
                    layoutParams.setGravity(Gravity.FILL);
                    layoutParams.height = 0;
                    layoutParams.width = 0;
                    allClassGrid.addView(cardView, layoutParams);
                    j += tmpClassInfo.getClassPeriod();
                    k++;
                } else {
                    View child = new TextView(this);
                    GridLayout.Spec rowSpec = GridLayout.spec(j - 1, 1);
                    GridLayout.Spec columnSpec = GridLayout.spec(i, 1, 1.0f);
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
                    layoutParams.width = 0;
                    layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    layoutParams.setGravity(Gravity.FILL);
                    child.setBackgroundColor(getResources().getColor(R.color.lightGray, null));
                    allClassGrid.addView(child, layoutParams);
                    j += 1;
                }
            }
        }
    }

    private void getStoredData() {
        allClasses = new ArrayList<>();
        allClasses.add(new ClassInfo("计算数论", "3A112", 1, 15, 1, 6, 2));
        allClasses.add(new ClassInfo("信号与信息处理", "3C101", 2, 16, 1, 11, 3));
        allClasses.add(new ClassInfo("高级计算机网络", "3B202", 1, 16, 2, 3, 3));
        allClasses.add(new ClassInfo("日常交流英语", "Jamie/2205/5132562595/jamie", 1, 16, 2, 6, 2));
        allClasses.add(new ClassInfo("高级计算机网络", "3C101", 8, 12, 4, 1, 2));
        allClasses.add(new ClassInfo("计算数论", "3A112", 1, 14, 4, 6, 2));
        semesterIndex = 1;
        grade = 1;
        teachWeekStartDate = "2020-02-17";
    }
}
