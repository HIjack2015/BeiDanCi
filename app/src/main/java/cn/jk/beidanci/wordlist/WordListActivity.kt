package cn.jk.beidanci.wordlist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.InitApplication
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import kotlinx.android.synthetic.main.activity_word_list.*
import org.jetbrains.anko.forEachChild

open class WordListActivity : BaseActivity() {
    lateinit var wordListAdapter: WordListAdapter
    var wordType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        supportActionBar!!.title = ShowWordListHelper.title
        wordType = ShowWordListHelper.title.replace("\\d".toRegex(), "")
        wordListAdapter = WordListAdapter(ShowWordListHelper.dbWordList, this, prefs[Constant.SHOW_CHINESE_LIST, true])

        var layoutManager = LinearLayoutManager(InitApplication.context)

        wordListView.setHasFixedSize(true)
        wordListView.layoutManager = layoutManager

        wordListView.adapter = wordListAdapter

        if (ShowWordListHelper.dbWordList.size == 0) {
            emptyView.visibility = View.VISIBLE
        } else {
            emptyView.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_word_list, menu)
        val showChineseChk = menu.findItem(R.id.showChineseChk)
        showChineseChk.isChecked = prefs[Constant.SHOW_CHINESE_LIST, true]
        return true
    }

    fun refreshTitle() {
        val size = ShowWordListHelper.dbWordList.size
        supportActionBar!!.title = wordType + size
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.showChineseChk -> {
                item.isChecked = !item.isChecked
                prefs[Constant.SHOW_CHINESE_LIST] = item.isChecked
                wordListAdapter.showChinese = item.isChecked
                wordListView.forEachChild {
                    val holder = wordListView.getChildViewHolder(it) as WordListAdapter.ViewHolder
                    holder.setChineseVisibility(item.isChecked)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
