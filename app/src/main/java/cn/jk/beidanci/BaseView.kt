package cn.jk.beidanci

import android.support.annotation.StringRes

interface BaseView {
    fun showMsg(msg: String)
    fun showMsg(@StringRes msg: Int)

}
