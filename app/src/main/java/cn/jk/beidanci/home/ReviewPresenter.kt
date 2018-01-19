package cn.jk.beidanci.home

import android.content.SharedPreferences
import cn.jk.beidanci.BasePresenterImpl
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.LearnRecord
import cn.jk.beidanci.data.model.LearnRecord_Table
import cn.jk.beidanci.data.model.WordState
import cn.jk.beidanci.learnword.ReviewWordList
import cn.jk.beidanci.learnword.WordListHelper
import cn.jk.beidanci.utils.DateUtil
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.rx2.kotlinextensions.list
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import com.raizlabs.android.dbflow.sql.language.SQLite
import java.util.*


/**
 * Created by jack on 2018/1/14.
 */
class ReviewPresenter(val view: ReviewContract.View, val prefs: SharedPreferences) : ReviewContract.Presenter, BasePresenterImpl() {
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

    private fun calendarDayToDbDay(calendarDay: CalendarDay): String {
        val month = "%02d".format(calendarDay.month + 1)
        val dbDay = "${calendarDay.year}-$month-${calendarDay.day}"
        return dbDay
    }

    override fun setSelectDay(calendarDay: CalendarDay) {
        val dbDay = calendarDayToDbDay(calendarDay)
        var todayLearnRecord = SQLite.select().from(LearnRecord::class.java).
                where(LearnRecord_Table.learnTime.eq(dbDay)).queryList()
        var knownCount = 0
        var unknownCount = 0
        var neverShowCount = 0
        todayLearnRecord.forEach {
            when (it.dbWord!!.state) {
                WordState.known -> knownCount++
                WordState.neverShow -> neverShowCount++
                WordState.unknown -> unknownCount++
                else -> {
                }
            }
        }
        view.showReviewCount(unknownCount, knownCount, neverShowCount)

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