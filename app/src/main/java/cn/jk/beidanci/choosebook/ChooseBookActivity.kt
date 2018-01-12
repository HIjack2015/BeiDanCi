package cn.jk.beidanci.choosebook


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cn.jk.beidanci.R
import cn.jk.beidanci.data.model.BooksResult
import kotlinx.android.synthetic.main.activity_choose_book.*
import org.jetbrains.anko.design.snackbar


class ChooseBookActivity : AppCompatActivity(), ChooseBookContract.View {

    private var blfpAdapter: BookListFragmentPagerAdapter = BookListFragmentPagerAdapter(supportFragmentManager)

    private lateinit var presenter: ChooseBookContract.Presenter
    override fun setPresenter(presenter: ChooseBookContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_book)



        presenter = ChooseBookPresenter(this)
        presenter.start()

    }

    override fun showBookList(bookResult: BooksResult) {

        for (cate in bookResult.cates) {
            var fragment = BookListFragment.newInstance(cate)
            blfpAdapter.add(fragment, cate.cateName)

        }

        viewPager.adapter = blfpAdapter
        tabs.setupWithViewPager(viewPager)


    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
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
