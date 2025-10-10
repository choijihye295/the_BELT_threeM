package com.summer.threemforth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout announce;
    int belt_point;
    int beltpoint_level = 0;
    int dedupeRed = 0;
    DrawerLayout drawerLayout;
    LinearLayout exit;
    LinearLayout home;
    ImageView imageView;
    LinearLayout list;
    ImageView menu;
    LinearLayout settings;
    TextView textView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        int i = PreferenceManager.getInt(this, "point");
        this.belt_point = i;
        if (i <= 0) {
            PreferenceManager.setInt(this, "point", 0);
        }
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.menu = (ImageView) findViewById(R.id.menu);
        this.home = (LinearLayout) findViewById(R.id.home);
        this.settings = (LinearLayout) findViewById(R.id.setting);
        this.list = (LinearLayout) findViewById(R.id.list);
        this.announce = (LinearLayout) findViewById(R.id.announce);
        this.exit = (LinearLayout) findViewById(R.id.exit);
        this.imageView = (ImageView) findViewById(R.id.img_belt);
        this.textView = (TextView) findViewById(R.id.text_belt);
        this.menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("menu", "메뉴 버튼 클릭");
                MainActivity.openDrawer(MainActivity.this.drawerLayout);
            }
        });
        this.home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.recreate();
            }
        });
        this.settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.redirectActivity(MainActivity.this, SettingsActivity.class);
            }
        });
        this.announce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.redirectActivity(MainActivity.this, NoticeList.class);
            }
        });
        this.list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.redirectActivity(MainActivity.this, TodoList.class);
            }
        });
        this.exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this).setTitle("종료하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.moveTaskToBack(true);
                        MainActivity.this.finishAndRemoveTask();
                        System.exit(0);
                    }
                }).setNegativeButton("취소", (DialogInterface.OnClickListener) null).show();
            }
        });
        int i2 = this.belt_point;
        if (i2 < 200) {
            this.beltpoint_level = 0;
        }
        if (i2 >= 200) {
            this.beltpoint_level = 1;
        }
        if (i2 >= 500) {
            this.beltpoint_level = 2;
        }
        if (i2 >= 1000) {
            this.beltpoint_level = 3;
        }
        if (i2 >= 1500) {
            this.beltpoint_level = 4;
        }
        if (i2 >= 2000) {
            this.beltpoint_level = 5;
        } else {
            this.beltpoint_level = 0;
        }
        if (i2 < 200) {
            TextView textView2 = this.textView;
            textView2.setText(this.belt_point + " / 200");
        }
        if (this.belt_point >= 200 && checkWhite()) {
            this.imageView.setImageResource(R.drawable.white3);
            TextView textView3 = this.textView;
            textView3.setText(this.belt_point + " / 500");
        }
        if (this.belt_point >= 500 && checkYellow()) {
            this.imageView.setImageResource(R.drawable.yellow3);
            TextView textView4 = this.textView;
            textView4.setText(this.belt_point + " / 1000");
        }
        if (this.belt_point >= 1000 && checkBlue()) {
            this.imageView.setImageResource(R.drawable.blue3);
            TextView textView5 = this.textView;
            textView5.setText(this.belt_point + " / 1500");
        }
        if (this.belt_point >= 1500 && checkRed()) {
            this.imageView.setImageResource(R.drawable.red3);
            TextView textView6 = this.textView;
            textView6.setText(this.belt_point + " / 2000");
        }
        if (this.belt_point >= 2000) {
            this.imageView.setImageResource(R.drawable.black3);
            TextView textView7 = this.textView;
            textView7.setText(this.belt_point + " / ~");
        }
    }

    public static void openDrawer(DrawerLayout drawerLayout2) {
        drawerLayout2.openDrawer((int) GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout2) {
        if (drawerLayout2.isDrawerOpen((int) GravityCompat.START)) {
            drawerLayout2.closeDrawer((int) GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        intent.setFlags(268435456);
        activity.startActivity(intent);
        activity.finish();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        closeDrawer(this.drawerLayout);
    }

    private boolean checkWhite() {
        int i;
        int i2;
        int i3;
        SQLiteDatabase readableDatabase = new todoDBHelper(this).getReadableDatabase();
        if (readableDatabase.rawQuery("SELECT * FROM todoList WHERE third = '1학년 대학생활계획서' AND done = 1;", (String[]) null) != null) {
            i2 = 20;
            i = 1;
        } else {
            i2 = 0;
            i = 0;
        }
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('성격검사(MBTI, LCSI, 에니어그램), 인성검사(MMPI, PAI)', '진로탐색검사, 직업선호도검사, 직업가치관 검사, 직업심리검사(LOPI)');", (String[]) null);
        rawQuery.moveToFirst();
        int i4 = 0;
        while (rawQuery.moveToNext()) {
            String string = rawQuery.getString(rawQuery.getColumnIndex("third"));
            int i5 = rawQuery.getInt(rawQuery.getColumnIndex("done"));
            if ("성격검사(MBTI, LCSI, 에니어그램), 인성검사(MMPI, PAI)".equals(string) && i5 == 1) {
                i4++;
                i2 += 10;
            }
            if ("진로탐색검사, 직업선호도검사, 직업가치관 검사, 직업심리검사(LOPI)".equals(string) && i5 == 1) {
                i4++;
                i2 += 5;
            }
        }
        if (i4 >= 1) {
            i++;
        }
        Cursor rawQuery2 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('JBNU 독서감상문(교양 100선)', 'JBNU 독서감상문(고전명저 110선)');", (String[]) null);
        if (rawQuery2 != null) {
            i2 += rawQuery2.getCount() * 15;
            if (rawQuery2.getCount() >= 4) {
                i++;
            }
        }
        Cursor rawQuery3 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 2등급 (500이상)', '(22.5월 이전 시험)TOEIC-S 2등급 (80점 이상)', 'OPIc 2등급 (IL)', 'TEPS 2등급 (417 이상)', 'TEPS-S 2등급 (31 이상)', 'IELTS 2등급 (5)', 'TOEFL 2등급 (57)', 'JPT 2등급 (500 이상)', 'JLPT 2등급 (N2 90 이상)', 'HSK 2등급 (4급 211 이상)', 'DELF/DALF 2등급 (A1)', '독일어 2등급 (A1)', '스페인어 2등급 (A1)', 'G-TELP 2등급 (LEVEL1) - , (LEVEL2) - , (LEVEL3) 68 이상', 'New TEPS 2등급 (220 이상)', '(22. 6 .4 이후 시험) TOEIC-S 2등급(1L)');", (String[]) null);
        if (rawQuery3 != null) {
            i2 += rawQuery3.getCount() * 60;
            i3 = 1;
        } else {
            i3 = 0;
        }
        Cursor rawQuery4 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 3등급 (600이상)', '(22.5월 이전 시험)TOEIC-S 3등급 (100점 이상)', 'OPIc 3등급 (IM 1)', 'TEPS 3등급 (482 이상)', 'TEPS-S 3등급 (42 이상)', 'IELTS 3등급 (5.5)', 'TOEFL 3등급 (64)', 'JPT 3등급 (600 이상)', 'JLPT 3등급 (N2 165 이상)', 'HSK 3등급 (5급 180 이상)', 'DELF/DALF 3등급 (A2)', '독일어 3등급 (A2)', '스페인어 3등급 (A2)', 'G-TELP 3등급 (LEVEL1) - , (LEVEL2) 50 이상 , (LEVEL3) 71 이상', 'New TEPS 3등급 (258 이상)', '(22. 6 .4 이후 시험) TOEIC-S 3등급(IM1)');", (String[]) null);
        if (rawQuery4 != null) {
            i2 += rawQuery4.getCount() * 70;
            i3++;
        }
        Cursor rawQuery5 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 4등급 (700이상)', '(22.5월 이전 시험)TOEIC-S 4등급 (120점 이상)', 'OPIc 4등급 (IM 2)', 'TEPS 4등급 (555 이상)', 'TEPS-S 4등급 (50 이상)', 'IELTS 4등급 (6)', 'TOEFL 4등급 (76)', 'JPT 4등급 (700 이상)', 'JLPT 4등급 (N1 100 이상)', 'HSK 4등급 (5급 211 이상)', 'DELF/DALF 4등급 (B1)', '독일어 4등급 (B1)', '스페인어 4등급 (B1)', 'G-TELP 4등급 (LEVEL1) - , (LEVEL2) 65 이상 , (LEVEL3) 85 이상', 'New TEPS 4등급 (300 이상)', '(22. 6 .4 이후 시험) TOEIC-S 4등급(IM2)');", (String[]) null);
        if (rawQuery5 != null) {
            i2 += rawQuery5.getCount() * 80;
            i3++;
        }
        Cursor rawQuery6 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 5등급 (800이상)', '(22.5월 이전 시험)TOEIC-S 5등급 (140점 이상)', 'OPIc 5등급 (IM 3)', 'TEPS 5등급 (637 이상)', 'TEPS-S 5등급 (57 이상)', 'IELTS 5등급 (6.5)', 'TOEFL 5등급 (90)', 'JPT 5등급 (800 이상)', 'JLPT 5등급 (N1 144 이상)', 'HSK 5등급 (6급 180 이상)', 'DELF/DALF 5등급 (B1)', '독일어 5등급 (B2)', '스페인어 5등급 (B2)', 'G-TELP 5등급 (LEVEL1) 50이상 , (LEVEL2) 76 이상 , (LEVEL3) 99 이상', 'New TEPS 5등급 (348 이상)', '(22. 6 .4 이후 시험) TOEIC-S 5등급(IM3)');", (String[]) null);
        if (rawQuery6 != null) {
            i2 += rawQuery6.getCount() * 90;
            i3++;
        }
        Cursor rawQuery7 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 6등급 (900이상)', '(22.5월 이전 시험)TOEIC-S 6등급 (160점 이상)', 'OPIc 6등급 (IH 이상)', 'TEPS 6등급 (766 이상)', 'TEPS-S 6등급 (67 이상)', 'IELTS 6등급 (7)', 'TOEFL 6등급 (105)', 'JPT 6등급 (900 이상)', 'JLPT 5등급 (N1 144 이상)', 'JLPT 6등급 (N1 162 이상)', 'HSK 6등급 (6급 211 이상)', 'DELF/DALF 6등급 (C1)', '독일어 6등급 (C1)', '스페인어 6등급 (C1)', 'G-TELP 6등급 (LEVEL1) 71이상 , (LEVEL2) 90 이상 , (LEVEL3) -', 'New TEPS 6등급 (430 이상)', '(22. 6 .4 이후 시험) TOEIC-S 6등급(1H)');", (String[]) null);
        if (rawQuery7 != null) {
            i2 += rawQuery7.getCount() * 100;
            i3++;
        }
        if (i3 > 0) {
            i++;
        }
        int checkChoice = checkChoice();
        if (i >= 4) {
            Log.d("successwhite", "whitebelt 체크성공했다!!");
            return true;
        } else if (checkChoice + i2 < 200) {
            return false;
        } else {
            Log.d("successwhite", "whitebelt 체크성공했다!!");
            return true;
        }
    }

    public boolean checkYellow() {
        int i;
        if (this.beltpoint_level < 1) {
            return false;
        }
        SQLiteDatabase readableDatabase = new todoDBHelper(this).getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND type = 0;", (String[]) null);
        rawQuery.moveToFirst();
        int i2 = 0;
        int i3 = 0;
        while (rawQuery.moveToNext()) {
            String string = rawQuery.getString(rawQuery.getColumnIndex("third"));
            int i4 = rawQuery.getInt(rawQuery.getColumnIndex("done"));
            if ("2학년 진로계획서".equals(string) && i4 == 1) {
                i3++;
                i2 += 20;
                Log.d("1", "2학년 진로계획서 must");
            }
            if ("직무역량검사, 성인용직업적성검사, 영업직무기본역량검사, IT직무역량검사".equals(string) && i4 == 1) {
                i3++;
                i2 += 5;
            }
            if ("채용(취업) 트렌드 특강 (2시간)".equals(string) && i4 == 1) {
                i3++;
                i2 += 10;
            }
        }
        Cursor rawQuery2 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('JBNU 독서감상문(교양 100선)', 'JBNU 독서감상문(고전명저 110선)');", (String[]) null);
        if (rawQuery2 != null) {
            i2 += (rawQuery2.getCount() + -4 < 0 ? 0 : rawQuery2.getCount() - 4) * 15;
            if (rawQuery2.getCount() >= 8) {
                i3++;
            }
        }
        Cursor rawQuery3 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 3등급 (600이상))', '(22.5월 이전 시험)TOEIC-S 3등급 (100점 이상)', 'OPIc 3등급 (IM 1)', 'TEPS 4등급 (555 이상)', 'TEPS-S 3등급 (42 이상)', 'IELTS 3등급 (5.5)', 'TOEFL 3등급 (64)', 'JPT 3등급 (600 이상)', 'JLPT 3등급 (N2 165 이상)', 'HSK 3등급 (5급 180 이상)', 'DELF/DALF 3등급 (A2)', '독일어 3등급 (A2)', '스페인어 3등급 (A2)', 'G-TELP 3등급 (LEVEL1) - , (LEVEL2) 50 이상 , (LEVEL3) 71 이상', 'New TEPS 3등급 (258 이상)', '(22. 6 .4 이후 시험) TOEIC-S 3등급(IM1)');", (String[]) null);
        if (rawQuery3 != null) {
            i2 += rawQuery3.getCount() * 70;
            i = 1;
        } else {
            i = 0;
        }
        Cursor rawQuery4 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 4등급 (700이상)', '(22.5월 이전 시험)TOEIC-S 4등급 (120점 이상)', 'OPIc 4등급 (IM 2)', 'TEPS 4등급 (555 이상)', 'TEPS-S 4등급 (50 이상)', 'IELTS 4등급 (6)', 'TOEFL 4등급 (76)', 'JPT 4등급 (700 이상)', 'JLPT 4등급 (N1 100 이상)', 'HSK 4등급 (5급 211 이상)', 'DELF/DALF 4등급 (B1)', '독일어 4등급 (B1)', '스페인어 4등급 (B1)', 'G-TELP 4등급 (LEVEL1) - , (LEVEL2) 65 이상 , (LEVEL3) 85 이상', 'New TEPS 4등급 (300 이상)', '(22. 6 .4 이후 시험) TOEIC-S 4등급(IM2)');", (String[]) null);
        if (rawQuery4 != null) {
            i2 += rawQuery4.getCount() * 80;
            i++;
        }
        Cursor rawQuery5 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 5등급 (800이상)', '(22.5월 이전 시험)TOEIC-S 5등급 (140점 이상)', 'OPIc 5등급 (IM 3)', 'TEPS 5등급 (637 이상)', 'TEPS-S 5등급 (57 이상)', 'IELTS 5등급 (6.5)', 'TOEFL 5등급 (90)', 'JPT 5등급 (800 이상)', 'JLPT 5등급 (N1 144 이상)', 'HSK 5등급 (6급 180 이상)', 'DELF/DALF 5등급 (B1)', '독일어 5등급 (B2)', '스페인어 5등급 (B2)', 'G-TELP 5등급 (LEVEL1) 50이상 , (LEVEL2) 76 이상 , (LEVEL3) 99 이상', 'New TEPS 5등급 (348 이상)', '(22. 6 .4 이후 시험) TOEIC-S 5등급(IM3)');", (String[]) null);
        if (rawQuery5 != null) {
            i2 += rawQuery5.getCount() * 90;
            i++;
        }
        Cursor rawQuery6 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 6등급 (900이상)', '(22.5월 이전 시험)TOEIC-S 6등급 (160점 이상)', 'OPIc 6등급 (IH 이상)', 'TEPS 6등급 (766 이상)', 'TEPS-S 6등급 (67 이상)', 'IELTS 6등급 (7)', 'TOEFL 6등급 (105)', 'JPT 6등급 (900 이상)', 'JLPT 5등급 (N1 144 이상)', 'JLPT 6등급 (N1 162 이상)', 'HSK 6등급 (6급 211 이상)', 'DELF/DALF 6등급 (C1)', '독일어 6등급 (C1)', '스페인어 6등급 (C1)', 'G-TELP 6등급 (LEVEL1) 71이상 , (LEVEL2) 90 이상 , (LEVEL3) -', 'New TEPS 6등급 (430 이상)', '(22. 6 .4 이후 시험) TOEIC-S 6등급(1H)');", (String[]) null);
        if (rawQuery6 != null) {
            i2 += rawQuery6.getCount() * 100;
            i++;
        }
        if (i > 0) {
            i3++;
        }
        String num = Integer.toString(i3);
        Log.d("checkmust", "mustcheck의 값은 = " + num);
        int checkChoice = checkChoice() + i2;
        String num2 = Integer.toString(checkChoice);
        Log.d("checkpoint", "mustpoint+choicepoint 의 값은 = " + num2);
        if (checkChoice >= 300) {
            Log.d("successyellow", "yellowbelt 체크성공했다!!");
            return true;
        } else if (i3 < 5) {
            return false;
        } else {
            Log.d("successyellow", "yellowbelt 체크성공했다!!");
            return true;
        }
    }

    public int checkChoice() {
        Cursor rawQuery = new todoDBHelper(this).getReadableDatabase().rawQuery("SELECT * FROM todoList WHERE done = 1 AND type = 2;", (String[]) null);
        rawQuery.moveToFirst();
        int i = 0;
        while (rawQuery.moveToNext()) {
            int i2 = rawQuery.getInt(rawQuery.getColumnIndex("done"));
            int i3 = rawQuery.getInt(rawQuery.getColumnIndex("point"));
            if (i2 == 1) {
                i += i3;
            }
        }
        return i;
    }

    public boolean checkBlue() {
        int i;
        int i2;
        int i3;
        if (this.beltpoint_level < 2) {
            Log.d("back", "bluebelt 빠꾸먹음");
            return false;
        }
        SQLiteDatabase readableDatabase = new todoDBHelper(this).getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND type = 0;", (String[]) null);
        rawQuery.moveToFirst();
        int i4 = 0;
        int i5 = 0;
        while (rawQuery.moveToNext()) {
            String string = rawQuery.getString(rawQuery.getColumnIndex("third"));
            int i6 = rawQuery.getInt(rawQuery.getColumnIndex("done"));
            if ("3학년 경력계획서(진학/취업 중 택일)".equals(string) && i6 == 1) {
                i5++;
                i4 += 20;
            }
            if ("취업지원과 상담".equals(string) && i6 == 1) {
                i5++;
                i4 += 10;
            }
            if ("입사지원서 작성법 집중교육".equals(string) && i6 == 1) {
                i5++;
                i4 += 30;
            }
            if ("NCS/기업 직무적성검사(정답률 40% 이상)".equals(string) && i6 == 1) {
                i5++;
                i4 += 30;
            }
        }
        Cursor rawQuery2 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('JBNU 독서감상문(교양 100선)', 'JBNU 독서감상문(고전명저 110선)');", (String[]) null);
        if (rawQuery2 != null) {
            i4 += (rawQuery2.getCount() - 8 < 0 ? 0 : rawQuery2.getCount() - 8) * 15;
            if (rawQuery2.getCount() >= 8) {
                i5++;
            }
        }
        Cursor rawQuery3 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 4등급 (700이상)', '(22.5월 이전 시험)TOEIC-S 4등급 (120점 이상)', 'OPIc 4등급 (IM 2)', 'TEPS 4등급 (555 이상)', 'TEPS-S 4등급 (50 이상)', 'IELTS 4등급 (6)', 'TOEFL 4등급 (76)', 'JPT 4등급 (700 이상)', 'JLPT 4등급 (N1 100 이상)', 'HSK 4등급 (5급 211 이상)', 'DELF/DALF 4등급 (B1)', '독일어 4등급 (B1)', '스페인어 4등급 (B1)', 'G-TELP 4등급 (LEVEL1) - , (LEVEL2) 65 이상 , (LEVEL3) 85 이상', 'New TEPS 4등급 (300 이상)', '(22. 6 .4 이후 시험) TOEIC-S 4등급(IM2)');", (String[]) null);
        if (rawQuery3 != null) {
            i4 += rawQuery3.getCount() * 80;
            i = 1;
        } else {
            i = 0;
        }
        Cursor rawQuery4 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 5등급 (800이상)', '(22.5월 이전 시험)TOEIC-S 5등급 (140점 이상)', 'OPIc 5등급 (IM 3)', 'TEPS 5등급 (637 이상)', 'TEPS-S 5등급 (57 이상)', 'IELTS 5등급 (6.5)', 'TOEFL 5등급 (90)', 'JPT 5등급 (800 이상)', 'JLPT 5등급 (N1 144 이상)', 'HSK 5등급 (6급 180 이상)', 'DELF/DALF 5등급 (B1)', '독일어 5등급 (B2)', '스페인어 5등급 (B2)', 'G-TELP 5등급 (LEVEL1) 50이상 , (LEVEL2) 76 이상 , (LEVEL3) 99 이상', 'New TEPS 5등급 (348 이상)', '(22. 6 .4 이후 시험) TOEIC-S 5등급(IM3)');", (String[]) null);
        if (rawQuery4 != null) {
            i4 += rawQuery4.getCount() * 90;
            i++;
        }
        Cursor rawQuery5 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 6등급 (900이상)', '(22.5월 이전 시험)TOEIC-S 6등급 (160점 이상)', 'OPIc 6등급 (IH 이상)', 'TEPS 6등급 (766 이상)', 'TEPS-S 6등급 (67 이상)', 'IELTS 6등급 (7)', 'TOEFL 6등급 (105)', 'JPT 6등급 (900 이상)', 'JLPT 5등급 (N1 144 이상)', 'JLPT 6등급 (N1 162 이상)', 'HSK 6등급 (6급 211 이상)', 'DELF/DALF 6등급 (C1)', '독일어 6등급 (C1)', '스페인어 6등급 (C1)', 'G-TELP 6등급 (LEVEL1) 71이상 , (LEVEL2) 90 이상 , (LEVEL3) -', 'New TEPS 6등급 (430 이상)', '(22. 6 .4 이후 시험) TOEIC-S 6등급(1H)');", (String[]) null);
        if (rawQuery5 != null) {
            i4 += rawQuery5.getCount() * 100;
            i++;
        }
        if (i > 0) {
            i5++;
        }
        Cursor rawQuery6 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('국내외 현장실습, 인턴십(4주 미만)', '직장체험(4주 미만)', '근로장학생(4주 미만)', '중소기업체험(4주 미만)');", (String[]) null);
        if (rawQuery6 != null) {
            i2 = (rawQuery6.getCount() * 20) + 0;
            i3 = rawQuery6.getCount() + 0;
        } else {
            i3 = 0;
            i2 = 0;
        }
        Cursor rawQuery7 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('국내외 현장실습, 인턴십(4주 이상)', '직장체험(4주 이상)', '근로장학생(4주 이상)', '중소기업체험(4주 이상)');", (String[]) null);
        if (rawQuery7 != null) {
            i2 += rawQuery7.getCount() * 30;
            i3 += rawQuery7.getCount();
        }
        Cursor rawQuery8 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('국내외 현장실습, 인턴십(8주 이상)', '직장체험(8주 이상)', '근로장학생(8주 이상)', '중소기업체험(8주 이상)');", (String[]) null);
        if (rawQuery8 != null) {
            i2 += rawQuery8.getCount() * 50;
            i3 += rawQuery8.getCount();
        }
        Cursor rawQuery9 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('국내외 현장실습, 인턴십(15주 이상)', '직장체험(15주 이상)', '근로장학생(15주 이상)', '중소기업체험(15주 이상)');", (String[]) null);
        if (rawQuery9 != null) {
            i2 += rawQuery9.getCount() * 100;
            i3 += rawQuery9.getCount();
        }
        int i7 = i4 + i2;
        this.dedupeRed = i2;
        if (i3 >= 1) {
            i5++;
        }
        Cursor rawQuery10 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('취업지원과 셀프면접 참여', '면접 기초교육(8시간 이내)');", (String[]) null);
        if (rawQuery10.getCount() > 0) {
            i5++;
        }
        rawQuery10.moveToFirst();
        while (rawQuery10.moveToNext()) {
            String string2 = rawQuery10.getString(rawQuery10.getColumnIndex("third"));
            int i8 = rawQuery10.getInt(rawQuery10.getColumnIndex("done"));
            if ("취업지원과 셀프면접 참여".equals(string2) && i8 == 1) {
                i7 += rawQuery10.getCount() * 10;
            }
            if ("면접 기초교육(8시간 이내)".equals(string2) && i8 == 1) {
                i7 += rawQuery10.getCount() * 30;
            }
        }
        int CheckMustchoiceType = CheckMustchoiceType();
        String num = Integer.toString(i5);
        Log.d("checkmust", "mustcheck의 값은 = " + num);
        int i9 = i7 + CheckMustchoiceType;
        String num2 = Integer.toString(i9);
        String num3 = Integer.toString(CheckMustchoiceType);
        Log.d("checkchoice", "choicePoint의 값은 = " + num3);
        Log.d("checkpoint", "mustpoint+choicepoint 의 값은 = " + num2);
        if (i5 >= 8 && CheckMustchoiceType >= 200) {
            Log.d("successBlue", "bluebelt 체크성공했다!!");
            return true;
        } else if (i9 < 500) {
            return false;
        } else {
            Log.d("successBlue", "bluebelt 체크성공했다!!");
            return true;
        }
    }

    public boolean checkRed() {
        int i;
        int i2;
        if (this.beltpoint_level < 3) {
            return false;
        }
        SQLiteDatabase readableDatabase = new todoDBHelper(this).getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND type = 0;", (String[]) null);
        rawQuery.moveToFirst();
        int i3 = 0;
        int i4 = 0;
        while (rawQuery.moveToNext()) {
            String string = rawQuery.getString(rawQuery.getColumnIndex("third"));
            int i5 = rawQuery.getInt(rawQuery.getColumnIndex("done"));
            if ("4학년 취업계획서(진학/취업 중 택일)".equals(string) && i5 == 1) {
                Log.d("55", "4학년취업계획서 must_check += 1");
                i3++;
                i4 += 20;
            }
            if ("입사지원서 클리닉(*취업지원과 상담제외)".equals(string) && i5 == 1) {
                Log.d("66", "입사지원서클리닉 must_check += 1");
                i3++;
                i4 += 10;
            }
            if ("NCS/기업 직무적성검사 교육".equals(string) && i5 == 1) {
                Log.d("77", "ncs/기업 직무적성검사교육 must_check += 1");
                i3++;
                i4 += 30;
            }
        }
        Cursor rawQuery2 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('면접 심화교육(8시간 이상)', '취업캠프, 면접캠프(1일)', '취업캠프, 면접캠프(2일 이상)');", (String[]) null);
        int i6 = 0;
        while (rawQuery2.moveToNext()) {
            String string2 = rawQuery2.getString(rawQuery2.getColumnIndex("third"));
            if ("면접 심화교육(8시간 이상)".equals(string2)) {
                i6++;
                i4 += rawQuery2.getCount() * 50;
                Log.d("123456", "면접 심화교육(8시간 이상) sub_check += 1");
            }
            if ("취업캠프, 면접캠프(1일)".equals(string2)) {
                i6++;
                i4 += rawQuery2.getCount() * 30;
                Log.d("321654", "취업캠프, 면접캠프(1일) sub_check += 1");
            }
            if ("취업캠프, 면접캠프(2일 이상)".equals(string2)) {
                i6++;
                i4 += rawQuery2.getCount() * 50;
                Log.d("789654", "취업캠프, 면접캠프(2일 이상) sub_check += 1");
            }
        }
        if (i6 >= 1) {
            i3++;
            Log.d("0000", "면접심화교육/취업(면접)캠프 must_check += 1");
        } else {
            Log.d("331", "면접심화교육/취업(면접)캠프 must_check += 1 안되고 커서에서 빠꾸먹음...");
        }
        Cursor rawQuery3 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('JBNU 독서감상문(교양 100선)', 'JBNU 독서감상문(고전명저 110선)');", (String[]) null);
        if (rawQuery3 != null) {
            i4 += (rawQuery3.getCount() + -12 < 0 ? 0 : rawQuery3.getCount() - 12) * 15;
            if (rawQuery3.getCount() > 15) {
                i3++;
                Log.d("1", "독서감상문 누적 16권 must_check += 1");
            }
        }
        Cursor rawQuery4 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 5등급 (800이상)', '(22.5월 이전 시험)TOEIC-S 5등급 (140점 이상)', 'OPIc 5등급 (IM 3)', 'TEPS 5등급 (637 이상)', 'TEPS-S 5등급 (57 이상)', 'IELTS 5등급 (6.5)', 'TOEFL 5등급 (90)', 'JPT 5등급 (800 이상)', 'JLPT 5등급 (N1 144 이상)', 'HSK 5등급 (6급 180 이상)', 'DELF/DALF 5등급 (B1)', '독일어 5등급 (B2)', '스페인어 5등급 (B2)', 'G-TELP 5등급 (LEVEL1) 50이상 , (LEVEL2) 76 이상 , (LEVEL3) 99 이상', 'New TEPS 5등급 (348 이상)', '(22. 6 .4 이후 시험) TOEIC-S 5등급(IM3)');", (String[]) null);
        if (rawQuery4 != null) {
            i4 += rawQuery4.getCount() * 90;
            i = 1;
        } else {
            i = 0;
        }
        Cursor rawQuery5 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('TOEIC 6등급 (900이상)', '(22.5월 이전 시험)TOEIC-S 6등급 (160점 이상)', 'OPIc 6등급 (IH 이상)', 'TEPS 6등급 (766 이상)', 'TEPS-S 6등급 (67 이상)', 'IELTS 6등급 (7)', 'TOEFL 6등급 (105)', 'JPT 6등급 (900 이상)', 'JLPT 5등급 (N1 144 이상)', 'JLPT 6등급 (N1 162 이상)', 'HSK 6등급 (6급 211 이상)', 'DELF/DALF 6등급 (C1)', '독일어 6등급 (C1)', '스페인어 6등급 (C1)', 'G-TELP 6등급 (LEVEL1) 71이상 , (LEVEL2) 90 이상 , (LEVEL3) -', 'New TEPS 6등급 (430 이상)', '(22. 6 .4 이후 시험) TOEIC-S 6등급(1H)');", (String[]) null);
        if (rawQuery5 != null) {
            i4 += rawQuery5.getCount() * 100;
            i++;
        }
        if (i > 0) {
            Log.d("11", "외국어 수준 5등급 이상 must_check += 1");
            i3++;
        }
        Cursor rawQuery6 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('국내외 현장실습, 인턴십(4주 미만)', '직장체험(4주 미만)', '근로장학생(4주 미만)', '중소기업체험(4주 미만)');", (String[]) null);
        if (rawQuery6 != null) {
            i4 += rawQuery6.getCount() * 20;
            i2 = rawQuery6.getCount() + 0;
        } else {
            i2 = 0;
        }
        Cursor rawQuery7 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('국내외 현장실습, 인턴십(4주 이상)', '직장체험(4주 이상)', '근로장학생(4주 이상)', '중소기업체험(4주 이상)');", (String[]) null);
        if (rawQuery7 != null) {
            i4 += rawQuery7.getCount() * 30;
            i2 += rawQuery7.getCount();
        }
        Cursor rawQuery8 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('국내외 현장실습, 인턴십(8주 이상)', '직장체험(8주 이상)', '근로장학생(8주 이상)', '중소기업체험(8주 이상)');", (String[]) null);
        if (rawQuery8 != null) {
            i4 += rawQuery8.getCount() * 50;
            i2 += rawQuery8.getCount();
        }
        Cursor rawQuery9 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE done = 1 AND third IN ('국내외 현장실습, 인턴십(15주 이상)', '직장체험(15주 이상)', '근로장학생(15주 이상)', '중소기업체험(15주 이상)');", (String[]) null);
        if (rawQuery9 != null) {
            i4 += rawQuery9.getCount() * 100;
            i2 += rawQuery9.getCount();
        }
        if (i2 > 1) {
            i3++;
            Log.d("222", "기업탐방/ 현장실습 누적  must_check += 1");
        }
        int i7 = this.dedupeRed;
        if (i7 > 0) {
            String num = Integer.toString(i7);
            Log.d("dedupeRed", "blue항목과 기업탐방/ 현장실습항목이 겹쳐서 blue항목의 점수는 제외 dedupeRed = " + num);
            i4 -= this.dedupeRed;
        }
        Cursor rawQuery10 = readableDatabase.rawQuery("SELECT * FROM todoList WHERE third IN ('취업지원과(취업지원팀) 취업상담(3,4학년)') and done == 1", (String[]) null);
        if (rawQuery10 != null) {
            i4 += (rawQuery10.getCount() - 1 < 0 ? 0 : rawQuery10.getCount() - 1) * 20;
            if (rawQuery10.getCount() > 1) {
                Log.d("333", "취업지원과 상담 누적 2회  must_check += 1");
                i3++;
            }
        }
        int CheckMustchoiceType = CheckMustchoiceType();
        String num2 = Integer.toString(i3);
        Log.d("checkmust", "mustcheck의 값은 = " + num2);
        int i8 = i4 + CheckMustchoiceType;
        String num3 = Integer.toString(i8);
        String num4 = Integer.toString(CheckMustchoiceType);
        Log.d("checkchoice", "choicePoint의 값은 = " + num4);
        Log.d("checkpoint", "mustpoint+choicepoint 의 값은 = " + num3);
        if (i3 >= 8 && CheckMustchoiceType >= 400) {
            Log.d("successRed", "redbelt 체크성공했다!!");
            return true;
        } else if (i8 - 200 < 500) {
            return false;
        } else {
            Log.d("successRed", "redbelt 체크성공했다!!");
            return true;
        }
    }

    public int CheckMustchoiceType() {
        Cursor rawQuery = new todoDBHelper(this).getReadableDatabase().rawQuery("SELECT * FROM todoList WHERE done = 1 AND type = 1;", (String[]) null);
        rawQuery.moveToFirst();
        int i = 0;
        while (rawQuery.moveToNext()) {
            String string = rawQuery.getString(rawQuery.getColumnIndex("third"));
            int i2 = rawQuery.getInt(rawQuery.getColumnIndex("done"));
            rawQuery.getInt(rawQuery.getColumnIndex("point"));
            if ("취업희망 직무역량 강화 교육 참가(직UP프로그램 등)(1~2h)".equals(string) && i2 == 1) {
                i += 10;
            }
            if ("취업희망 직무역량 강화 교육 참가(직UP프로그램 등)(3~7h)".equals(string) && i2 == 1) {
                i += 20;
            }
            if ("취업희망 직무역량 강화 교육 참가(직UP프로그램 등)(8~12h)".equals(string) && i2 == 1) {
                i += 30;
            }
            if ("취업희망 직무역량 강화 교육 참가(직UP프로그램 등)(13이상)".equals(string) && i2 == 1) {
                i += 40;
            }
            if ("기업의 달인되기 프로그램 참가".equals(string) && i2 == 1) {
                i += 50;
            }
            if ("전공 관련 자격증(전공자격인정원 포함 신청)".equals(string) && i2 == 1) {
                i += 40;
            }
            if ("취업지원과 운영 취업교과목 이수(취업실전1,2,3, 경력개발과 취업전략)".equals(string) && i2 == 1) {
                i += 50;
            }
            if ("공기업/공공기관연계 학점과정(유니온, 빛가람 등),~1학점".equals(string) && i2 == 1) {
                i += 20;
            }
            if ("공기업/공공기관연계 학점과정(유니온, 빛가람 등),~2학점".equals(string) && i2 == 1) {
                i += 30;
            }
            if ("공기업/공공기관연계 학점과정(유니온, 빛가람 등),~3학점".equals(string) && i2 == 1) {
                i += 40;
            }
            if ("공모전 대회 입상(전국)".equals(string) && i2 == 1) {
                i += 50;
            }
            if ("컴퓨터활용(1급)".equals(string) && i2 == 1) {
                i += 30;
            }
            if ("한국사능력시험 1급".equals(string) && i2 == 1) {
                i += 40;
            }
            if ("한자시험(국가공인) 1급".equals(string) && i2 == 1) {
                i += 40;
            }
            if ("사무자동화산업기사".equals(string) && i2 == 1) {
                i += 30;
            }
        }
        return i;
    }
}
