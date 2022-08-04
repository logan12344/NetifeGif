package com.example.netifegif.preference

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context)
{
    private val APP_PREFERENCE = "appPreference"
    private val preferences: SharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)

        var viewTypeList: Boolean
        get() = preferences.getBoolean(APP_PREFERENCE, true)
        set(value) = preferences.edit().putBoolean(APP_PREFERENCE, value).apply()
}