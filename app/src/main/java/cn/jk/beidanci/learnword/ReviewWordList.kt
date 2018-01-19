package cn.jk.beidanci.learnword

import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.LearnRecord
import com.raizlabs.android.dbflow.kotlinextensions.update

/**
 * Created by jack on 2018/1/16.
 */
class ReviewWordList(val learnRecords: List<LearnRecord>, title: String, beforeCount: Int = 0, //从哪个位置开始学习.
                     totalCount: Int = learnRecords.size + beforeCount)
    : WordList(learnRecords.map { it.dbWord as DbWord }, title, beforeCount, totalCount) {
    override fun currentKnown() {
        super.currentKnown()
        setCurrentReviewed()
    }

    private fun setCurrentReviewed() {
        val currentRecord = learnRecords.get(currentPosition)
        currentRecord.reviewed = true
        currentRecord.update()
    }

    override fun currentUnknown() {
        super.currentUnknown()
        setCurrentReviewed()
    }
}