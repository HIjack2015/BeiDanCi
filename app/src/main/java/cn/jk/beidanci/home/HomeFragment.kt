package cn.jk.beidanci.home

/**
 * Created by jack on 2018/1/9.
 */
class HomeFragment : HomeContract.View {
    override fun showMsg(msg: String) {

    }

    private lateinit var homePresenter: HomeContract.Presenter

    fun setPresenter(presenter: HomeContract.Presenter) {
        homePresenter = presenter!!
    }
}