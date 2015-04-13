/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.android.settings.deviceinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

import com.android.internal.util.Preconditions;
import com.android.settings.R;

public class StorageWizardMigrate extends StorageWizardBase {
    private RadioButton mRadioNow;
    private RadioButton mRadioLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_wizard_migrate);

        Preconditions.checkNotNull(mDisk);

        final String time = DateUtils.formatDuration(0).toString();
        final String size = Formatter.formatFileSize(this, 0);

        setHeaderText(R.string.storage_wizard_migrate_title, mDisk.getDescription());
        setBodyText(R.string.storage_wizard_migrate_body, mDisk.getDescription(), time, size);

        mRadioNow = (RadioButton) findViewById(R.id.storage_wizard_migrate_now);
        mRadioLater = (RadioButton) findViewById(R.id.storage_wizard_migrate_later);

        mRadioNow.setOnCheckedChangeListener(mRadioListener);
        mRadioLater.setOnCheckedChangeListener(mRadioListener);

        mRadioNow.setChecked(true);
    }

    private final OnCheckedChangeListener mRadioListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == mRadioNow) {
                    mRadioLater.setChecked(false);
                } else if (buttonView == mRadioLater) {
                    mRadioNow.setChecked(false);
                }
            }
        }
    };

    @Override
    public void onNavigateNext() {
        if (mRadioNow.isChecked()) {
            final Intent intent = new Intent(this, StorageWizardMigrateConfirm.class);
            intent.putExtra(EXTRA_DISK_ID, mDisk.id);
            startActivity(intent);
        } else if (mRadioLater.isChecked()) {
            final Intent intent = new Intent(this, StorageWizardReady.class);
            intent.putExtra(EXTRA_DISK_ID, mDisk.id);
            startActivity(intent);
        }
    }
}
