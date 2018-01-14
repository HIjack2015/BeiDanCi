package cn.jk.beidanci

/**
 * 用来表示Presenter 和 view的各种方法.
 * Created by jack on 2018/1/10.
 */
interface BaseContract {
    interface View : BaseView
    interface Presenter : BasePresenter {
    }
}