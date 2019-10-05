package cn.jk.beidanci.searchword


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.InitApplication.Companion.context
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.DbWord_Table
import cn.jk.beidanci.wordlist.WordListAdapter
import com.raizlabs.android.dbflow.kotlinextensions.select
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search_word.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class SearchWordActivity : BaseActivity() {
    var haveShowChineseTip = false
    lateinit var wordListAdapter: WordListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_word)
        setRecView()

    }


    private fun setRecView() {
        wordListAdapter = WordListAdapter(wordList = ArrayList<DbWord>(), context = context!!)
        var layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        candidateWordView.setHasFixedSize(true)
        candidateWordView.layoutManager = layoutManager

        candidateWordView.adapter = wordListAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.search_word_menu, menu)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        val searchItem = menu.findItem(R.id.searchWordView)
        val wordSearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        setQueryTextLogic(wordSearchView)
        return true
    }

    private fun setQueryTextLogic(wordSearchView: SearchView) {

        wordSearchView.isIconified = false
        wordSearchView.setOnQueryTextFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                finish()
            }
        })
        val queryTextObserver = Observable.create<String> {
            wordSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    startActivity<NetWordActivity>(Constant.ENGLISH to query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        it.onNext(newText)
                    }
                    return true
                }

            })
            it.setCancellable { wordSearchView.setOnQueryTextListener(null) }

        }

        queryTextObserver  // 2
                .observeOn(AndroidSchedulers.mainThread()).filter {

                    val isFineWord = Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z]+$").matcher(it).matches()
                    if (isFineWord || it.isEmpty()) {
                        true
                    } else {
                        if (!haveShowChineseTip) {
                            toast(R.string.ONLY_CHINESE_ENGLISH_SUPPORT)
                            haveShowChineseTip = true
                        }
                        false
                    }
                }
                .observeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                // 3
                .map {
                    getCandidateList(it)
                }
                // 4
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showCandidateList(it)
                }
    }

    fun showCandidateList(wordList: MutableList<DbWord>) {
        if (wordList.size == 0) {
            if (prefs[Constant.searchTipShow, true]) {
                alert(Appcompat, R.string.searchTips) { yesButton { prefs[Constant.searchTipShow] = false } }.show()
            }

        }
        wordListAdapter.wordList = wordList
        wordListAdapter.notifyDataSetChanged()

    }

    private fun getCandidateList(english: String): MutableList<DbWord> {
        val dbWordList = select.from(DbWord::class.java).where(DbWord_Table.bookId.eq(prefs[Constant.CURRENT_BOOK, ""])).and(DbWord_Table.head.like(english + "%")).queryList().map { it as DbWord }
        return dbWordList as MutableList<DbWord>
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
