package cn.jk.beidanci.choosebook

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.GlideApp
import cn.jk.beidanci.R
import cn.jk.beidanci.data.model.Book
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.layout_book_item.view.*

/**
 * Created by jack on 2018/1/11.
 * 展示单词书列表的adapter
 */
class BookListAdapter(val bookList: List<Book>) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        holder.setValue(book)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_book_item, parent, false)

        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class ViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {

        fun setValue(book: Book) {
            with(containerView) {
                nameTxt.text = book.title
                originTxt.text = book.bookOrigin.toString()
                wordCountTxt.text = context.getText(R.string.word_count).toString() + book.wordNum.toString()
                GlideApp.with(context).load(book.cover).diskCacheStrategy(DiskCacheStrategy.ALL).into(book_cover)
                var context2 = context
                if (context2 is ChooseBookActivity) {
                    this.setOnClickListener {
                        context2.getPresenter().downloadBook(book)
                    }
                }
            }
        }

    }
}