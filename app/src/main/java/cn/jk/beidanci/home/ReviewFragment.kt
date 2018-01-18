package cn.jk.beidanci.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.BaseViewFragment
import cn.jk.beidanci.R
import kotlinx.android.synthetic.main.fragment_review.*

/**
 * Created by jack on 2018/1/14.
 */
class ReviewFragment : BaseViewFragment<ReviewContract.Presenter>(), ReviewContract.View {
    override fun showReviewCount(unknown: Int, known: Int, neverShow: Int) {
        knowCountTxt.text = known.toString()
        unknownCountTxt.text = unknown.toString()
        neverShowCountTxt.text = neverShow.toString()
    }

    override lateinit var mPresenter: ReviewContract.Presenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_review, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = ReviewPresenter(this, prefs)

    }

}