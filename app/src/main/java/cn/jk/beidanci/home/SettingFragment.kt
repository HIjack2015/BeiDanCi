package cn.jk.beidanci.home


import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.annotation.StringRes
import cn.jk.beidanci.R
import cn.jk.beidanci.choosebook.ChooseBookActivity
import cn.jk.beidanci.settings.AboutActivity
import cn.jk.beidanci.settings.AdvanceSettingActivity
import cn.jk.beidanci.settings.ChoosePlanDialog

import org.jetbrains.anko.startActivity

/**
 * Created by jack on 2018/1/14.
 */
class SettingFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting)


        setClickListener(R.string.download_book_pref, Preference.OnPreferenceClickListener {
            startActivity<ChooseBookActivity>()
            false
        })
        startActivityOnClick(R.string.about_app,AboutActivity::class.java)
        startActivityOnClick(R.string.advance_setting,AdvanceSettingActivity::class.java)
        findPreference(activity.getString(R.string.should_learn)).setOnPreferenceClickListener {
            ChoosePlanDialog().show(fragmentManager,"choosePlay")
            false
        }
    }
    fun setClickListener(@StringRes id:Int,listener:Preference.OnPreferenceClickListener) {
        val pref=findPreference(activity.getString(id))
        pref.setOnPreferenceClickListener(listener)
    }
    fun startActivityOnClick(@StringRes id:Int, activityToStart: Class< out Any>) {
        val pref=findPreference(activity.getString(id))

        pref.setOnPreferenceClickListener {

            val intent=Intent(activity,activityToStart)
            startActivity(intent)
            false
        }
    }
}
