package com.example.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

/**
 * Created by pedro on 01/09/2016.
 */
public class BackupAgent extends BackupAgentHelper {

    public static final String PREFS_BACKUP_KEY = "prefs";

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesBackupHelper sharedPreferencesBackupHelper = new SharedPreferencesBackupHelper(this, getPackageName() + "_preferences");
        addHelper(PREFS_BACKUP_KEY, sharedPreferencesBackupHelper);
    }

}
