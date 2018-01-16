package cn.jk.beidanci.home

import android.content.SharedPreferences
import cn.jk.beidanci.BasePresenterImpl
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.*
import cn.jk.beidanci.learnword.WordList
import cn.jk.beidanci.learnword.WordListHelper
import com.raizlabs.android.dbflow.sql.language.SQLite
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jack on 2018/1/9.
 */
class HomePresenter(private val view: HomeContract.View, val prefs: SharedPreferences) : HomeContract.Presenter, BasePresenterImpl() {


    override fun drawPi() {
        var map: HashMap<WordState, Int> = HashMap()
        val bookId: String? = prefs[Constant.CURRENT_BOOK]

        val stateList = mutableListOf(WordState.unknown, WordState.unlearned, WordState.known, WordState.neverShow)
        var countAll = SQLite.selectCountOf().from(DbWord::class.java).
                where(DbWord_Table.bookId.eq(bookId)).longValue()
        for (state in stateList) {
            var count = SQLite.selectCountOf().from(DbWord::class.java).
                    where(DbWord_Table.bookId.eq(bookId), DbWord_Table.state.eq(state)).longValue()

            map.put(state, count.toInt())

        }

        view.showPi(map, countAll.toInt())
    }

    override fun setLearnWordList() {
        val currentBookId: String? = prefs[Constant.CURRENT_BOOK]
        val today = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val learnPerDay: Int? = prefs[Constant.PLAN_LEARN, Constant.PLAN_LEARN_NO]
        var todayLearnCount = SQLite.selectCountOf().from(LearnRecord::class.java).
                where(LearnRecord_Table.learnTime.eq(today))
                .longValue().toInt()

//        var recordsToLearn = SQLite.select().from(LearnRecord::class.java).
//                where(LearnRecord_Table.learned.eq(false), LearnRecord_Table.learnTime.eq(today))
//                .queryList()

        if (todayLearnCount == 0) { //说明还没有背单词
            val dbWordList = SQLite.select().from(DbWord::class.java).
                    where(DbWord_Table.bookId.eq(currentBookId), DbWord_Table.state.notEq(WordState.neverShow))
                    .limit(learnPerDay!!).queryList()
            WordListHelper.wordList = WordList(dbWordList, Constant.LEARN_MODE)
        } else if (todayLearnCount < learnPerDay!!) {
            val needLearn = learnPerDay - todayLearnCount
            val dbWordList = SQLite.select().from(DbWord::class.java).
                    where(DbWord_Table.bookId.eq(currentBookId), DbWord_Table.state.notEq(WordState.neverShow))
                    .limit(needLearn).queryList()
            WordListHelper.wordList = WordList(dbWordList, Constant.LEARN_MODE, todayLearnCount)
        } else {
            val dbWordList = SQLite.select().from(DbWord::class.java).
                    where(DbWord_Table.bookId.eq(currentBookId), DbWord_Table.state.notEq(WordState.neverShow))
                    .limit(learnPerDay).queryList() //再取出计划学习数.
            view.showMsg(R.string.FINISH_PLAN)
            WordListHelper.wordList = WordList(dbWordList, Constant.EXTRA_LEARN, learnPerDay, learnPerDay)
        }
    }


    override fun stop() {

    }

    override fun start() {
        drawPi()
    }
}