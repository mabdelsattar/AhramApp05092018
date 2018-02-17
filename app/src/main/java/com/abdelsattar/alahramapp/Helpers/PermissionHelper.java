package com.abdelsattar.alahramapp.Helpers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.abdelsattar.alahramapp.Ui.AttachActivity;

/**
 * Created by amiraelsayed on 2/17/2018.
 */

public class PermissionHelper
{

    //;
    public static boolean WaitingCamPermission = false;
    public static boolean WaitingStoragePermission = false;


    public static boolean isCameraPermissionGranted(AttachActivity mainActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (mainActivity.checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                WaitingCamPermission = false;
                Log.v("PPP","Permission is granted");
                return true;
            } else {
                WaitingCamPermission = true;
                Log.v("PPP","Permission is revoked");
                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.CAMERA}, 2001);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("PPP","Permission is granted");
            WaitingCamPermission = false;
            return true;
        }
    }

    public static boolean isStoragePermissionGranted(AttachActivity mainActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (mainActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                WaitingStoragePermission = false;
                Log.v("PPP","Permission is granted");
                return true;
            } else {
                WaitingStoragePermission = true;
                Log.v("PPP","Permission is revoked");
                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2002);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("PPP","Permission is granted");
            WaitingStoragePermission = false;
            return true;
        }
    }



}
