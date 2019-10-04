package cn.jk.beidanci.home

import android.content.SharedPreferences
import cn.jk.beidanci.BasePresenterImpl
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.*
import cn.jk.beidanci.learnword.ReviewWordList
import cn.jk.beidanci.learnword.WordList
import cn.jk.beidanci.learnword.WordListHelper
import cn.jk.beidanci.utils.DateUtil
import com.orhanobut.logger.Logger
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.raizlabs.android.dbflow.kotlinextensions.and
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.rx2.kotlinextensions.list
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import com.raizlabs.android.dbflow.sql.language.OperatorGroup
import com.raizlabs.android.dbflow.sql.language.SQLite
import java.util.*


/**
 * Created by jack on 2018/1/14.
 */
class ReviewPresenter(val view: ReviewContract.View, val prefs: SharedPreferences) : ReviewContract.Presenter, BasePresenterImpl() {
    override fun setForgetCurveReviewList(selectedDate: CalendarDay) {
        val dbDay = calendarDayToDbDay(selectedDate)

        val now = Date().time
        val duringSecond = arrayOf(longArrayOf(3, 8), longArrayOf(20, 40), longArrayOf((10 * 60).toLong(), (14 * 60).toLong()), longArrayOf((22 * 60).toLong(), (26 * 60).toLong()), longArrayOf((24 * 2 * 60).toLong(), (24 * 3 * 60).toLong()), longArrayOf((24 * 6 * 60).toLong(), (24 * 7 * 60).toLong()), longArrayOf((24 * 14 * 60).toLong(), (24 * 15 * 60).toLong()))
        for (i in duringSecond.indices) {
            for (j in 0 until duringSecond[0].size) {
                duringSecond[i][j] = now - duringSecond[i][j] * 60 * 1000
            }
        }
        var operatorGroup: OperatorGroup = OperatorGroup.clause().and(DbWord_Table.state.notEq(WordState.neverShow))
        var timeGroup: OperatorGroup = OperatorGroup.clause()
        for (i in duringSecond.indices) {
            val beforeWhen = Date(duringSecond[i][0])
            val afterWhen = Date(duringSecond[i][1])

            timeGroup.or(DbWord_Table.lastLearnTime.greaterThan(afterWhen)
                    .and(DbWord_Table.lastLearnTime.lessThan(beforeWhen)))
        }
        operatorGroup.and(timeGroup)
        val wordList = select.from(DbWord::class.java).where(operatorGroup).queryList().map { it as DbWord }
        WordListHelper.wordList = WordList(wordList, Constant.FORGET_CURVE_LEARN_MODE)
    }


    /**
     * 按照当天单词状态进行复习,不更新学习记录表中的记录
     */
    override fun setReviewList(selectedDate: CalendarDay, state: WordState) {
        val dbDay = calendarDayToDbDay(selectedDate)
        val wordList = select.from(LearnRecord::class.java)
                .where(LearnRecord_Table.learnTime.eq(dbDay), LearnRecord_Table.reviewed.eq(false))
                .queryList().map { it.dbWord as DbWord }.filter { it.state == state }
        WordListHelper.wordList = WordList(wordList, Constant.REVIEW_TITLE)
    }

    override fun setReviewList(selectedDate: CalendarDay) {
        val dbDay = calendarDayToDbDay(selectedDate)
        val recordList = select.from(LearnRecord::class.java)
                .where(LearnRecord_Table.learnTime.eq(dbDay), LearnRecord_Table.reviewed.eq(false))
                .queryList()
        val reviewedCount = SQLite.selectCountOf().from(LearnRecord::class.java)
                .where(LearnRecord_Table.learnTime.eq(dbDay), LearnRecord_Table.reviewed.eq(true))
                .longValue().toInt()
        WordListHelper.wordList = ReviewWordList(recordList, Constant.REVIEW_TITLE, reviewedCount)
    }


    override fun highLightMonth(year: Int, month: Int) {
        val firstDayOfMonth = DateUtil.firstDayOfMonth(year, month)
        val lastDayOfMonth = DateUtil.lastDayOfMonth(year, month)


        SQLite.select(LearnRecord_Table.learnTime).distinct().from(LearnRecord::class.java)
                .where(LearnRecord_Table.learnTime.greaterThanOrEq(firstDayOfMonth), LearnRecord_Table.learnTime.lessThanOrEq(lastDayOfMonth))
                .rx().list {
                    val days = it.map {
                        val dateStrArr = it.learnTime.split("-")
                        val year = dateStrArr[0].toInt()
                        val month = dateStrArr[1].toInt() - 1
                        val day = dateStrArr[2].toInt()
                        CalendarDay(year, month, day)
                    }
                    if (days.isNotEmpty()) {
                        view.highlightDay(days)
                    }
                }


    }

    /**
     * 转成2018-06-03的格式
     *
     */
    private fun calendarDayToDbDay(calendarDay: CalendarDay): String {
        val month = "%02d".format(calendarDay.month + 1)
        val day = "%02d".format(calendarDay.day)
        val dbDay = "${calendarDay.year}-$month-$day"
        return dbDay
    }

    override fun setSelectDay(calendarDay: CalendarDay) {

        val dbDay = calendarDayToDbDay(calendarDay)
        select.from(LearnRecord::class.java).where(LearnRecord_Table.learnTime.eq(dbDay))
                .rx()
                .list { todayLearnRecord ->

                    var knownCount = 0
                    var unknownCount = 0
                    var neverShowCount = 0
                    todayLearnRecord.forEach {
                        if (it != null && it.dbWord != null) {
                            when (it.dbWord!!.state) {
                                WordState.known -> knownCount++
                                WordState.neverShow -> neverShowCount++
                                WordState.unknown -> unknownCount++
                                else -> {
                                }
                            }
                        } else {
                            if (it == null) {
                                Logger.wtf("record is null , 这不可能发生!")
                            } else {
                                Logger.wtf("record %s 对应不到word 也很奇怪!", it.id.toString())
                            }
                        }
                    }
                    view.showReviewCount(unknownCount, knownCount, neverShowCount)

                }

    }

    override fun stop() {
    }

    override fun start() {
        val currentBookId: String? = prefs[Constant.CURRENT_BOOK]
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        highLightMonth(year, month)
        setSelectDay(CalendarDay(Date()))

    }


}