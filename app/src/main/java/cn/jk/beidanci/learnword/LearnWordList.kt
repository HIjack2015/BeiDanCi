package cn.jk.beidanci.learnword

import cn.jk.beidanci.data.model.DbWord

/**
 * Created by jack on 2018/1/16.
 */
class LearnWordList(title: String, words: List<DbWord>, var learnedCount: Int)
    : WordList(words, title) {
    init {

    }

}