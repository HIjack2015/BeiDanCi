package cn.jk.beidanci.home

import android.content.Context
import cn.jk.beidanci.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

/**
 * Created by jack on 2018/1/18.
 */
class CalendarViewDecorator(val dates: Collection<CalendarDay>, private val context: Context) : DayViewDecorator {


    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(context.resources.getDrawable(R.drawable.star_grey))

    }
}