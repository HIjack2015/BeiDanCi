package cn.jk.beidanci.home

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.BaseViewFragment
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.WordState
import cn.jk.beidanci.learnword.LearnWordActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity


/**
 * Created by jack on 2018/1/9.
 */
class HomeFragment : BaseViewFragment<HomeContract.Presenter>(), HomeContract.View {
    override lateinit var mPresenter: HomeContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = HomePresenter(this, prefs)
    }

    override fun onStart() {
        super.onStart()
        startLearnBtn.setOnClickListener {
            mPresenter.setLearnWordList()
            startActivity<LearnWordActivity>()
        }

    }


    override fun showPi(map: HashMap<WordState, Int>, countAll: Int) {
        var pieWord: String? = prefs[Constant.PIE_WORD]
        if (pieWord == null) {
            pieWord = activity.getString(R.string.DEFAULT_PIE_WORD)
        }
        progressRatePi.centerText = pieWord

        val entries = ArrayList<PieEntry>()
        for ((wordState, count) in map) {
            var fakeCount = 0
            //防止刚开始学习时显示的太丑
            if (count < countAll / 10) {
                fakeCount = count + countAll / 10
            } else {
                fakeCount = count
            }
            entries.add(PieEntry(fakeCount.toFloat(), wordState.getDesc(activity, wordState) + count))
        }
        val set = PieDataSet(entries, "")
        set.setDrawValues(false)
        set.valueTextSize = 18f
        val grey4 = ContextCompat.getColor(activity, R.color.grey400)
        val grey6 = ContextCompat.getColor(activity, R.color.grey600)
        val grey8 = ContextCompat.getColor(activity, R.color.grey800)
        val grey9 = ContextCompat.getColor(activity, R.color.grey900)
        set.setColors(grey4, grey6, grey8, grey9)
        val data = PieData(set)
        progressRatePi.setCenterTextSize(40f)
        progressRatePi.description.isEnabled = false
        progressRatePi.legend.isEnabled = false

        progressRatePi.data = data
        progressRatePi.invalidate()
    }
}