package cn.jk.beidanci.wordlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.animate.ScaleDownThenUp
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.WordState
import com.raizlabs.android.dbflow.kotlinextensions.update
import kotlinx.android.synthetic.main.layout_word_brief.view.*
import org.jetbrains.anko.imageResource

import org.jetbrains.anko.sdk27.coroutines.onClick


/**
 * Created by jack on 2018/1/20.
 */
class WordListAdapter(var wordList: MutableList<DbWord>, val context: Context, var showChinese: Boolean = true
) : androidx.recyclerview.widget.RecyclerView.Adapter<WordListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(cn.jk.beidanci.R.layout.layout_word_brief, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setValue(wordList[position], showChinese, position, this)
    }


    override fun getItemCount(): Int {
        return wordList.size
    }

    class ViewHolder(val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView) {
        fun setValue(dbWord: DbWord, showChinese: Boolean, position: Int, wordListAdapter: WordListAdapter) {
            with(containerView) {
                this.onClick {
                    val bundle = Bundle()
                    val intent = Intent(context, WordDetailActivity::class.java)
                    bundle.putSerializable(Constant.DB_WORD, dbWord)
                    intent.putExtras(bundle)
                    context.startActivity(intent)
                }
                englishTxt.text = dbWord.head
                if (!showChinese) setChineseVisibility(false)
                chineseTxt.text = dbWord.getWordContent().trans.map { it.tranCn + " " }.toString()
                chineseTxt.setOnClickListener {
                    setChineseVisibility(true)
                }
                if (dbWord.collect) {
                    collectBtn.imageResource = cn.jk.beidanci.R.drawable.ic_star_deep_blue_24dp
                } else {
                    collectBtn.imageResource = cn.jk.beidanci.R.drawable.ic_star_border_blue_24dp
                }
                collectBtn.onClick {
                    dbWord.collect = !dbWord.collect
                    dbWord.update()
                    if (dbWord.collect) {
                        ScaleDownThenUp.animate(collectBtn, cn.jk.beidanci.R.drawable.ic_star_deep_blue_24dp)
                    } else {
                        ScaleDownThenUp.animate(collectBtn, cn.jk.beidanci.R.drawable.ic_star_border_blue_24dp)
                    }
                }
                if (WordState.neverShow.isNeverShow(dbWord)) {
                    neverShowBtn.setImageResource(cn.jk.beidanci.R.drawable.ic_restore_blue_24dp)
                } else {
                    neverShowBtn.setImageResource(cn.jk.beidanci.R.drawable.ic_delete_border_24dp)
                }
                neverShowBtn.setOnClickListener {
                    wordListAdapter.removeAt(position)
                }
                if (!ShowWordListHelper.showDeleteIcon) {
                    neverShowBtn.visibility = View.GONE
                }
                if (!ShowWordListHelper.showCollectIcon) {
                    collectBtn.visibility = View.GONE
                }
            }
        }

        fun setChineseVisibility(shouldShow: Boolean) {
            if (shouldShow) {
                containerView.chineseTxt.setTextColor(containerView.englishTxt.currentTextColor)
            } else {
                containerView.chineseTxt.setTextColor(0x00000000)
            }
        }
    }

    fun removeAt(position: Int) {
        var dbWord = wordList.get(position)
        if (dbWord.state != WordState.neverShow) {
            dbWord.state = WordState.neverShow
        } else {
            dbWord.state = WordState.known
        }
        dbWord.update()

        wordList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, wordList.size)
        if (context is WordListActivity) {
            context.refreshTitle()
        }
    }

}