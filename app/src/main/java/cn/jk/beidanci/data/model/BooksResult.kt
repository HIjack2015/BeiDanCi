package cn.jk.beidanci.data.model

/**
 * Created by jack on 2018/1/10.
 */
data class BooksResult(val code: Int, val reason: String, val cates: List<Cate>)

class Cate(val cateName: String, val bookList: List<Book>)