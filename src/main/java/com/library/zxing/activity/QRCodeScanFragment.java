package com.library.zxing.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

public class QRCodeScanFragment extends Fragment {
    private final static int SCANNIN_GREQUEST_CODE = 1;

    public void startScanQRCode() {
        if (AndPermission.hasPermission(QRCodeScanFragment.this.getActivity(), Manifest.permission.CAMERA)) {
            // 有权限，直接do anything.
            Intent intent = new Intent();
            intent.setClass(QRCodeScanFragment.this.getActivity(), MipcaActivityCapture.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
        } else {
            // 申请权限。
            AndPermission.with(QRCodeScanFragment.this)
                    .requestCode(100)
                    .permission(Manifest.permission.CAMERA)
                    .send();
        }
    }

    //利用Listener方式回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == 100) {
                Intent intent = new Intent();
                intent.setClass(QRCodeScanFragment.this.getActivity(), MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(QRCodeScanFragment.this, deniedPermissions)) {
                AndPermission.defaultSettingDialog(QRCodeScanFragment.this, 0).show();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //返回内容
                    String url = bundle.getString("result");
                    if (url != null) {
                        Intent intent = new Intent(QRCodeScanFragment.this.getActivity(), WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

}
