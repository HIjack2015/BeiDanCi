package cn.jk.beidanci.home

import cn.jk.beidanci.BasePresenter
import cn.jk.beidanci.BaseView
import cn.jk.beidanci.data.model.WordState

/**
 * Created by jack on 2018/1/9.
 * 首页的约定.
 */
interface HomeContract {
    interface View : BaseView {
        fun showPi(map: HashMap<WordState, Int>, countAll: Int)
    }

    interface Presenter : BasePresenter {
        fun drawPi()
        fun setLearnWordList()
        fun setShowWordList(label: String, wordState: WordState)
    }
}