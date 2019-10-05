package cn.jk.beidanci.choosebook


import android.os.Bundle
import android.view.View
import cn.jk.beidanci.BaseViewActivity
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.BooksResult
import cn.jk.beidanci.home.MainActivity
import kotlinx.android.synthetic.main.activity_choose_book.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class ChooseBookActivity : BaseViewActivity<ChooseBookContract.Presenter>(), ChooseBookContract.View {
    override fun downloadSuccess(bookId: String) {
        hideDownLoad()
        toast(R.string.downloadSuccess)

        chooseBook(bookId)
        finish()
    }

    override fun onBackPressed() {
        val bookId: String = prefs[Constant.CURRENT_BOOK, ""]
        if (bookId.isEmpty()) {
            showMsg("额,你得先选一本单词书后才能进入学习")
        } else {
            super.onBackPressed()
        }
    }

    override fun chooseBook(bookId: String) {
        //TODO 写sharedpreference 明显该由presenter来做,downlodSuccess也不应该接受一个bookId的参数.
        //但是当前真的没什么好办法,先这样吧.
        prefs[Constant.CURRENT_BOOK] = bookId
        startActivity<MainActivity>()
    }

    override fun hideReload() {
        refreshLyt.finishRefresh()
    }

    var downLoadBookDialog: DownloadBookDialog? = null

    override fun clear() {
        blfpAdapter.clear()
        blfpAdapter.notifyDataSetChanged()
    }

    override var mPresenter: ChooseBookContract.Presenter = ChooseBookPresenter(this)


    override fun showDownLoad() {
        downLoadBookDialog!!.show(supportFragmentManager, "downloadBook")


    }

    override fun hideDownLoad() {
        downLoadBookDialog!!.dismiss()

    }

    private var blfpAdapter: BookListFragmentPagerAdapter = BookListFragmentPagerAdapter(supportFragmentManager)


    fun getPresenter(): ChooseBookContract.Presenter {
        return mPresenter
    }

    override fun setLoadingMsg(msg: String) {
        runOnUiThread {
            downLoadBookDialog!!.setMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_book)
        supportActionBar!!.title="选单词书"

        mPresenter.start()
        refreshLyt.setOnRefreshListener {
            mPresenter.reload()
        }
        downLoadBookDialog = DownloadBookDialog()
    }

    override fun showBookList(bookResult: BooksResult) {

        for (cate in bookResult.cates) {
            var fragment = BookListFragment.newInstance(cate)
            blfpAdapter.add(fragment, cate.cateName)

        }

        viewPager.adapter = blfpAdapter
        tabs.setupWithViewPager(viewPager)


    }


    override fun hideLoad() {
        progress_bar.visibility = View.GONE
    }

    override fun showLoad() {
        progress_bar.visibility = View.VISIBLE
    }


}
