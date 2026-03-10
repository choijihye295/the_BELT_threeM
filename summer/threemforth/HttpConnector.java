package com.summer.threemforth;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnector extends Thread {
    private OnResultListener listener;

    public interface OnResultListener {
        void onResult(String str);
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.listener = onResultListener;
    }

    public void run() {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://threem-jbnu.koyeb.app/notices/").openConnection();
            if (httpURLConnection != null) {
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("GET");
                if (httpURLConnection.getResponseCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        sb.append(readLine);
                    }
                    bufferedReader.close();
                    OnResultListener onResultListener = this.listener;
                    if (onResultListener != null) {
                        onResultListener.onResult(sb.toString());
                    }
                }
                httpURLConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HttpConnector", "Error: " + e.getMessage());
        }
    }
}
