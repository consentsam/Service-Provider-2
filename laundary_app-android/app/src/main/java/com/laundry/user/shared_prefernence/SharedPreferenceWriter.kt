package com.laundry.user.shared_prefernence

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.laundry.user.ui.activity.ui.profile.ProfileFragment

class SharedPreferenceWriter private constructor() {

    fun writeStringValue(key: String, value: String) {
        prefEditor!!.putString(key, value)
        prefEditor!!.commit()
    }

    fun writeStringArrayValue(key: String, value: String) {
        writeStringValue(key, Gson().toJson(value))
    }

    fun writeIntValue(key: String, value: Int) {
        prefEditor!!.putInt(key, value)
        prefEditor!!.commit()
    }


    fun writeBooleanValue(key: String, value: Boolean) {
        prefEditor!!.putBoolean(key, value)
        prefEditor!!.commit()
    }

    fun writeLongValue(key: String, value: Long) {
        prefEditor!!.putLong(key, value)
        prefEditor!!.commit()
    }

    fun clearPreferenceValue(key: String, value: String) {
        prefEditor!!.putString(key, "")
        prefEditor!!.commit()

    }

    fun getString(key: String): String? {

        return mPrefs!!.getString(key, "")

    }

    fun getStringArray(key: String): ArrayList<String>? {
        return Gson().fromJson(getString(key), ArrayList<String>()::class.java)
    }


    fun getInt(key: String): Int {
        return mPrefs!!.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean {
        return mPrefs!!.getBoolean(key, false)
    }

    fun getLong(key: String): Long {
        return mPrefs!!.getLong(key, 0.0.toLong())
    }

    fun clearPreferenceValues() {
        prefEditor!!.clear()
        prefEditor!!.commit()

        writeBooleanValue(SharedPreferenceKey.IS_APP_LAUNCH_EARLIER, true)

    }

    companion object {
        private var sharePref: SharedPreferenceWriter? = null
        private var mPrefs: SharedPreferences? = null
        private var prefEditor: SharedPreferences.Editor? = null

        fun getInstance(context: Context): SharedPreferenceWriter {
            if (null == sharePref) {
                sharePref = SharedPreferenceWriter()
                mPrefs = context.getSharedPreferences("Laundry User", Context.MODE_PRIVATE)
                prefEditor = mPrefs!!.edit()
            }
            return sharePref as SharedPreferenceWriter
        }
    }
}