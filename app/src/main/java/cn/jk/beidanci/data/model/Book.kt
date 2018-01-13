package cn.jk.beidanci.data.model

import cn.jk.beidanci.data.source.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

/**
 * Created by jack on 2018/1/10.
 * 有道单词书的结构.
 */
@Table(database = AppDatabase::class)
class Book(var cover: String? = null, //封面
           var bookOrigin: BookOrigin? = null,
           var size: Int = 0,
           @Column
           var wordNum: Int = 0,
           @PrimaryKey
           var id: String = "",
           @Column
           var title: String? = null,
           var offlinedata: String? = null,
           @Column
           var cateName: String? = null,
           var version: String? = null
) {
}

data class BookOrigin(
        var originUrl: String,
        var desc: String,
        var originName: String

) {
    override fun toString(): String {
        return desc + originName
    }
}