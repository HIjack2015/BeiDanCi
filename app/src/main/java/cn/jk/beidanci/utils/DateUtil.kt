package cn.jk.beidanci.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jack on 2018/1/16.
 */
class DateUtil {
    companion object {
        fun formateToday(): String {
            val today = SimpleDateFormat("yyyy-MM-dd").format(Date())
            return today
        }
    }
}