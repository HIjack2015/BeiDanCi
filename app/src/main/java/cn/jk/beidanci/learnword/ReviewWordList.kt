package cn.jk.beidanci.learnword

import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.LearnRecord
import cn.jk.beidanci.data.model.LearnRecord_Table
import com.raizlabs.android.dbflow.kotlinextensions.update
import com.raizlabs.android.dbflow.sql.language.SQLite

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

    /**
     * 将当前学习记录状态设为已经复习,如果是最后一个单词,则重置当天复习状态
     */
    private fun setCurrentReviewed() {
        val currentRecord = learnRecords.get(currentPosition)
        currentRecord.reviewed = true
        currentRecord.update()

        if (currentPosition == learnRecords.size - 1) {
            resetLearnRecord()
        }
    }

    /**
     *
     * 当完成当天的复习计划后,将当天的已复习状态设为false
     */
    private fun resetLearnRecord() {
        val firstRecord = learnRecords.get(0)
        if (firstRecord != null) {
            val learnTime = firstRecord.learnTime
            SQLite.update(LearnRecord::class.java).
                    set(LearnRecord_Table.reviewed.eq(false)).
                    where(LearnRecord_Table.learnTime.eq(learnTime)).execute()
        }
    }

    override fun currentUnknown() {
        super.currentUnknown()
        setCurrentReviewed()
    }
}