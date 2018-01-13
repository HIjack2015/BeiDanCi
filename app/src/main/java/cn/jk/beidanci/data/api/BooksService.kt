package cn.jk.beidanci.data.api

import cn.jk.beidanci.data.model.BooksResult
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url


/**
 * Created by jack on 2018/1/10.
 */
interface BooksService {
    @GET(Settings.BOOK_LIST)
    fun getBookList(): Observable<BooksResult>

    @GET
    fun downloadBookFile(@Url fileUrl: String?): Observable<ResponseBody>
}