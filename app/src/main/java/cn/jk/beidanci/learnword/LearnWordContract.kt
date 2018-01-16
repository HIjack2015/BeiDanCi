package cn.jk.beidanci.learnword

import cn.jk.beidanci.BasePresenter
import cn.jk.beidanci.BaseView
import cn.jk.beidanci.data.model.DbWord

/**
 * Created by jack on 2018/1/16.
 */
interface LearnWordContract {
    interface View : BaseView {
        fun setPercent(percent: Int)
        fun inflateCardView(dbWord: DbWord)

    }

    interface Presenter : BasePresenter {
        fun next()

    }
}