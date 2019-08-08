package cn.jk.beidanci.data.api

import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.api.Settings.YOUDAO_SERVER
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * 初始化各种联网的服务,是个单例.
 */
object ApiManager {

    private val retrofit = Retrofit.Builder()
            .baseUrl(YOUDAO_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    val booksService: BooksService
    val feedbackService: FeedbackService

    init {
        booksService = retrofit.create<BooksService>(BooksService::class.java)
        feedbackService = Retrofit.Builder().baseUrl(Constant.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create<FeedbackService>(FeedbackService::class.java)
    }


}