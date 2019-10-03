package cn.jk.beidanci

import androidx.annotation.StringRes

interface BaseView {
    fun showMsg(message: String)
    fun showMsg(@StringRes message: Int)

}
