package cn.jk.beidanci.home

import android.content.SharedPreferences
import cn.jk.beidanci.BasePresenterImpl
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.LearnRecord
import cn.jk.beidanci.data.model.LearnRecord_Table
import cn.jk.beidanci.data.model.WordState
import cn.jk.beidanci.utils.DateUtil
import com.raizlabs.android.dbflow.sql.language.SQLite

/**
 * Created by jack on 2018/1/14.
 */
class ReviewPresenter(val view: ReviewContract.View, val prefs: SharedPreferences) : ReviewContract.Presenter, BasePresenterImpl() {
    override fun stop() {
    }

    override fun start() {
        val currentBookId: String? = prefs[Constant.CURRENT_BOOK]
        var todayLearnRecord = SQLite.select().from(LearnRecord::class.java).
                where(LearnRecord_Table.learnTime.eq(DateUtil.formateToday())).queryList()
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

}