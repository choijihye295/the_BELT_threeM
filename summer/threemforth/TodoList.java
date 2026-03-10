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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class TodoList extends AppCompatActivity {
    static int total_point;
    SimpleCursorAdapter adapter;
    FloatingActionButton addTodo;
    LinearLayout announce;
    DrawerLayout drawerLayout;
    LinearLayout exit;
    LinearLayout home;
    LinearLayout list;
    ImageView menu;
    todoDBHelper myHelper;
    LinearLayout settings;
    SQLiteDatabase sqlDB;
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<TodoList> cls = TodoList.class;
        }

        public void onActivityResult(ActivityResult activityResult) {
            if (activityResult.getResultCode() == -1) {
                Bundle bundleExtra = activityResult.getData().getBundleExtra("itemEdit");
                int i = bundleExtra.getInt("cmd");
                ArrayList<Integer> integerArrayList = bundleExtra.getIntegerArrayList("item_belts");
                TodoList todoList = TodoList.this;
                todoList.sqlDB = todoList.myHelper.getWritableDatabase();
                if (i == 0) {
                    SQLiteDatabase sQLiteDatabase = TodoList.this.sqlDB;
                    sQLiteDatabase.execSQL("UPDATE todoList SET name = '" + bundleExtra.getString("item_name") + "', type = " + bundleExtra.getInt("item_type") + ", first = '" + bundleExtra.getString("item_first") + "', second = '" + bundleExtra.getString("item_second") + "', third = '" + bundleExtra.getString("item_third") + "', point = " + bundleExtra.getInt("item_point") + ", nobelt = " + integerArrayList.get(0) + ", white = " + integerArrayList.get(1) + ", yellow = " + integerArrayList.get(2) + ", blue = " + integerArrayList.get(3) + ", red = " + integerArrayList.get(4) + ", black = " + integerArrayList.get(5) + ", deadline = '" + bundleExtra.getString("item_deadline") + "', alarm = " + bundleExtra.getInt("item_alarm") + " WHERE _id = " + bundleExtra.getInt("item_id") + ";");
                } else if (i == 1) {
                    SQLiteDatabase sQLiteDatabase2 = TodoList.this.sqlDB;
                    sQLiteDatabase2.execSQL("INSERT INTO todoList (name, type, first, second, third, point, nobelt, white, yellow, blue, red, black, deadline, alarm) VALUES ('" + bundleExtra.getString("item_name") + "', " + bundleExtra.getInt("item_type") + ", '" + bundleExtra.getString("item_first") + "', '" + bundleExtra.getString("item_second") + "', '" + bundleExtra.getString("item_third") + "', " + bundleExtra.getInt("item_point") + ", " + integerArrayList.get(0) + ", " + integerArrayList.get(1) + ", " + integerArrayList.get(2) + ", " + integerArrayList.get(3) + ", " + integerArrayList.get(4) + ", " + integerArrayList.get(5) + ", '" + bundleExtra.getString("item_deadline") + "', " + bundleExtra.getInt("item_alarm") + ") ;");
                } else {
                    Toast.makeText(TodoList.this.getApplicationContext(), "취소되었습니다.", 1).show();
                }
                TodoList.this.adapter.changeCursor(TodoList.this.sqlDB.rawQuery("SELECT * FROM todoList ORDER BY deadline;", (String[]) null));
                TodoList.this.adapter.notifyDataSetChanged();
            }
        }
    });
    TextView title;
    ListView todoList;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.todo_list);
        total_point = PreferenceManager.getInt(this, "point");
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.menu = (ImageView) findViewById(R.id.menu);
        this.home = (LinearLayout) findViewById(R.id.home);
        this.settings = (LinearLayout) findViewById(R.id.setting);
        this.list = (LinearLayout) findViewById(R.id.list);
        this.announce = (LinearLayout) findViewById(R.id.announce);
        this.exit = (LinearLayout) findViewById(R.id.exit);
        TextView textView = (TextView) findViewById(R.id.title);
        this.title = textView;
        textView.setText(R.string.todo_list);
        this.todoList = (ListView) findViewById(R.id.todoList);
        this.myHelper = new todoDBHelper(this);
        this.addTodo = (FloatingActionButton) findViewById(R.id.addTodo);
        SQLiteDatabase readableDatabase = this.myHelper.getReadableDatabase();
        this.sqlDB = readableDatabase;
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.todo_item, readableDatabase.rawQuery("SELECT * FROM todoList ORDER BY deadline;", (String[]) null), new String[]{"name", "deadline", "done"}, new int[]{R.id.name, R.id.deadline, R.id.done}, 0);
        this.adapter = simpleCursorAdapter;
        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int i) {
                boolean z = false;
                if (!(view instanceof CheckBox)) {
                    return false;
                }
                CheckBox checkBox = (CheckBox) view;
                checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Cursor cursor = (Cursor) TodoList.this.adapter.getItem(TodoList.this.todoList.getPositionForView(view));
                        String string = cursor.getString(0);
                        int i = cursor.getInt(6);
                        if (((CheckBox) view).isChecked()) {
                            TodoList.this.sqlDB = TodoList.this.myHelper.getWritableDatabase();
                            SQLiteDatabase sQLiteDatabase = TodoList.this.sqlDB;
                            sQLiteDatabase.execSQL("UPDATE todoList SET done = 1 WHERE _id = " + string + ";");
                            TodoList.this.sqlDB.close();
                            TodoList.total_point = TodoList.total_point + i;
                            Log.d("point", "total_point :" + TodoList.total_point);
                            PreferenceManager.setInt(TodoList.this, "point", TodoList.total_point);
                        } else {
                            TodoList.this.sqlDB = TodoList.this.myHelper.getWritableDatabase();
                            SQLiteDatabase sQLiteDatabase2 = TodoList.this.sqlDB;
                            sQLiteDatabase2.execSQL("UPDATE todoList SET done = 0 WHERE _id = " + string + ";");
                            TodoList.this.sqlDB.close();
                            TodoList.total_point = TodoList.total_point - i;
                            Log.d("point", "total_point :" + TodoList.total_point);
                            PreferenceManager.setInt(TodoList.this, "point", TodoList.total_point);
                        }
                        TodoList.this.sqlDB = TodoList.this.myHelper.getReadableDatabase();
                        TodoList.this.adapter.changeCursor(TodoList.this.sqlDB.rawQuery("SELECT * FROM todoList ORDER BY deadline;", (String[]) null));
                        TodoList.this.adapter.notifyDataSetChanged();
                        TodoList.this.sqlDB.close();
                    }
                });
                if (cursor.getInt(i) != 0) {
                    z = true;
                }
                checkBox.setChecked(z);
                return true;
            }
        });
        this.todoList.setAdapter(this.adapter);
        this.sqlDB.close();
        this.todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Cursor cursor = (Cursor) TodoList.this.adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putInt("item_id", cursor.getInt(0));
                bundle.putString("item_name", cursor.getString(1));
                bundle.putInt("item_type", cursor.getInt(2));
                bundle.putString("item_first", cursor.getString(3));
                bundle.putString("item_second", cursor.getString(4));
                bundle.putString("item_third", cursor.getString(5));
                bundle.putInt("item_point", cursor.getInt(6));
                ArrayList arrayList = new ArrayList();
                for (int i2 = 7; i2 < 13; i2++) {
                    arrayList.add(Integer.valueOf(cursor.getInt(i2)));
                }
                bundle.putIntegerArrayList("item_belts", arrayList);
                bundle.putInt("item_done", cursor.getInt(13));
                bundle.putString("item_deadline", cursor.getString(14));
                bundle.putInt("item_alarm", cursor.getInt(15));
                Intent intent = new Intent(TodoList.this.getApplicationContext(), EditDialog.class);
                intent.putExtra("item", bundle);
                TodoList.this.startActivityResult.launch(intent);
            }
        });
        this.todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long j) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TodoList.this);
                builder.setTitle("삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Cursor cursor = (Cursor) TodoList.this.adapter.getItem(i);
                        if (cursor.getInt(13) == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TodoList.this);
                            builder.setTitle("완료된 항목");
                            builder.setMessage("완료되지 않았다면 삭제 전 체크를 해제해주세요.");
                            builder.setNegativeButton("취소", (DialogInterface.OnClickListener) null);
                            builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TodoList.this.sqlDB = TodoList.this.myHelper.getWritableDatabase();
                                    SQLiteDatabase sQLiteDatabase = TodoList.this.sqlDB;
                                    sQLiteDatabase.execSQL("DELETE FROM todoList WHERE _id = " + cursor.getInt(0) + ";");
                                    TodoList.this.sqlDB.close();
                                    new AlertDialog.Builder(TodoList.this).setMessage("삭제되었습니다.").setPositiveButton("확인", (DialogInterface.OnClickListener) null).show();
                                    TodoList.this.sqlDB = TodoList.this.myHelper.getReadableDatabase();
                                    TodoList.this.adapter.changeCursor(TodoList.this.sqlDB.rawQuery("SELECT * FROM todoList ORDER BY deadline;", (String[]) null));
                                    TodoList.this.adapter.notifyDataSetChanged();
                                    TodoList.this.sqlDB.close();
                                }
                            });
                            builder.show();
                            return;
                        }
                        TodoList.this.sqlDB = TodoList.this.myHelper.getWritableDatabase();
                        SQLiteDatabase sQLiteDatabase = TodoList.this.sqlDB;
                        sQLiteDatabase.execSQL("DELETE FROM todoList WHERE _id = " + cursor.getInt(0) + ";");
                        TodoList.this.sqlDB.close();
                        new AlertDialog.Builder(TodoList.this).setMessage("삭제되었습니다.").setPositiveButton("확인", (DialogInterface.OnClickListener) null).show();
                        TodoList.this.sqlDB = TodoList.this.myHelper.getReadableDatabase();
                        TodoList.this.adapter.changeCursor(TodoList.this.sqlDB.rawQuery("SELECT * FROM todoList ORDER BY deadline;", (String[]) null));
                        TodoList.this.adapter.notifyDataSetChanged();
                        TodoList.this.sqlDB.close();
                    }
                });
                builder.setNegativeButton("취소", (DialogInterface.OnClickListener) null);
                builder.show();
                return true;
            }
        });
        this.addTodo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TodoList.this.startActivityResult.launch(new Intent(TodoList.this.getApplicationContext(), EditDialog.class));
            }
        });
        this.menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TodoList.openDrawer(TodoList.this.drawerLayout);
            }
        });
        this.home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TodoList.redirectActivity(TodoList.this, MainActivity.class);
            }
        });
        this.settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TodoList.redirectActivity(TodoList.this, SettingsActivity.class);
            }
        });
        this.announce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TodoList.redirectActivity(TodoList.this, NoticeList.class);
            }
        });
        this.list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TodoList.this.recreate();
            }
        });
        this.exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(TodoList.this).setTitle("종료하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TodoList.this.moveTaskToBack(true);
                        TodoList.this.finishAndRemoveTask();
                        System.exit(0);
                    }
                }).setNegativeButton("취소", (DialogInterface.OnClickListener) null).show();
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
