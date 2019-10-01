package cn.jk.beidanci.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Config
import cn.jk.beidanci.utils.DayUtil
import kotlinx.android.synthetic.main.dialog_plan_learn.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * Created by Administrator on 2017/7/10.
 */

class ChoosePlanDialog : DialogFragment() {

    //未掌握单词数目.
    internal var unGraspCount = 0
    internal var learnPerDayRecord = -1
    internal var needDayRecord = -1
    // Save your custom view at the class level
    lateinit var customView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Simply return the already inflated custom view
        return customView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        learnPerDayTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(learnPerDayStr: CharSequence, start: Int, before: Int, count: Int) {
                if (learnPerDayStr.toString().isEmpty()) {
                    return
                }
                val learnPerDay = Integer.valueOf(learnPerDayStr.toString())
                if (learnPerDay <= 0) {
                    toast("每天学习单词数目不能小于1")
                    return
                }
                if (learnPerDay == learnPerDayRecord) {
                    return
                }
                val needDay = unGraspCount / learnPerDay + 1

                needDayRecord = needDay
                learnPerDayRecord = learnPerDay

                needDayTxt.setText(needDay.toString())
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_MONTH, needDay)
                finishTimeTxt.text = DayUtil.getFormatDate(calendar)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        needDayTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(needDayStr: CharSequence?, start: Int, before: Int, count: Int) {
                if (needDayStr == null || needDayStr.toString().isEmpty()) {
                    return
                }
                val needDay = Integer.valueOf(needDayStr.toString())
                if (needDay == needDayRecord) {
                    return
                }
                if (needDay < 0) {
                    toast("学习天数不能小于0")
                    return
                }

                val learnPerDay = unGraspCount / needDay + 1

                needDayRecord = needDay
                learnPerDayRecord = learnPerDay

                learnPerDayTxt.setText(learnPerDay.toString())
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_MONTH, needDay)
                finishTimeTxt.text = DayUtil.getFormatDate(calendar)
            }
        })
        unGraspCount = 100 //TODO 这里怎么从数据库取出来再看

        val learnPerDay = Config.planShouldLearn
        learnPerDayTxt.setText(learnPerDay.toString())


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(activity)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater
         customView = inflater.inflate(R.layout.dialog_plan_learn, null)
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customView)
        // Add action buttons

        builder.setPositiveButton(R.string.confirm) { dialog, id ->
            if (learnPerDayTxt.text.isNullOrEmpty()) {
                toast("你需要输入每天的掌握单词数才可以哦~")
                return@setPositiveButton
            }
            Config.setPlanShouldLearn(learnPerDayTxt.text.toString().toInt())
            toast("设置成功 你真棒 ~")
        }
                .setNegativeButton(R.string.cancel, { dialog, id -> })



        return builder.create()
    }





}
