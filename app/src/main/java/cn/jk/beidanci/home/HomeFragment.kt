package cn.jk.beidanci.home

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.R

/**
 * Created by jack on 2018/1/9.
 */
class HomeFragment : Fragment(), HomeContract.View {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun showMsg(msg: Int) {

    }

    override fun showMsg(msg: String) {

    }

    private lateinit var homePresenter: HomeContract.Presenter

    fun setPresenter(presenter: HomeContract.Presenter) {
        homePresenter = presenter!!
    }
}