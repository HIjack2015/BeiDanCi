package cn.jk.beidanci.myword

import android.view.MenuItem
import cn.jk.beidanci.R
import cn.jk.beidanci.wordlist.WordListActivity

class MyWordActivity : WordListActivity() {
    override fun getMenuId(): Int {
        return R.menu.menu_my_word_list
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addWord -> {
                AddWordDialog().show(supportFragmentManager, "addWordDialog")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
