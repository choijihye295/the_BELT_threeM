package com.summer.threemforth;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class NoticePage extends AppCompatActivity {
    FloatingActionButton addtodo;
    FloatingActionButton buttons;
    private boolean buttons_flag;
    SQLiteDatabase sqlDB;
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<NoticePage> cls = NoticePage.class;
        }

        public void onActivityResult(ActivityResult activityResult) {
            if (activityResult.getResultCode() == -1) {
                Bundle bundleExtra = activityResult.getData().getBundleExtra("itemEdit");
                int i = bundleExtra.getInt("cmd");
                ArrayList<Integer> integerArrayList = bundleExtra.getIntegerArrayList("item_belts");
                NoticePage noticePage = NoticePage.this;
                noticePage.sqlDB = noticePage.todoHelper.getWritableDatabase();
                if (i == 0) {
                    SQLiteDatabase sQLiteDatabase = NoticePage.this.sqlDB;
                    sQLiteDatabase.execSQL("UPDATE todoList SET name = '" + bundleExtra.getString("item_name") + "', first = '" + bundleExtra.getString("item_first") + "', second = '" + bundleExtra.getString("item_second") + "', third = '" + bundleExtra.getString("item_third") + "', point = " + bundleExtra.getInt("item_point") + ", nobelt = " + integerArrayList.get(0) + ", white = " + integerArrayList.get(1) + ", yellow = " + integerArrayList.get(2) + ", blue = " + integerArrayList.get(3) + ", red = " + integerArrayList.get(4) + ", black = " + integerArrayList.get(5) + ", deadline = '" + bundleExtra.getString("item_deadline") + "', alarm = " + bundleExtra.getInt("item_alarm") + " WHERE _id = " + bundleExtra.getInt("item_id") + ";");
                } else if (i == 1) {
                    SQLiteDatabase sQLiteDatabase2 = NoticePage.this.sqlDB;
                    sQLiteDatabase2.execSQL("INSERT INTO todoList (name, first, second, third, point, nobelt, white, yellow, blue, red, black, deadline, alarm) VALUES ('" + bundleExtra.getString("item_name") + "', '" + bundleExtra.getString("item_first") + "', '" + bundleExtra.getString("item_second") + "', '" + bundleExtra.getString("item_third") + "', " + bundleExtra.getInt("item_point") + ", " + integerArrayList.get(0) + ", " + integerArrayList.get(1) + ", " + integerArrayList.get(2) + ", " + integerArrayList.get(3) + ", " + integerArrayList.get(4) + ", " + integerArrayList.get(5) + ", '" + bundleExtra.getString("item_deadline") + "', " + bundleExtra.getInt("item_alarm") + ") ;");
                } else {
                    Toast.makeText(NoticePage.this.getApplicationContext(), "취소되었습니다.", 1).show();
                }
            }
        }
    });
    todoDBHelper todoHelper;
    FloatingActionButton tolist;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.notice_page);
        this.buttons = (FloatingActionButton) findViewById(R.id.buttons);
        this.buttons_flag = false;
        this.tolist = (FloatingActionButton) findViewById(R.id.tolist);
        this.addtodo = (FloatingActionButton) findViewById(R.id.addtodo);
        this.todoHelper = new todoDBHelper(this);
        String stringExtra = getIntent().getStringExtra("link");
        WebView webView = (WebView) findViewById(R.id.noticepage);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setMixedContentMode(0);
        webView.loadUrl(stringExtra);
        this.buttons.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NoticePage.this.toggleFab();
            }
        });
        this.tolist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NoticePage.this.finish();
            }
        });
        this.addtodo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NoticePage.this.startActivityResult.launch(new Intent(NoticePage.this.getApplicationContext(), EditDialog.class));
            }
        });
    }

    public void toggleFab() {
        if (this.buttons_flag) {
            ObjectAnimator.ofFloat(this.tolist, "translationY", new float[]{0.0f}).start();
            ObjectAnimator.ofFloat(this.addtodo, "translationY", new float[]{0.0f}).start();
        } else {
            ObjectAnimator.ofFloat(this.tolist, "translationY", new float[]{-150.0f}).start();
            ObjectAnimator.ofFloat(this.addtodo, "translationY", new float[]{-300.0f}).start();
        }
        this.buttons_flag = !this.buttons_flag;
    }
}
