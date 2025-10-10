package com.summer.threemforth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout announce;
    DrawerLayout drawerLayout;
    LinearLayout exit;
    /* access modifiers changed from: private */
    public TextView handleTextView;
    LinearLayout home;
    LinearLayout list;
    ImageView menu;
    private PDFView pdfView;
    LinearLayout settings;
    TextView title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_settings);
        TextView textView = (TextView) findViewById(R.id.title);
        this.title = textView;
        textView.setText("큰사람 프로젝트 메뉴얼");
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.menu = (ImageView) findViewById(R.id.menu);
        this.home = (LinearLayout) findViewById(R.id.home);
        this.settings = (LinearLayout) findViewById(R.id.setting);
        this.list = (LinearLayout) findViewById(R.id.list);
        this.announce = (LinearLayout) findViewById(R.id.announce);
        this.exit = (LinearLayout) findViewById(R.id.exit);
        this.menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.openDrawer(SettingsActivity.this.drawerLayout);
            }
        });
        this.home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.redirectActivity(SettingsActivity.this, MainActivity.class);
            }
        });
        this.settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.recreate();
            }
        });
        this.announce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.redirectActivity(SettingsActivity.this, NoticeList.class);
            }
        });
        this.list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.redirectActivity(SettingsActivity.this, TodoList.class);
            }
        });
        this.exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(SettingsActivity.this).setTitle("종료하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SettingsActivity.this.moveTaskToBack(true);
                        SettingsActivity.this.finishAndRemoveTask();
                        System.exit(0);
                    }
                }).setNegativeButton("취소", (DialogInterface.OnClickListener) null).show();
            }
        });
        PDFView pDFView = (PDFView) findViewById(R.id.pdfshow);
        this.pdfView = pDFView;
        pDFView.fromAsset("manual1.pdf").defaultPage(0).scrollHandle(new ScrollHandle() {
            public void destroyLayout() {
            }

            public void hide() {
            }

            public void hideDelayed() {
            }

            public void setScroll(float f) {
            }

            public boolean shown() {
                return true;
            }

            public void setupLayout(PDFView pDFView) {
                SettingsActivity.this.handleTextView = new TextView(pDFView.getContext());
                SettingsActivity.this.handleTextView.setTextColor(Color.rgb(111, 114, 255));
                SettingsActivity.this.handleTextView.setGravity(17);
                SettingsActivity.this.handleTextView.setTextSize(2, 20.0f);
                pDFView.addView(SettingsActivity.this.handleTextView);
            }

            public void setPageNum(int i) {
                if (SettingsActivity.this.handleTextView != null) {
                    SettingsActivity.this.handleTextView.setText(String.valueOf(i));
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) SettingsActivity.this.handleTextView.getLayoutParams();
                    layoutParams.leftMargin = dpToPx(10);
                    SettingsActivity.this.handleTextView.setLayoutParams(layoutParams);
                }
            }

            private int dpToPx(int i) {
                return Math.round(((float) i) * SettingsActivity.this.getResources().getDisplayMetrics().density);
            }

            public void show() {
                SettingsActivity.this.handleTextView.setVisibility(0);
            }
        }).spacing(10).load();
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
}
