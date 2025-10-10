package com.summer.threemforth;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class todoDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "todoDB";
    private static String DB_PATH = "";
    private static final String TAG = "todoDBHelper";
    private ArrayAdapter<String> mAdapter;
    private Context mContext;

    public todoDBHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        dataBaseCheck();
    }

    private void dataBaseCheck() {
        if (!new File(DB_PATH + DB_NAME).exists()) {
            dbCopy();
            Log.d(TAG, "Database is copied.");
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE todoList (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type INTEGER, first TEXT, second TEXT, third TEXT, point INTEGER, nobelt INTEGER, white INTEGER, yellow INTEGER, blue INTEGER, red INTEGER, black INTEGER, done INTEGER, deadline TEXT, alarm INTEGER);");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS firstTB (_id INTEGER PRIMARY KEY, name TEXT);");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS secondTB (_id INTEGER PRIMARY KEY, name TEXT, first TEXT);");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS thirdTB (_id INTEGER PRIMARY KEY, name TEXT, nobelt INTEGER, white INTEGER, yellow INTEGER, blue INTEGER, red INTEGER, black INTEGER, point INTEGER, second TEXT);");
        Log.d(TAG, "onCreate()");
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todoList (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type INTEGER, first TEXT, second TEXT, third TEXT, point INTEGER, nobelt INTEGER, white INTEGER, yellow INTEGER, blue INTEGER, red INTEGER, black INTEGER, done INTEGER, deadline TEXT, alarm INTEGER);");
        Log.d(TAG, "onOpen() : DB Opening!");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS todoList;");
        onCreate(sQLiteDatabase);
        Log.d(TAG, "onUpgrade() : DB Schema Modified and Excuting onCreate()");
    }

    private void dbCopy() {
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                file.mkdir();
            }
            InputStream open = this.mContext.getAssets().open(DB_NAME);
            FileOutputStream fileOutputStream = new FileOutputStream(DB_PATH + DB_NAME);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    open.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("dbCopy", "IOException 발생함");
        }
    }

    public void fillSpinner(Context context, Spinner spinner, String str, String str2, String str3) {
        Log.d("fillspineer", "fillSpinner 실행 column = " + str2 + "table =" + str + "where=" + str3);
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT " + str2 + " FROM " + str + " " + str3, (String[]) null);
        while (rawQuery.moveToNext()) {
            arrayList.add(rawQuery.getString(rawQuery.getColumnIndex(str2)));
        }
        rawQuery.close();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, 17367048, arrayList);
        this.mAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        spinner.setAdapter(this.mAdapter);
        readableDatabase.close();
    }

    public void setText(Spinner spinner, Context context, String str, String str2, String str3) {
        Log.d("title", "title = " + str);
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT name FROM " + str2 + " " + str3, (String[]) null);
        if (rawQuery.getCount() > 0) {
            while (rawQuery.moveToNext()) {
                arrayList.add(rawQuery.getString(rawQuery.getColumnIndex("name")));
                Log.d("marray", "cursor로 array 추가");
            }
        } else {
            Log.d("getcount", "cursor getcount < 0");
        }
        rawQuery.close();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, 17367048, arrayList);
        this.mAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        spinner.setAdapter(this.mAdapter);
        readableDatabase.close();
        Log.d("getPosition", "getPosition = " + this.mAdapter.getPosition(str) + " table = " + str2);
        spinner.setSelection(this.mAdapter.getPosition(str), false);
    }
}
