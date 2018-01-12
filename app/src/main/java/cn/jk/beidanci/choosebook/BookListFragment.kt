package cn.jk.beidanci.choosebook


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.R
import cn.jk.beidanci.data.model.Cate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_book_list.*


/**
 *
 */
class BookListFragment : Fragment() {

    val key = "data"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_book_list, container, false)
    }


    override fun onStart() {
        super.onStart()
        val cate = Gson().fromJson<Cate>(arguments!!.getString(key), Cate::class.java)

        val adapter = BookListAdapter(context, cate.bookList)
        var layoutManager = LinearLayoutManager(context)
        bookListRcy.setHasFixedSize(true)
        bookListRcy.setLayoutManager(layoutManager)
        bookListRcy.adapter = adapter


    }


    companion object {

        fun newInstance(cate: Cate): BookListFragment {
            val fragment = BookListFragment()
            var bundle = Bundle()
            bundle.putString("data", Gson().toJson(cate))
            fragment.arguments = bundle

            return fragment
        }
    }
}
