package cn.jk.beidanci.home

import android.os.Bundle
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.R
import cn.jk.beidanci.choosebook.ChooseBookActivity
import cn.jk.beidanci.data.Constant
import org.jetbrains.anko.startActivity

/**
 * app首页,包括 主页,复习,设置三个功能.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val currentBookName: String? = prefs[Constant.CURRENT_BOOK] //getter
        if (currentBookName == null) {
            startActivity<ChooseBookActivity>()
        } else {

        }
    }
}

