package cn.jk.beidanci.learnword

import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.LearnRecord
import com.raizlabs.android.dbflow.kotlinextensions.save

/**
 * Created by jack on 2018/1/16.
 */
class LearnWordList(words: List<DbWord>, title: String, beforeCount: Int = 0, //从哪个位置开始学习.
                    totalCount: Int = words.size + beforeCount)
    : WordList(words, title, beforeCount, totalCount) {
    override fun currentKnown() {
        super.currentKnown()
        val currentWord = getCurrentWord()
        LearnRecord(dbWord = currentWord).save()
    }

    override fun currentUnknown() {
        super.currentUnknown()
        val currentWord = getCurrentWord()
        LearnRecord(dbWord = currentWord).save()

    }
}