package cn.jk.beidanci.choosebook


import android.os.Bundle
import android.view.View
import cn.jk.beidanci.BaseViewActivity
import cn.jk.beidanci.R
import cn.jk.beidanci.data.model.BooksResult
import kotlinx.android.synthetic.main.activity_choose_book.*
import org.jetbrains.anko.design.snackbar


class ChooseBookActivity : BaseViewActivity<ChooseBookContract.Presenter>(), ChooseBookContract.View {
    override var mPresenter: ChooseBookContract.Presenter = ChooseBookPresenter(this)

    override fun showMsg(message: Int) {
        snackbar(mainView, message)
    }

    override fun showMsg(message: String) {
        snackbar(mainView, message)
    }

    override fun showDownLoad() {
        progress_bar.visibility = View.VISIBLE

    }

    override fun hideDownLoad() {
        progress_bar.visibility = View.GONE

    }

    private var blfpAdapter: BookListFragmentPagerAdapter = BookListFragmentPagerAdapter(supportFragmentManager)


    fun getPresenter(): ChooseBookContract.Presenter {
        return mPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_book)

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


    override fun showNetError() {
        snackbar(mainView, R.string.net_work_error)
        hideLoad()
    }

}
