package cn.jk.beidanci.home

import android.content.SharedPreferences
import cn.jk.beidanci.BasePresenterImpl
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.DbWord_Table
import cn.jk.beidanci.data.model.WordState
import com.raizlabs.android.dbflow.sql.language.SQLite

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

    override fun stop() {

    }

    override fun start() {
        drawPi()
    }
}