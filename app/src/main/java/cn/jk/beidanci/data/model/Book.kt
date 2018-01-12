package cn.jk.beidanci.data.model

/**
 * Created by jack on 2018/1/10.
 * 有道单词书的结构.
 */
data class Book(val cover: String, //封面
                val bookOrigin: BookOrigin,
                val size: Int,
                val wordNum: Int,
                val id: String,
                val title: String,
                val offlinedata: String,
                val cateName: String,
                val version: String
) {
}

data class BookOrigin(
        val originUrl: String,
        val desc: String,
        val originName: String

) {
    override fun toString(): String {
        return desc + originName
    }
}