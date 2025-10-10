package com.summer.threemforth;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;

public class spinnerDBHelper extends SQLiteOpenHelper {
    private ArrayAdapter<String> mAdapter;

    public void setData() {
    }

    public spinnerDBHelper(Context context) {
        super(context, "todoDB", (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS firstTB (_id INTEGER PRIMARY KEY, name TEXT);");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS secondTB (_id INTEGER PRIMARY KEY, name TEXT, first INTEGER);");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS thirdTB (_id INTEGER PRIMARY KEY, name TEXT, nobelt INTEGER, white INTEGER, yellow INTEGER, blue INTEGER, red INTEGER, black INTEGER, point INTEGER, second INTEGER);");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.e("onUpgrade", "onUpgrade 오류 실행됨");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS todoList;");
        onCreate(sQLiteDatabase);
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
        readableDatabase.close();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, 17367048, arrayList);
        this.mAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        spinner.setAdapter(this.mAdapter);
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
        Log.d("getPosition", "getPosition = " + this.mAdapter.getPosition(str) + " table = " + str2);
        spinner.setSelection(this.mAdapter.getPosition(str), false);
        readableDatabase.close();
    }
}
