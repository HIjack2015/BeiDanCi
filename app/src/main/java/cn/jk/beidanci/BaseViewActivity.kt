package cn.jk.beidanci

import android.os.Bundle
import org.jetbrains.anko.design.snackbar

/**
 * Created by jack on 2018/1/13.
 */
abstract open class BaseViewActivity<T : BasePresenter> : BaseActivity(), BaseView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.start()
    }

    protected abstract var mPresenter: T
    override fun showMsg(msg: String) {
        snackbar(window.decorView.rootView, msg)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.stop()
    }
}