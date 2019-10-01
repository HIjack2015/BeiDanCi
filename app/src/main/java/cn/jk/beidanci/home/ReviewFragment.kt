package cn.jk.beidanci.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.BaseViewFragment
import cn.jk.beidanci.R
import cn.jk.beidanci.data.model.WordState
import cn.jk.beidanci.learnword.LearnWordActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.fragment_review.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

/**
 * Created by jack on 2018/1/14.
 */
class ReviewFragment : BaseViewFragment<ReviewContract.Presenter>(), ReviewContract.View {
    override fun highlightDay(days: List<CalendarDay>) {
        calendarView.addDecorator(CalendarViewDecorator(days, activity!!))
    }

    override fun onStart() {
        super.onStart()
        //仅在手动点击后触发.
        calendarView.setOnDateChangedListener { materialCalendarView: MaterialCalendarView, calendarDay: CalendarDay, b: Boolean ->
            if (b) mPresenter.setSelectDay(calendarDay)
        }
        calendarView.setOnMonthChangedListener({ materialCalendarView: MaterialCalendarView, calendarDay: CalendarDay ->
            val month = calendarDay.month
            val year = calendarDay.year
            mPresenter.highLightMonth(year, month)
        })
        calendarView.setSelectedDate(Date())
        startReviewBtn.setOnClickListener {
            mPresenter.setReviewList(calendarView.selectedDate)
            startActivity<LearnWordActivity>()
        }
        unknownLyt.setOnClickListener {
            mPresenter.setReviewList(calendarView.selectedDate, WordState.unknown)
            startActivity<LearnWordActivity>()
        }
        knowLyt.setOnClickListener {
            mPresenter.setReviewList(calendarView.selectedDate, WordState.known)
            startActivity<LearnWordActivity>()
        }
        neverShowLyt.setOnClickListener {
            mPresenter.setReviewList(calendarView.selectedDate, WordState.neverShow)
            startActivity<LearnWordActivity>()
        }
        startReviewBtn.setOnLongClickListener {
            mPresenter.setForgetCurveReviewList(calendarView.selectedDate)
            startActivity<LearnWordActivity>()
            false
        }

    }

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