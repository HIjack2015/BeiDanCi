package cn.jk.beidanci.home

import cn.jk.beidanci.BasePresenter
import cn.jk.beidanci.BaseView

/**
 * Created by jack on 2018/1/9.
 * 首页的约定.
 */
interface HomeContract {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter
}