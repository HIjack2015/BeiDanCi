package cn.jk.beidanci.wordlist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.InitApplication
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import kotlinx.android.synthetic.main.activity_word_list.*
import org.jetbrains.anko.forEachChild

class WordListActivity : BaseActivity() {
    lateinit var wordListAdapter: WordListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        supportActionBar!!.title = ShowWordListHelper.title
        wordListAdapter = WordListAdapter(ShowWordListHelper.dbWordList, prefs[Constant.SHOW_CHINESE_LIST, true])

        var layoutManager = LinearLayoutManager(InitApplication.context)

        wordListView.setHasFixedSize(true)
        wordListView.setLayoutManager(layoutManager)

        wordListView.adapter = wordListAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_word_list, menu)
        val showChineseChk = menu.findItem(R.id.showChineseChk)
        showChineseChk.isChecked = prefs[Constant.SHOW_CHINESE_LIST, true]
        return true
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
