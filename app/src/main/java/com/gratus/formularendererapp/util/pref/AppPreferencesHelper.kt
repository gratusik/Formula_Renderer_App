package com.gratus.formularendererapp.util.pref

import android.content.Context
import android.content.SharedPreferences
import com.gratus.formularendererapp.util.constants.AppConstants.Companion.APP_PREF_NAME
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(context: Context) : AppPrefHelper {
    private var mPrefs: SharedPreferences =
        context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE)
}

