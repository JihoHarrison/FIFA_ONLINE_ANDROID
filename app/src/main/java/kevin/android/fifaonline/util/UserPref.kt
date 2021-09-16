package kevin.android.fifaonline.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/09/16
 * Time: 5:48 오후
 */
object UserPref {

    private lateinit var pref: SharedPreferences
    private const val PREF_NAME = "Pref"

    const val PREF_ACCESS_ID = "accessId"

    @JvmStatic
    fun init(context: Context) {
        pref = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    @JvmStatic
    fun get(key: String, defValue: String): String {
        return pref.getString(key, defValue) ?: defValue
    }

    @JvmStatic
    fun get(key: String, defValue: Int): Int {
        return pref.getInt(key, defValue)
    }

    @JvmStatic
    fun get(key: String, defValue: Long): Long {
        return pref.getLong(key, defValue)
    }

    @JvmStatic
    fun get(key: String, defValue: Boolean): Boolean {
        return pref.getBoolean(key, defValue)
    }

    @JvmStatic
    fun put(key: String, value: String) {
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    @JvmStatic
    fun put(key: String, value: Int) {
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    @JvmStatic
    fun put(key: String, value: Long) {
        val editor = pref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    @JvmStatic
    fun put(key: String, value: Boolean) {
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    @JvmStatic
    fun remove(key: String) {
        val editor = pref.edit()
        editor.remove(key)
        editor.apply()
    }

    @JvmStatic
    fun clear() {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }







}