package cn.jk.beidanci.choosebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.GlideApp
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.Book
import cn.jk.beidanci.utils.PreferenceHelper
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.raizlabs.android.dbflow.kotlinextensions.exists
import kotlinx.android.synthetic.main.layout_book_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick


/**
 * Created by jack on 2018/1/11.
 * 展示单词书列表的adapter
 */
class BookListAdapter(val bookList: List<Book>) : androidx.recyclerview.widget.RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
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

    class ViewHolder(val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView) {

        fun setValue(book: Book) {
            with(containerView) {
                //我们并没有图片...
                nameTxt.text = book.title!!.replace("（图片记忆）", "")
                originTxt.text = book.bookOrigin.toString()
                wordCountTxt.text = context.getText(R.string.word_count).toString() + book.wordNum.toString()
                //     downloadBtn.background.setColorFilter(ContextCompat.getColor(context, R.color.accent_material_light), PorterDuff.Mode.MULTIPLY);
                if (book.exists()) {
                    val isCurrentBook=book.id.equals(PreferenceHelper.getString(context,Constant.CURRENT_BOOK,""))
                    if (isCurrentBook) {
                        downloadBtn.setText(R.string.current_choose)
                        downloadBtn.setOnClickListener {
                            (context as ChooseBookActivity).showMsg("你正在背这本书哦~加油~")
                        }
                    } else {
                        downloadBtn.setText(R.string.choose_this)
                        downloadBtn.onClick {
                            (context as ChooseBookActivity).getPresenter().chooseBook(book)
                        }
                    }
                } else {
                    downloadBtn.setOnClickListener {
                        (context as ChooseBookActivity).getPresenter().downloadBook(book)
                    }
                    GlideApp.with(context).load(book.cover).diskCacheStrategy(DiskCacheStrategy.ALL).into(book_cover)
                }
            }
        }

    }
}