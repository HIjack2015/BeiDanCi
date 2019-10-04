package cn.jk.beidanci.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by jack on 2018/1/14.
 * 网上找的工具类
 * https://gist.github.com/krupalshah
 */
object PreferenceHelper {

    fun defaultPrefs(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPrefs(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    /**
     * 获取String类型的值
     * @param mContext this
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    fun getString(mContext: Context, key: String, defValue: String): String? {
        return defaultPrefs(mContext).getString(key, defValue)
    }
}