package cn.jk.beidanci.learnword

import cn.jk.beidanci.data.model.Word

/**
 * Created by jack on 2018/1/15.
 */
abstract class WordList(var words: List<Word>, var title: String) {

    fun isEmpty(): Boolean {
        return words.isEmpty()
    }

    fun getListName(): String {
        return title
    }

    fun getFinishMessage(): String {
        return "完成学习单词列表"
    }

    fun getEmptyMessage(): String {
        return "单词列表为空"
    }
}