/*
 * Copyright (C) 2018 BRAHMACARIYA FOUNDATION LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.contacts.activities.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.contacts.R;
import com.android.contacts.util.PermissionUtil;


public abstract class BaseActivity extends AppCompatActivity {

    protected abstract String tag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showShortToast(int res) {
        showShortToast(getString(res));
    }

    protected void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void showLongToast(int res) {
        showLongToast(getString(res));
    }

    // Request camera permission
    public void requestCameraScanPermission() {
        PermissionUtil.requestMultiPermissions(this, PermissionUtil.CAMERA_PERMISSIONS, PermissionUtil.CODE_CAMERA_SCAN);
    }

    // Request write external storage
    public void requestExternalStorage() {
        PermissionUtil.requestMultiPermissions(this, PermissionUtil.EXTERNAL_STORAGE_PERMISSIONS, PermissionUtil.CODE_EXTERNAL_STORAGE);
    }

    public void handleCameraScanPermission() {}

    public void handleExternalStoragePermission() {}

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PermissionUtil.CODE_CAMERA_SCAN) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                handleCameraScanPermission();
            } else {
                PermissionUtil.openSettingActivity(this, getString(R.string.tip_camera_permission));
            }
        } else if (requestCode == PermissionUtil.CODE_EXTERNAL_STORAGE) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                handleExternalStoragePermission();
            } else {
                PermissionUtil.openSettingActivity(this, getString(R.string.tip_external_storage_permission));
            }
        }
    }

    // show navigation backup action icon
    protected void showNavBackBtn() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setDisplayShowHomeEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * set toolbar title
     */
    protected void setToolbarTitle(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }
}
