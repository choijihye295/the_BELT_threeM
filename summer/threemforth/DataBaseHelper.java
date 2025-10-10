package com.summer.threemforth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "todoDB";
    private static String DB_PATH = "";
    private static final String TAG = "DataBaseHelper";
    private Context mContext;
    private SQLiteDatabase mDataBase;

    public DataBaseHelper(Context context) {
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

    public synchronized void close() {
        SQLiteDatabase sQLiteDatabase = this.mDataBase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        super.close();
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.d(TAG, "onCreate()");
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        Log.d(TAG, "onOpen() : DB Opening!");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
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
}
