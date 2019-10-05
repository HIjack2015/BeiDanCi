package cn.jk.beidanci.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import cn.jk.beidanci.BaseViewFragment
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Config
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.Constant.Companion.grasp_key
import cn.jk.beidanci.data.Constant.Companion.known_key
import cn.jk.beidanci.data.Constant.Companion.unknown_key
import cn.jk.beidanci.data.model.WordState
import cn.jk.beidanci.learnword.LearnWordActivity
import cn.jk.beidanci.wordlist.WordListActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.jetbrains.anko.support.v4.longToast

import org.jetbrains.anko.support.v4.startActivity
import java.util.*
import kotlin.collections.ArrayList


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
        countDownView.onLongClick {
            val encourageSentence = Constant.ENCOURAGE_SENTENCE
            val choosePosition = Random().nextInt(encourageSentence.size)
            val showSentence = encourageSentence[choosePosition]
            longToast(showSentence)
        }
        Timer().schedule(ShowRemainTime(), 0, 1000)

    }

    internal inner class ShowRemainTime : TimerTask() {
        override fun run() {
            if (activity != null) {
                activity!!.runOnUiThread { setRemainTime() }
            }
        }
    }

    private fun setRemainTime() {
        val now = Date()
        val diff = Config.examTime!!.time - now.time
        if (diff < 0) {
            Config.addExamDate()
            return
        }


        val diffSeconds = diff / 1000 % 60
        val diffMinutes = diff / (60 * 1000) % 60
        val diffHours = diff / (60 * 60 * 1000) % 24
        val diffDays = diff / (24 * 60 * 60 * 1000)


        val remainTime = (diffDays.toString() + "天" + String.format(Locale.getDefault(), "%02d", diffHours) + "时"
                + String.format(Locale.getDefault(), "%02d", diffMinutes) + "分" + String.format(Locale.getDefault(), "%02d", diffSeconds) + "秒")
        if (remainTimeTxt == null) {
            return
        }
        remainTimeTxt.text = remainTime
    }


    override fun showPi(map: HashMap<WordState, Int>, countAll: Int) {
        var pieWord: String? = prefs[Constant.PIE_WORD]
        if (pieWord == null) {
            pieWord = activity!!.getString(R.string.DEFAULT_PIE_WORD)
        }
        progressRatePi.centerText = pieWord

        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()
        val stateToColorKeyMap = hashMapOf<WordState, String>(
                WordState.unlearned to Constant.unlearn_key,
                WordState.unknown to unknown_key,
                WordState.known to known_key,
                WordState.neverShow to grasp_key

        )
        val grey4 = ContextCompat.getColor(activity!!, R.color.grey400)
        val grey6 = ContextCompat.getColor(activity!!, R.color.grey600)
        val grey8 = ContextCompat.getColor(activity!!, R.color.grey800)
        val grey9 = ContextCompat.getColor(activity!!, R.color.grey900)
        val defaultMap = hashMapOf(
                Constant.unlearn_key to grey4,
                unknown_key to grey6,
                known_key to grey8,
                grasp_key to grey9
        )
        for ((wordState, count) in map) {
            var fakeCount = 0
            //防止刚开始学习时显示的太丑
            if (count < countAll / 10) {
                fakeCount = count + countAll / 10
            } else {
                fakeCount = count
            }
            val prefKey = stateToColorKeyMap[wordState]
            val defaultColor: Int? = defaultMap[prefKey]
            val color: Int = prefs[prefKey!!, defaultColor]!!
            colors.add(color)
            entries.add(PieEntry(fakeCount.toFloat(), wordState.getDesc(activity!!, wordState) + count))
        }
        val set = PieDataSet(entries, "")
        set.setDrawValues(false)
        set.valueTextSize = 18f

        set.colors = colors
        val data = PieData(set)
        if (!pieWord.isNullOrEmpty() && pieWord.length > 3) {
            progressRatePi.setCenterTextSize(25f)
        } else {
            progressRatePi.setCenterTextSize(40f)
        }
        progressRatePi.description.isEnabled = false
        progressRatePi.legend.isEnabled = false

        progressRatePi.data = data
        progressRatePi.invalidate()

        progressRatePi.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
                val label = (e as PieEntry).label
                val wordType = label.replace("\\d".toRegex(), "")
                mPresenter.setShowWordList(label, WordState.neverShow.getState(activity!!, wordType))
                progressRatePi.highlightValue(h)

                startActivity<WordListActivity>()
            }

            override fun onNothingSelected() {

            }
        })
    }
}