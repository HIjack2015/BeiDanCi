package cn.jk.beidanci.learnword

import cn.jk.beidanci.data.model.DbWord

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
        if (currentPosition >= words.size) {
            return null
        } else {
            return words.get(currentPosition)
        }

    }

    fun currentUnknown() {
        //TODO
    }

    fun currentKnown() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 移动一个position
     * 并获取一个dbword
     */
    fun next(): DbWord? {
        currentPosition++
        return getCurrentWord()
    }
}