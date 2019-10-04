package cn.jk.beidanci.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import cn.jk.beidanci.R
import cn.jk.beidanci.utils.SPUtil
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")//TODO 这里有内存泄漏哦。
/**
 * Created by Administrator on 2017/6/20.
 */

object Config {

    private var context: Context? = null

    /**
     * @return 今日剩余需学单词数目
     */
    val todayShouldLearn: Int
        get() {
            val simpleDateFormat = SimpleDateFormat("yyyyMMdd")
            val haveLearnSp = simpleDateFormat.format(Date()) + "learn"
            val haveLearn = SPUtil.get(context!!, haveLearnSp, 0) as Int
            SPUtil.get(context!!, haveLearnSp, 0)
            var todayRemain = planShouldLearn - haveLearn
            if (todayRemain < 0) {
                todayRemain = 0
            }
            return todayRemain
        }

    /**
     * @return 计划今日应学单词总数
     */
    val planShouldLearn: Int
        get() {
            val planLearn = SPUtil.get(context!!, Constant.PLAN_LEARN, 50) as Int
            return Integer.valueOf(planLearn)
        }

    val examTime: Date?
        get() {
            val examTimeStr = SPUtil.get(context!!, Constant.EXAM_DATE, context!!.getString(R.string.defaultTestDate)) as String
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            try {
                return simpleDateFormat.parse(examTimeStr)
            } catch (e: Exception) {
                Log.e("should never go there", e.toString())
                return null
            }

        }


    val pieWord: String
        get() = SPUtil.get(context!!, Constant.PIE_WORD, "进度") as String


    var autoDisplay: Boolean
        get() = SPUtil.get(context!!, Constant.AUTO_DISPLAY, false) as Boolean
        set(autoDisplay) = SPUtil.putAndApply(context!!, Constant.AUTO_DISPLAY, autoDisplay)

    var guid: String
        get() = SPUtil.get(context!!, Constant.GUID, Constant.DEFAULT_GUID) as String
        set(guid) = SPUtil.putAndApply(context!!, Constant.GUID, guid)

    var showChinese: Boolean
        get() = SPUtil.get(context!!, Constant.SHOW_CHINESE_LIST, true) as Boolean
        set(showChinese) = SPUtil.putAndApply(context!!, Constant.SHOW_CHINESE_LIST, showChinese)

    fun setPlanShouldLearn(planShouldLearn: Int) {
        SPUtil.putAndApply(context!!, Constant.PLAN_LEARN, planShouldLearn)
    }

    /**
     * call when init application
     *
     * @param initContext
     */
    fun setContext(initContext: Context) {
        context = initContext.applicationContext
    }

    fun shouldSearchTipShow(): Boolean {
        return SPUtil.get(context!!, Constant.searchTipShow, true) as Boolean
    }

    fun setSearchTipShow(searchTipShow: Boolean) {
        SPUtil.putAndApply(context!!, Constant.searchTipShow, searchTipShow)
    }

    fun coreModeIsOn(): Boolean {
        return SPUtil.get(context!!, context!!.getString(R.string.core_mode), false) as Boolean
    }

    fun easyModeIsOn(): Boolean {
        return SPUtil.get(context!!, context!!.getString(R.string.hide_easy), false) as Boolean
    }

    /**
     *
     * @param examDate calendar
     */
    fun setExamTime(examDate: Calendar) {
        val year = examDate.get(Calendar.YEAR)
        var month = examDate.get(Calendar.MONTH)
        month++//应该是12月,但是calendar month最大是11.
        val day = examDate.get(Calendar.DAY_OF_MONTH)
        val formatExamDate = "$year-$month-$day"
        SPUtil.putAndApply(context!!, Constant.EXAM_DATE, formatExamDate)
    }

    //自动调整开考时间
    fun addExamDate() {
        setExamDate(Calendar.getInstance().get(Calendar.YEAR))
    }

    fun setExamDate(year: Int) {
        var year = year

        val examDate = Calendar.getInstance()
        val month = examDate.getActualMaximum(Calendar.MONTH)
        var day = 29 //考试时间是周六,必然是12月2*的一天.
        examDate.set(year, month, day)
        while (true) {
            val dayOfWeek = examDate.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == Calendar.SATURDAY) {
                break
            } else {
                day--
                examDate.set(Calendar.DAY_OF_MONTH, day)
            }
        }
        //当前是12月并且已经过去今年的考研时间
        if (Calendar.getInstance().after(examDate)) {
            year++
            setExamDate(year)
        } else {
            Config.setExamTime(examDate)
        }

    }
}
