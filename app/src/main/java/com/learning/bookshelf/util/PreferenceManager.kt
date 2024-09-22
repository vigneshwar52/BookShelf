package com.learning.bookshelf.util

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREFS_NAME = "USER_PREFS"
    private const val KEY_EMAIL = "EMAIL"
    private const val KEY_PASSWORD_HASH = "PASSWORD_HASH"

    private fun getPrefs(context: Context): SharedPreferences{
        return context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
    }

    fun storeUserCredentials(context: Context,email:String,password:String){
        val editor = getPrefs(context).edit()
        editor.putString(KEY_EMAIL,email)
        editor.putString(KEY_PASSWORD_HASH,password)
        editor.apply()
    }

    fun getUserEmail(context: Context):String?{
        return getPrefs(context).getString(KEY_EMAIL,null)
    }
    fun getPassword(context: Context):String?{
        return getPrefs(context).getString(KEY_PASSWORD_HASH,null)
    }
    fun clearCredentials(context: Context){
        val editor = getPrefs(context).edit()
        editor.clear()
        editor.apply()
    }
}