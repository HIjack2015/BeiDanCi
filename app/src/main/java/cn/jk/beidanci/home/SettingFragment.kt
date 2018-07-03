package cn.jk.beidanci.home

import android.os.Bundle
import android.preference.PreferenceFragment
import cn.jk.beidanci.R
import cn.jk.beidanci.choosebook.ChooseBookActivity
import org.jetbrains.anko.startActivity

/**
 * Created by jack on 2018/1/14.
 */
class SettingFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting)

        val chooseBookPref = findPreference(activity.getString(R.string.download_book_pref))
        chooseBookPref.setOnPreferenceClickListener {
            startActivity<ChooseBookActivity>()
            false
        }
       
    }
}
