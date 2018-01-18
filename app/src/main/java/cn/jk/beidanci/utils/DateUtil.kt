package cn.jk.beidanci.utils

import com.prolificinteractive.materialcalendarview.R.id.month
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jack on 2018/1/16.
 */
class DateUtil {
    companion object {
        fun formateToday(): String {
            return formatDay(Date())
        }

        /**
         * @param month 参数从0-11
         */
        fun firstDayOfMonth(year: Int, month: Int): String {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)

            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return formatDay(calendar.time)
        }

        /**
         * @param month 参数从0-11
         */
        fun lastDayOfMonth(year: Int,month: Int): String
        {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return formatDay(calendar.time)
        }

        fun formatDay(date: Date): String {
            return SimpleDateFormat("yyyy-MM-dd").format(date)
        }

    }
}