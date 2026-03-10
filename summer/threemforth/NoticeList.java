package com.summer.threemforth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.summer.threemforth.HttpConnector;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NoticeList extends AppCompatActivity {
    NoticeListAdapter adapter;
    LinearLayout announce;
    DrawerLayout drawerLayout;
    LinearLayout exit;
    Spinner filter;
    LinearLayout home;
    boolean isSelected = false;
    LinearLayout list;
    ImageView menu;
    ListView noticelist;
    LinearLayout settings;
    TextView title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.notice_list);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.menu = (ImageView) findViewById(R.id.menu);
        this.home = (LinearLayout) findViewById(R.id.home);
        this.settings = (LinearLayout) findViewById(R.id.setting);
        this.list = (LinearLayout) findViewById(R.id.list);
        this.announce = (LinearLayout) findViewById(R.id.announce);
        this.exit = (LinearLayout) findViewById(R.id.exit);
        this.title = (TextView) findViewById(R.id.title);
        this.noticelist = (ListView) findViewById(R.id.noticelist);
        this.filter = (Spinner) findViewById(R.id.filter);
        this.title.setText(R.string.notice_list);
        this.menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NoticeList.openDrawer(NoticeList.this.drawerLayout);
            }
        });
        this.home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NoticeList.redirectActivity(NoticeList.this, MainActivity.class);
            }
        });
        this.settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NoticeList.redirectActivity(NoticeList.this, SettingsActivity.class);
                NoticeList.this.recreate();
            }
        });
        this.announce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NoticeList.this.recreate();
            }
        });
        this.list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NoticeList.redirectActivity(NoticeList.this, TodoList.class);
            }
        });
        this.exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(NoticeList.this).setTitle("종료하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NoticeList.this.moveTaskToBack(true);
                        NoticeList.this.finishAndRemoveTask();
                        System.exit(0);
                    }
                }).setNegativeButton("취소", (DialogInterface.OnClickListener) null).show();
            }
        });
        this.filter.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.filter_item, getResources().getStringArray(R.array.belts)));
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.start();
        final ArrayList arrayList = new ArrayList();
        httpConnector.setOnResultListener(new HttpConnector.OnResultListener() {
            public void onResult(String str) {
                try {
                    JSONArray jSONArray = new JSONArray(str);
                    int i = 0;
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i2);
                        int i3 = jSONObject.getInt("_id");
                        String string = jSONObject.getString("name");
                        String string2 = jSONObject.getString("link");
                        String string3 = jSONObject.getString("belt");
                        String string4 = jSONObject.getString("date");
                        Log.d("JsonParsing", "_id : " + i3);
                        Log.d("JsonParsing", "name : " + string);
                        Log.d("JsonParsing", "link : " + string2);
                        Log.d("JsonParsing", "belt : " + string3);
                        Log.d("JsonParsing", "date : " + string4);
                        i++;
                        arrayList.add(new NoticeItem(i3, string, string2, string3, string4));
                    }
                    Log.d("JsonParsing", "개수 : " + i);
                    NoticeList.this.runOnUiThread(new Runnable() {
                        public void run() {
                            NoticeList.this.adapter = new NoticeListAdapter(NoticeList.this, arrayList);
                            NoticeList.this.adapter.notifyDataSetChanged();
                            NoticeList.this.noticelist.setAdapter(NoticeList.this.adapter);
                            NoticeList.this.filter.setSelection(0);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        this.noticelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                String link = ((NoticeItem) arrayList.get(i)).getLink();
                Intent intent = new Intent(NoticeList.this.getApplicationContext(), NoticePage.class);
                intent.putExtra("link", link);
                NoticeList.this.startActivity(intent);
            }
        });
        this.filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                Log.d("onitemselected", "onItemSelected 실행됨");
                String obj = adapterView.getItemAtPosition(i).toString();
                Log.d("selectedbelt", "벨트 선택됨 " + obj);
                if (obj.equals("BELT")) {
                    NoticeList.this.adapter = new NoticeListAdapter(NoticeList.this, arrayList);
                    NoticeList.this.adapter.notifyDataSetChanged();
                    NoticeList.this.noticelist.setAdapter(NoticeList.this.adapter);
                    Log.d("beltall", "belt 전체 setadapter");
                    return;
                }
                Log.d("selectedbelt", " " + obj);
                ArrayList arrayList = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    NoticeItem noticeItem = (NoticeItem) it.next();
                    if (obj.equals(noticeItem.getBelt())) {
                        arrayList.add(noticeItem);
                        Log.d("belt filer", "belt.add" + noticeItem.getName() + " belt = " + noticeItem.getBelt());
                    }
                }
                NoticeList.this.adapter = new NoticeListAdapter(NoticeList.this, arrayList);
                NoticeList.this.noticelist.setAdapter(NoticeList.this.adapter);
                NoticeList.this.adapter.notifyDataSetChanged();
            }
        });
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
