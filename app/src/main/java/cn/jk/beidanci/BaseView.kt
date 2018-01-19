package cn.jk.beidanci

import android.support.annotation.StringRes

interface BaseView {
    fun showMsg(message: String)
    fun showMsg(@StringRes message: Int)

}
