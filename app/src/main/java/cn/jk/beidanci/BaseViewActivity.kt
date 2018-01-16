package cn.jk.beidanci

import kotlinx.android.synthetic.main.activity_choose_book.*
import org.jetbrains.anko.design.snackbar

/**
 * Created by jack on 2018/1/13.
 */
abstract open class BaseViewActivity<T : BasePresenter> : BaseActivity(), BaseView {


    override fun onStart() {
        super.onStart()
        mPresenter.start()
    }

    protected abstract var mPresenter: T
    override fun showMsg(msg: String) {
        snackbar(window.decorView.rootView, msg)
    }

    override fun showMsg(message: Int) {
        snackbar(mainView, message)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.stop()
    }
}