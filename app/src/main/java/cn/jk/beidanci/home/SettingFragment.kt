package cn.jk.beidanci.home


import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.recyclerview.widget.RecyclerView
import cn.jk.beidanci.R
import cn.jk.beidanci.choosebook.ChooseBookActivity
import cn.jk.beidanci.settings.AboutActivity
import cn.jk.beidanci.settings.AdvanceSettingActivity
import cn.jk.beidanci.settings.ChoosePlanDialog


/**
 * Created by jack on 2018/1/14.
 */
class SettingFragment : androidx.preference.PreferenceFragmentCompat() {
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting)


        setClickListener(R.string.download_book_pref, Preference.OnPreferenceClickListener {
            startActivity<ChooseBookActivity>()
            false
        })
        startActivityOnClick(R.string.about_app,AboutActivity::class.java)
        startActivityOnClick(R.string.advance_setting,AdvanceSettingActivity::class.java)
        findPreference(activity!!.getString(R.string.should_learn)).setOnPreferenceClickListener {
            ChoosePlanDialog().show(activity!!.supportFragmentManager!!, "choosePlay")
            false
        }
    }
    fun setClickListener(@StringRes id:Int,listener:Preference.OnPreferenceClickListener) {
        val pref = findPreference(activity!!.getString(id))
        pref.onPreferenceClickListener = listener
    }
    fun startActivityOnClick(@StringRes id:Int, activityToStart: Class< out Any>) {
        val pref = findPreference(activity!!.getString(id))

        pref.setOnPreferenceClickListener {

            val intent=Intent(activity,activityToStart)
            startActivity(intent)
            false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = listView
        val itemDecoration = androidx.recyclerview.widget.DividerItemDecoration(context, androidx.recyclerview.widget.RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }
}
