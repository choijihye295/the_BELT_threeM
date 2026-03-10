package com.summer.threemforth;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditDialog extends AppCompatActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    SwitchCompat alarm_switch;
    CheckBox blackBox;
    CheckBox blueBox;
    Button btn_back;
    Button btn_save;
    Bundle finalItem;
    String firstvalue;
    boolean isFirstSelected;
    Bundle item;
    todoDBHelper myHelper;
    CheckBox redBox;
    /* access modifiers changed from: private */
    public ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new EditDialog$$ExternalSyntheticLambda1(this));
    int searchFilter;
    String secondvalue;
    String thirdvalue;
    SQLiteDatabase todoDB;
    EditText todo_deadline;
    Spinner todo_first;
    EditText todo_name;
    TextView todo_point;
    Spinner todo_second;
    Spinner todo_third;
    Spinner todo_type;
    CheckBox whiteBox;
    CheckBox yellowBox;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.dialog);
        this.myHelper = new todoDBHelper(this);
        this.searchFilter = -1;
        this.isFirstSelected = true;
        this.item = getIntent().getBundleExtra("item");
        this.todo_name = (EditText) findViewById(R.id.todo_name);
        this.todo_deadline = (EditText) findViewById(R.id.todo_deadline);
        this.todo_type = (Spinner) findViewById(R.id.todo_type);
        this.todo_first = (Spinner) findViewById(R.id.todo_first);
        this.todo_second = (Spinner) findViewById(R.id.todo_second);
        this.todo_third = (Spinner) findViewById(R.id.todo_third);
        this.todo_point = (TextView) findViewById(R.id.todo_point);
        this.alarm_switch = (SwitchCompat) findViewById(R.id.alarm_switch);
        this.whiteBox = (CheckBox) findViewById(R.id.cb_white);
        this.yellowBox = (CheckBox) findViewById(R.id.cb_yellow);
        this.blueBox = (CheckBox) findViewById(R.id.cb_blue);
        this.redBox = (CheckBox) findViewById(R.id.cb_red);
        this.blackBox = (CheckBox) findViewById(R.id.cb_black);
        setTypeSpinner();
        setPointSpinner();
        Bundle bundle2 = this.item;
        if (bundle2 != null) {
            this.todo_name.setText(bundle2.getString("item_name"));
            this.todo_deadline.setText(this.item.getString("item_deadline"));
            this.todo_type.setSelection(this.item.getInt("item_type"));
            if (this.item.getInt("item_alarm") == 1) {
                this.alarm_switch.setChecked(true);
            }
            this.firstvalue = this.item.getString("item_first");
            this.secondvalue = this.item.getString("item_second");
            this.thirdvalue = this.item.getString("item_third");
            this.myHelper.setText(this.todo_first, this, this.firstvalue, "firstTB", "");
            todoDBHelper tododbhelper = this.myHelper;
            Spinner spinner = this.todo_second;
            String str = this.secondvalue;
            tododbhelper.setText(spinner, this, str, "secondTB", "WHERE first = '" + this.firstvalue + "'");
            todoDBHelper tododbhelper2 = this.myHelper;
            Spinner spinner2 = this.todo_third;
            String str2 = this.thirdvalue;
            tododbhelper2.setText(spinner2, this, str2, "thirdTB", "WHERE first = '" + this.firstvalue + "' AND second = '" + this.secondvalue + "'");
            ArrayList<Integer> integerArrayList = this.item.getIntegerArrayList("item_belts");
            if (integerArrayList.get(0).intValue() != 1) {
                this.whiteBox.setChecked(integerArrayList.get(1).intValue() == 1);
                this.yellowBox.setChecked(integerArrayList.get(2).intValue() == 1);
                this.blueBox.setChecked(integerArrayList.get(3).intValue() == 1);
                this.redBox.setChecked(integerArrayList.get(4).intValue() == 1);
                this.blackBox.setChecked(integerArrayList.get(5).intValue() == 1);
            }
            this.todo_point.setText(String.format(Locale.KOREA, TimeModel.NUMBER_FORMAT, new Object[]{Integer.valueOf(this.item.getInt("item_point"))}));
            this.item.putInt("cmd", 0);
        } else {
            this.item = new Bundle();
            this.myHelper.setText(this.todo_first, this, "공모전", "firstTB", "");
            this.myHelper.setText(this.todo_second, this, "공모전 및 대회", "secondTB", "WHERE first = '공모전'");
            this.myHelper.setText(this.todo_third, this, "공모전 대회 입상(교내)", "thirdTB", "WHERE first = '공모전' AND second = '공모전 및 대회'");
            setBelt("공모전 대회 입상(교내)", "공모전 및 대회", "공모전");
            this.item.putInt("cmd", 1);
        }
        this.alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z && Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(EditDialog.this, "android.permission.POST_NOTIFICATIONS") != 0) {
                    if (EditDialog.this.shouldShowRequestPermissionRationale("android.permission.POST_NOTIFICATIONS")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditDialog.this);
                        builder.setTitle("권한 요청");
                        builder.setMessage("알림 기능을 사용하기 위해서 권한 허용이 필요합니다.");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditDialog.this.requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS");
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditDialog.this.alarm_switch.setChecked(false);
                            }
                        });
                        builder.show();
                        return;
                    }
                    Log.d("권한 요구", "권한 내놔");
                    EditDialog.this.requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS");
                }
            }
        });
        this.finalItem = this.item;
        Button button = (Button) findViewById(R.id.btn_save);
        this.btn_save = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int i;
                if (EditDialog.this.checkBlank()) {
                    EditDialog.this.finalItem.putString("item_name", EditDialog.this.todo_name.getText().toString());
                    EditDialog.this.finalItem.putString("item_deadline", EditDialog.this.todo_deadline.getText().toString());
                    EditDialog.this.finalItem.putString("item_first", EditDialog.this.firstvalue);
                    EditDialog.this.finalItem.putString("item_second", EditDialog.this.secondvalue);
                    EditDialog.this.finalItem.putString("item_third", EditDialog.this.thirdvalue);
                    EditDialog.this.finalItem.putInt("item_point", Integer.parseInt(EditDialog.this.todo_point.getText().toString()));
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(0, 0);
                    if (EditDialog.this.whiteBox.isChecked()) {
                        arrayList.add(1, 1);
                        i = 1;
                    } else {
                        arrayList.add(1, 0);
                        i = 0;
                    }
                    if (EditDialog.this.yellowBox.isChecked()) {
                        arrayList.add(2, 1);
                        i++;
                    } else {
                        arrayList.add(2, 0);
                    }
                    if (EditDialog.this.blueBox.isChecked()) {
                        arrayList.add(3, 1);
                        i++;
                    } else {
                        arrayList.add(3, 0);
                    }
                    if (EditDialog.this.redBox.isChecked()) {
                        arrayList.add(4, 1);
                        i++;
                    } else {
                        arrayList.add(4, 0);
                    }
                    if (EditDialog.this.blackBox.isChecked()) {
                        arrayList.add(5, 1);
                        i++;
                    } else {
                        arrayList.add(5, 0);
                    }
                    if (i == 0) {
                        arrayList.add(0, 1);
                    }
                    EditDialog.this.finalItem.putIntegerArrayList("item_belts", arrayList);
                    EditDialog.this.finalItem.putInt("item_type", EditDialog.this.todo_type.getSelectedItemPosition());
                    if (EditDialog.this.checkAlarm()) {
                        Intent intent = new Intent(EditDialog.this.getApplicationContext(), TodoList.class);
                        intent.putExtra("itemEdit", EditDialog.this.finalItem);
                        EditDialog.this.setResult(-1, intent);
                        EditDialog.this.finish();
                        return;
                    }
                    new AlertDialog.Builder(EditDialog.this).setTitle("잘못된 알람").setMessage("이미 지난 날짜이거나 잘못된 형식입니다.").setPositiveButton("뒤로", (DialogInterface.OnClickListener) null).show();
                }
            }
        });
        Button button2 = (Button) findViewById(R.id.btn_back);
        this.btn_back = button2;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditDialog.this.finalItem.putInt("cmd", 2);
                EditDialog.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean checkBlank() {
        int i = !EditDialog$$ExternalSyntheticBackport0.m(this.todo_name.getText().toString());
        if (!EditDialog$$ExternalSyntheticBackport0.m(this.todo_deadline.getText().toString())) {
            i++;
        }
        if (i == 2) {
            return true;
        }
        new AlertDialog.Builder(this).setMessage("모든 항목을 채워주세요.").setPositiveButton("뒤로", (DialogInterface.OnClickListener) null).show();
        return false;
    }

    /* access modifiers changed from: private */
    public boolean checkAlarm() {
        PendingIntent pendingIntent;
        if (this.alarm_switch.isChecked()) {
            this.finalItem.putInt("item_alarm", 1);
            return convertDate(this.finalItem.getInt("item_id"));
        }
        this.finalItem.putInt("item_alarm", 0);
        Intent intent = new Intent(getApplicationContext(), MyNotificationReceiver.class);
        intent.putExtra("name", this.todo_name.getText().toString());
        if (Build.VERSION.SDK_INT >= 23) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), this.finalItem.getInt("item_id"), intent, 335544320);
        } else {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), this.finalItem.getInt("item_id"), intent, 268435456);
        }
        if (pendingIntent != null) {
            ((AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(pendingIntent);
        }
        return true;
    }

    private boolean convertDate(int i) {
        String str = this.todo_deadline.getText().toString().trim() + " 00:00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(str);
            Log.d("알림 생성", "변환된 날짜: " + simpleDateFormat.format(parse));
            return scheduleNotification(parse, i);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("알림형식", "잘못된 날짜 형식입니다.");
            return false;
        }
    }

    private boolean scheduleNotification(Date date, int i) {
        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intent intent = new Intent(this, MyNotificationReceiver.class);
        intent.putExtra("name", this.todo_name.getText().toString());
        intent.putExtra("plan", this.todo_deadline.getText().toString());
        if (Build.VERSION.SDK_INT >= 23) {
            pendingIntent = PendingIntent.getBroadcast(this, i, intent, 335544320);
        } else {
            pendingIntent = PendingIntent.getBroadcast(this, i, intent, 268435456);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        if (instance.getTimeInMillis() <= System.currentTimeMillis()) {
            return false;
        }
        alarmManager.set(0, instance.getTimeInMillis(), pendingIntent);
        return true;
    }

    private void setTypeSpinner() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, getResources().getStringArray(R.array.type));
        arrayAdapter.setDropDownViewResource(17367049);
        this.todo_type.setAdapter(arrayAdapter);
        this.todo_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == 0) {
                    EditDialog.this.item.putInt("item_type", 0);
                } else if (i == 1) {
                    EditDialog.this.item.putInt("item_type", 1);
                } else {
                    EditDialog.this.item.putInt("item_type", 2);
                }
            }
        });
    }

    private void setPointSpinner() {
        final todoDBHelper tododbhelper = new todoDBHelper(this);
        this.todoDB = tododbhelper.getWritableDatabase();
        this.todo_first.setPrompt("포인트 항목을 선택하세요");
        this.todo_second.setPrompt("포인트 세부항목을 선택하세요");
        this.todo_third.setPrompt("활동영역을 선택하세요");
        this.todo_first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                EditDialog.this.firstvalue = adapterView.getItemAtPosition(i).toString();
                if (EditDialog.this.isFirstSelected) {
                    EditDialog.this.isFirstSelected = false;
                    Log.d("firstSelected", "firstSelected false로 바뀜");
                } else if (EditDialog.this.searchFilter == 1) {
                    todoDBHelper tododbhelper = tododbhelper;
                    EditDialog editDialog = EditDialog.this;
                    Spinner spinner = editDialog.todo_second;
                    tododbhelper.fillSpinner(editDialog, spinner, "secondTB", "name", "WHERE first = '" + EditDialog.this.firstvalue + "'");
                    Log.d("searchFilter1", "1번째 spinner fillSpinner 실행");
                }
                Log.d("one", "1번째 spinner onItemSelected 실행");
            }
        });
        this.todo_second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                EditDialog.this.secondvalue = adapterView.getItemAtPosition(i).toString();
                if (EditDialog.this.searchFilter == 1) {
                    todoDBHelper tododbhelper = tododbhelper;
                    EditDialog editDialog = EditDialog.this;
                    Spinner spinner = editDialog.todo_third;
                    tododbhelper.fillSpinner(editDialog, spinner, "thirdTB", "name", "WHERE first = '" + EditDialog.this.firstvalue + "' AND second = '" + EditDialog.this.secondvalue + "'");
                    Log.d("searchFilter2", "2번째 spinner fillSpinner 실행");
                }
                Log.d("two", "2번째 spinner onItemSelected 실행 firstvalue = " + EditDialog.this.firstvalue + "secondvalue = " + EditDialog.this.secondvalue);
            }
        });
        this.todo_third.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                Log.d("three", "3번째 spinner onItemSelected 실행");
                EditDialog.this.thirdvalue = adapterView.getItemAtPosition(i).toString();
                if (EditDialog.this.searchFilter == 1) {
                    EditDialog editDialog = EditDialog.this;
                    editDialog.setBelt(editDialog.thirdvalue, EditDialog.this.secondvalue, EditDialog.this.firstvalue);
                    Log.d("searchFilter3", "3번째 spinner setBelt 실행 secondvalue = " + EditDialog.this.secondvalue + " thirdvalue" + EditDialog.this.thirdvalue);
                }
            }
        });
        this.todo_first.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("touch1", "1번째 spinner 클릭");
                EditDialog.this.searchFilter = 1;
                return false;
            }
        });
        this.todo_second.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("touch2", "2번째 spinner 클릭");
                EditDialog.this.searchFilter = 1;
                return false;
            }
        });
        this.todo_third.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("touch3", "3번째 spinner 클릭");
                EditDialog.this.searchFilter = 1;
                return false;
            }
        });
    }

    public void setBelt(String str, String str2, String str3) {
        boolean z = false;
        this.whiteBox.setChecked(false);
        this.yellowBox.setChecked(false);
        this.blueBox.setChecked(false);
        this.redBox.setChecked(false);
        this.blackBox.setChecked(false);
        Log.d("setBelt", "setBelt 실행");
        SQLiteDatabase readableDatabase = this.myHelper.getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM thirdTB WHERE first = '" + str3 + "' AND second = '" + str2 + "' AND name = '" + str + "'", (String[]) null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() > 0) {
            Log.d("setBeltcursor", "setBelt cursor실행");
            this.todo_point.setText(Integer.toString(rawQuery.getInt(rawQuery.getColumnIndex("point"))));
            if (rawQuery.getInt(rawQuery.getColumnIndex("nobelt")) == 0) {
                int i = rawQuery.getInt(rawQuery.getColumnIndex("white"));
                int i2 = rawQuery.getInt(rawQuery.getColumnIndex("yellow"));
                int i3 = rawQuery.getInt(rawQuery.getColumnIndex("blue"));
                int i4 = rawQuery.getInt(rawQuery.getColumnIndex("red"));
                int i5 = rawQuery.getInt(rawQuery.getColumnIndex("black"));
                this.whiteBox.setChecked(i == 1);
                this.yellowBox.setChecked(i2 == 1);
                this.blueBox.setChecked(i3 == 1);
                this.redBox.setChecked(i4 == 1);
                CheckBox checkBox = this.blackBox;
                if (i5 == 1) {
                    z = true;
                }
                checkBox.setChecked(z);
            }
        }
        readableDatabase.close();
        rawQuery.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-summer-threemforth-EditDialog  reason: not valid java name */
    public /* synthetic */ void m88lambda$new$0$comsummerthreemforthEditDialog(Boolean bool) {
        if (bool.booleanValue()) {
            Log.d("권한 요구 결과", "권한 있음");
            return;
        }
        Log.d("권한 요구 결과", "권한 없음");
        new AlertDialog.Builder(this).setTitle("권한 거부").setMessage("알람 기능을 사용할 수 없습니다.").setPositiveButton("확인", (DialogInterface.OnClickListener) null).show();
        this.alarm_switch.setChecked(false);
    }
}
