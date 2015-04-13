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

import com.android.internal.util.Preconditions;
import com.android.settings.R;

public class StorageWizardFormatConfirm extends StorageWizardBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_wizard_generic);

        Preconditions.checkNotNull(mDisk);

        setHeaderText(R.string.storage_wizard_format_confirm_title);
        setBodyText(R.string.storage_wizard_format_confirm_body,
                mDisk.getDescription());

        // TODO: make this a big red scary button
        getNextButton().setText(R.string.storage_wizard_format_confirm_next);
    }

    @Override
    public void onNavigateNext() {
        final Intent intent = new Intent(this, StorageWizardFormatProgress.class);
        intent.putExtra(EXTRA_DISK_ID, mDisk.id);
        startActivity(intent);
        finishAffinity();
    }
}
