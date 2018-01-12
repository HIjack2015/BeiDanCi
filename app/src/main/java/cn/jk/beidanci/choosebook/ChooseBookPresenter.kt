package cn.jk.beidanci.choosebook

import cn.jk.beidanci.data.api.ApiManager
import cn.jk.beidanci.data.model.BooksResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by jack on 2018/1/10.
 */
class ChooseBookPresenter(private val view: ChooseBookContract.View) : ChooseBookContract.Presenter {
    private lateinit var disposable: Disposable
    override fun start() {
        disposable = ApiManager.booksService.getBookList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    view.showLoad()
                }
                .subscribeOn(Schedulers.io())
                .subscribe({ bookResult: BooksResult ->
                    view.hideLoad()
                    view.showBookList(bookResult)
                }, {
                    view.showNetError()
                })
    }

    override fun stop() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

}