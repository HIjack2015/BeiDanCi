package cn.jk.beidanci.choosebook

import android.util.Log
import cn.jk.beidanci.R
import cn.jk.beidanci.data.api.ApiManager
import cn.jk.beidanci.data.model.Book
import cn.jk.beidanci.data.model.BooksResult
import cn.jk.beidanci.data.model.DbWord
import com.raizlabs.android.dbflow.kotlinextensions.insert
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.util.zip.ZipInputStream

/**
 * Created by jack on 2018/1/10.
 */
class ChooseBookPresenter(private val view: ChooseBookContract.View) : ChooseBookContract.Presenter {
    override fun downloadBook(book: Book) {
        ApiManager.booksService.downloadBookFile(book.offlinedata)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    view.showDownLoad()
                    Log.i("time", (System.nanoTime() / 1000000).toString())
                }
                .observeOn(Schedulers.io())
                .map {
                    Log.i("time", (System.nanoTime() / 1000000).toString())
                    dealResponse(it, book)
                    Log.i("time", (System.nanoTime() / 1000000).toString())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe() {
                    view.hideDownLoad()
                    view.showMsg(R.string.downloadSuccess)
                }
        //error handle TODO
    }


    private fun dealResponse(body: ResponseBody, book: Book) {
        val zis = ZipInputStream(BufferedInputStream(body.byteStream()))
        zis.nextEntry
        zis.bufferedReader().use {
            while (true) {
                val aWord = it.readLine()
                if (aWord == null) {
                    break
                }

                val dbWord = DbWord(aWord, book)
                dbWord.insert()
            }
            Log.i("time", (System.nanoTime() / 1000000).toString())
            book.insert()
        }
    }

    private fun insert(word: Book) {

    }


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
