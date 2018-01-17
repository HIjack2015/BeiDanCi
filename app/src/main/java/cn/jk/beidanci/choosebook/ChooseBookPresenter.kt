package cn.jk.beidanci.choosebook

import android.util.Log
import cn.jk.beidanci.data.api.ApiManager
import cn.jk.beidanci.data.model.Book
import cn.jk.beidanci.data.model.BooksResult
import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.GeneralErrorHandler
import cn.jk.beidanci.data.source.AppDatabase
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.insert
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import java.io.BufferedInputStream
import java.util.zip.ZipInputStream

/**
 * Created by jack on 2018/1/10.
 */
class ChooseBookPresenter(private val view: ChooseBookContract.View) : ChooseBookContract.Presenter, AnkoLogger {


    override fun downloadBook(book: Book) {
        ApiManager.booksService.downloadBookFile(book.offlinedata)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    view.showDownLoad()
                    Log.i("time", "1")
                }
                .observeOn(Schedulers.io())
                .map({
                    dealResponse(it, book)
                    Log.i("time", "1")
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view.downloadSuccess(book.id)
                }, {
                    view.hideDownLoad()
                    GeneralErrorHandler(view, true).accept(it)
                })

    }


    private fun dealResponse(body: ResponseBody, book: Book) {
        val zis = ZipInputStream(BufferedInputStream(body.byteStream()))
        zis.nextEntry

        var wordList = ArrayList<DbWord>()
        warn("解析返回")
        zis.bufferedReader().use {
            while (true) {
                val aWord = it.readLine()
                if (aWord == null) {
                    break
                }

                val dbWord = DbWord(aWord, book)
                wordList.add(dbWord)
            }

        }
        warn("完成构造list")
        FlowManager.getDatabase(AppDatabase::class.java).executeTransaction(FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(DbWord::class.java))
                .addAll(wordList)
                .build())
        book.insert()
        warn("插入成功")
    }

    override fun reload() {
        view.clear()
        start()
        view.hideReload()
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
                    GeneralErrorHandler(view, true).accept(it)
                })
    }

    override fun stop() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

}
