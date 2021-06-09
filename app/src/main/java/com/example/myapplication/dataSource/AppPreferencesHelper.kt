package com.example.myapplication.dataSource

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.utils.Common

class AppPreferencesHelper(context: Context?) {
    private val mPrefs: SharedPreferences
    private val PREF_KEY_CURRENT_USER_INFO: String = "current_user_info"

    fun setCurrentUserInfo(userData: String?) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_INFO, userData).apply()
    }

    fun getCurrentUserInfo(): String? {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_INFO, null)
    }

    init {
        mPrefs = context!!.getSharedPreferences(Common.PFER_NAME, Context.MODE_PRIVATE)
    }
}