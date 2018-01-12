package cn.jk.beidanci.choosebook

import cn.jk.beidanci.BaseContract
import cn.jk.beidanci.BasePresenter
import cn.jk.beidanci.BaseView
import cn.jk.beidanci.data.model.BooksResult

/**
 * Created by jack on 2018/1/10.
 */
interface ChooseBookContract : BaseContract {
    interface View : BaseView<Presenter> {
        fun showBookList(bookResult: BooksResult)
        fun showLoad()
        fun hideLoad()
        fun showNetError()

    }

    interface Presenter : BasePresenter {
        override fun stop()
    }

}