package cn.jk.beidanci.choosebook

import android.util.Log
import cn.jk.beidanci.data.api.ApiManager
import cn.jk.beidanci.data.model.*
import cn.jk.beidanci.data.source.AppDatabase
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.insert
import com.raizlabs.android.dbflow.kotlinextensions.select
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
    override fun chooseBook(book: Book) {
        view.chooseBook(book.id)
    }


    override fun downloadBook(book: Book) {
        view.showDownLoad()
        Log.i("time", "startDownload")

        ApiManager.booksService.downloadBookFile(book.offlinedata)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    view.setLoadingMsg("下载完成,开始解压数据文件...")
                }
                .observeOn(Schedulers.io())
                .map({
                    dealResponse(it, book)
                    Log.i("time", "downloadSuccess")
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
                val dbWordList= select.from(DbWord::class.java).where(DbWord_Table.head.eq(dbWord.head)).queryList().map { it as DbWord }
                if (dbWordList.size>0) {
                    val existDbWord=dbWordList.get(0)
                    dbWord.unknownCount=existDbWord.unknownCount
                    dbWord.knownCount=existDbWord.knownCount
                    dbWord.easy=existDbWord.easy
                    dbWord.lastLearnTime=existDbWord.lastLearnTime
                    dbWord.rank=existDbWord.rank
                    dbWord.state=existDbWord.state
                }
                wordList.add(dbWord)
            }

        }
        warn("完成构造list")

        view.setLoadingMsg("完成解压和解析数据,正在进行最后一步 录入数据库...")
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
        view.showLoad()
        disposable = ApiManager.booksService.getBookList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {

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
