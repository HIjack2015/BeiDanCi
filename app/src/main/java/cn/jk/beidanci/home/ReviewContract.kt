package cn.jk.beidanci.home

import cn.jk.beidanci.BasePresenter
import cn.jk.beidanci.BaseView
import com.prolificinteractive.materialcalendarview.CalendarDay

/**
 * Created by jack on 2018/1/14.
 */
interface ReviewContract {
    interface View : BaseView {
        fun showReviewCount(unknown: Int, known: Int, neverShow: Int)
        fun highlightDay(days: List<CalendarDay>)
    }

    interface Presenter : BasePresenter {
        fun setSelectDay(calendarDay: CalendarDay)
        /**
         * 将指定月份的学习记录高亮
         * month 0-11
         */
        fun highLightMonth(year: Int, month: Int)

        fun setReviewList(selectedDate: CalendarDay)

    }
}
