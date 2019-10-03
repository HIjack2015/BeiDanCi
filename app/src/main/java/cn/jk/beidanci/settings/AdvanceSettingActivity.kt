package cn.jk.beidanci.settings

import android.os.Bundle
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.R

class AdvanceSettingActivity : BaseActivity() {

    lateinit var fragment:AdvanceSettingFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advance_setting)
        if (savedInstanceState == null) {
            fragment = AdvanceSettingFragment()
            fragmentManager.beginTransaction().replace(R.id.content_frames, fragment).commit()
        } else {
            fragment = fragmentManager.findFragmentById(R.id.content_frames) as AdvanceSettingFragment

        }
        title=("高级设置")
    }
}
