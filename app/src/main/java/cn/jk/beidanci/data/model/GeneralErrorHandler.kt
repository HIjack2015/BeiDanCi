package cn.jk.beidanci.data.model

import android.support.annotation.StringRes
import android.text.TextUtils
import cn.jk.beidanci.BaseView
import cn.jk.beidanci.R
import io.reactivex.functions.Consumer
import retrofit2.HttpException
import java.lang.ref.WeakReference
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by jack on 2018/1/14.
 */
class GeneralErrorHandler(view: BaseView? = null,
                          private val mShowError: Boolean = true,
                          val onFailure: () -> Unit = {})
    : Consumer<Throwable> {
    override fun accept(throwable: Throwable?) {

        var errorBody: ErrorBody? = null
        if (isNetworkError(throwable)) {

            showMessage(R.string.internet_connection_unavailable)
        } else if (throwable is HttpException) {
            errorBody = ErrorBody.parseError(throwable.response())
            if (errorBody != null) {
                handleError(errorBody)
            }
        }
        onFailure
    }

    private val mViewReference = WeakReference<BaseView>(view)


    private fun isNetworkError(throwable: Throwable?): Boolean {
        return throwable is SocketException ||
                throwable is UnknownHostException ||
                throwable is SocketTimeoutException
    }

    private fun handleError(errorBody: ErrorBody) {
        if (errorBody.code != ErrorBody.UNKNOWN_ERROR) {
            showErrorIfRequired(errorBody.message)
        }
    }

    private fun showErrorIfRequired(@StringRes strResId: Int) {
        if (mShowError) {
            mViewReference.get()?.showMsg(strResId)
        }
    }

    private fun showErrorIfRequired(error: String) {
        if (mShowError && !TextUtils.isEmpty(error)) {
            mViewReference.get()?.showMsg(error)
        }
    }

    private fun showMessage(@StringRes strResId: Int) {
        mViewReference.get()?.showMsg(strResId)
    }
}