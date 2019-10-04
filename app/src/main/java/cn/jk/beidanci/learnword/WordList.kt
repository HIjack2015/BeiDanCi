package cn.jk.beidanci.learnword

import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.DbWord_Table
import cn.jk.beidanci.data.model.LearnRecord
import cn.jk.beidanci.data.model.WordState
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.update
import java.util.*

/**
 * Created by jack on 2018/1/15.
 */
open class WordList(var words: List<DbWord>, var title: String,
                    var beforeCount: Int = 0, //从哪个位置开始学习.
                    var totalCount: Int = words.size + beforeCount) {
    var currentPosition: Int = 0
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

    fun getPercent(): Int {
        var percent = (currentPosition + beforeCount) * 100 / totalCount
        return if (percent > 100) 100 else percent
    }

    fun getCurrentWord(): DbWord? {
        if (currentPosition >= words.size || currentPosition < 0) {
            return null
        } else {
            return words.get(currentPosition)
        }

    }

    /**
     * 将当前单词标记为不认识.并更新db
     */
    open fun currentUnknown() {
        val dbWord = getCurrentWord()
        if (dbWord != null) {
            dbWord.state = WordState.unknown
            dbWord.unknownCount += 1
            dbWord.lastLearnTime = Date()
            updateAllStateByThis(dbWord)

        }

    }

    private fun updateAllStateByThis(dbWord: DbWord) {
        val wordList = select.from(DbWord::class.java).where(DbWord_Table.head.eq(dbWord.head)).queryList().map { it as DbWord }
        wordList.forEach {
            val existDbWord = it
            existDbWord.unknownCount = dbWord.unknownCount
            existDbWord.knownCount = dbWord.knownCount
            existDbWord.easy = dbWord.easy
            existDbWord.lastLearnTime = dbWord.lastLearnTime
            existDbWord.rank = dbWord.rank
            existDbWord.state = dbWord.state
            existDbWord.update()
        }
    }

    open fun currentKnown() {
        val dbWord = getCurrentWord()
        if (dbWord != null) {
            dbWord.state = WordState.known
            dbWord.knownCount += 1
            dbWord.lastLearnTime = Date()
            updateAllStateByThis(dbWord)
        }
    }

    /**
     * 移动一个position
     * 并获取一个dbword
     */
    fun next(): DbWord? {
        currentPosition++
        return getCurrentWord()
    }

    fun previous(): DbWord? {
        if (currentPosition == 0) {
            return null
        } else {
            currentPosition--
        }
        return getCurrentWord()
    }

    fun currentNeverShow() {
        val dbWord = getCurrentWord()
        if (dbWord != null) {
            dbWord.state = WordState.neverShow
            dbWord.knownCount += 1
            dbWord.lastLearnTime = Date()
            updateAllStateByThis(dbWord)
            LearnRecord(dbWord = dbWord).save()
        }
    }
}