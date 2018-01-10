package cn.jk.beidanci.home

/**
 * Created by jack on 2018/1/9.
 */
class HomeFragment : HomeContract.View {
    private lateinit var homePresenter: HomeContract.Presenter

    override fun setPresenter(presenter: HomeContract.Presenter?) {
        homePresenter = presenter!!
    }
}