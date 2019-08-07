package cn.jk.beidanci.wordlist

import cn.jk.beidanci.data.model.DbWord

/**
 * Created by jack on 2018/1/20.
 * 存放需要传递的word list对象
 */

object ShowWordListHelper {
    fun useDefault(title: String, dbWords: List<DbWord>) {
        ShowWordListHelper.title = title
        dbWordList = dbWords as MutableList<DbWord>
        showCollectIcon = true
        showDeleteIcon = true
    }

    var title = ""
    lateinit var dbWordList: MutableList<DbWord>
    var showDeleteIcon = true
    var showCollectIcon = true
}
