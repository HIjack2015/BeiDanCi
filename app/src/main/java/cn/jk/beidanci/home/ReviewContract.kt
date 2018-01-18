package cn.jk.beidanci.home

import cn.jk.beidanci.BasePresenter
import cn.jk.beidanci.BaseView

/**
 * Created by jack on 2018/1/14.
 */
interface ReviewContract {
    interface View : BaseView {
        fun showReviewCount(unknown: Int, known: Int, neverShow: Int)
    }

    interface Presenter : BasePresenter {

    }
}
