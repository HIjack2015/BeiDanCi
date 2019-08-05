package cn.jk.beidanci.wordlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.R
import cn.jk.beidanci.data.model.DbWord
import kotlinx.android.synthetic.main.layout_word_brief.view.*

/**
 * Created by jack on 2018/1/20.
 */
class WordListAdapter(var wordList: MutableList<DbWord>, var showChinese: Boolean = true
) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.layout_word_brief, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setValue(wordList[position], showChinese)
    }


    override fun getItemCount(): Int {
        return wordList.size
    }

    class ViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        fun setValue(dbWord: DbWord, showChinese: Boolean) {
            with(containerView) {
                englishTxt.text = dbWord.head
                if (!showChinese) setChineseVisibility(false)
                chineseTxt.text = dbWord.getWordContent().trans.map { it.tranCn + " " }.toString()
                chineseTxt.setOnClickListener {
                    setChineseVisibility(true)
                }
//                if(dbWord) {
//
//                } else {
//
//                }
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
        wordList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, wordList.size)
    }

}