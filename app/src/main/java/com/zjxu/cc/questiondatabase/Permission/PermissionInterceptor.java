package com.zjxu.cc.questiondatabase.Permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hjq.permissions.IPermissionInterceptor;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.zjxu.cc.questiondatabase.R;

import java.util.ArrayList;
import java.util.List;

public final class PermissionInterceptor implements IPermissionInterceptor {

    @Override
    public void grantedPermissions(Activity activity, List<String> allPermissions, List<String> grantedPermissions,
                                   boolean all, OnPermissionCallback callback) {
        if (callback != null) {
            callback.onGranted(grantedPermissions, all);
        }
    }
    @Override
    public void deniedPermissions(Activity activity, List<String> allPermissions, List<String> deniedPermissions,
                                  boolean never, OnPermissionCallback callback) {
        if (callback != null) {
            callback.onDenied(deniedPermissions, never);
        }
        if (never) {
            showPermissionDialog(activity, deniedPermissions);
            return;
        }
        ToastUtils.show(R.string.common_permission_fail_1);
        if (callback == null) {
            return;
        }
        callback.onDenied(deniedPermissions, never);
    }

    /**
     * 显示授权对话框
     */
    protected void showPermissionDialog(Activity activity, List<String> permissions) {
        // 这里的 Dialog 只是示例，没有用 DialogFragment 来处理 Dialog 生命周期
        new AlertDialog.Builder(activity)
                .setTitle(R.string.common_permission_alert)
                .setCancelable(false)
                .setMessage(getPermissionHint(activity, permissions))
                .setPositiveButton(R.string.common_permission_goto, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        XXPermissions.startPermissionActivity(activity, permissions);
                    }
                })
                .show();
    }
    /**
     * 根据权限获取提示
     */
    protected String getPermissionHint(Context context, List<String> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return context.getString(R.string.common_permission_fail_2);
        }
        List<String> hints = new ArrayList<>();
        for (String permission : permissions) {
            switch (permission) {
                case Permission.READ_EXTERNAL_STORAGE:
                case Permission.WRITE_EXTERNAL_STORAGE:
                case Permission.MANAGE_EXTERNAL_STORAGE: {
                    String hint = context.getString(R.string.common_permission_storage);
                    if (!hints.contains(hint)) {
                        hints.add(hint);
                    }
                    break;
                }
                default:
                    break;
            }
        }
        if (!hints.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String text : hints) {
                if (builder.length() == 0) {
                    builder.append(text);
                } else {
                    builder.append("、")
                            .append(text);
                }
            }
            builder.append(" ");
            return context.getString(R.string.common_permission_fail_3, builder.toString());
        }
        return context.getString(R.string.common_permission_fail_2);
    }
}
