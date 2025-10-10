package com.summer.threemforth;

import android.app.Activity;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class PermissionSupport {
    private final int MULTIPLE_PERMISSIONS = 1023;
    private Activity activity;
    private Context context;
    private List<String> permissionList;
    private String[] permissions = {"android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"};

    public PermissionSupport(Activity activity2, Context context2) {
        this.activity = activity2;
        this.context = context2;
    }

    public boolean checkPermission() {
        this.permissionList = new ArrayList();
        for (String str : this.permissions) {
            if (ContextCompat.checkSelfPermission(this.context, str) != 0) {
                this.permissionList.add(str);
            }
        }
        return this.permissionList.isEmpty();
    }

    public void requestPermission() {
        Activity activity2 = this.activity;
        List<String> list = this.permissionList;
        ActivityCompat.requestPermissions(activity2, (String[]) list.toArray(new String[list.size()]), 1023);
    }

    public boolean permissionResult(int i, String[] strArr, int[] iArr) {
        if (i != 1023 || iArr.length <= 0) {
            return true;
        }
        for (int i2 : iArr) {
            if (i2 == -1) {
                return false;
            }
        }
        return true;
    }
}
