package cn.jk.beidanci.learnword

import cn.jk.beidanci.data.Constant

/**
 * Created by jack on 2018/1/16.
 */
class WordListHelper {
    companion object {
        var wordList: WordList = WordList(listOf(), Constant.NOT_INIT)
        fun isInitialized(): Boolean {
            return wordList.title != Constant.NOT_INIT
        }
    }
}