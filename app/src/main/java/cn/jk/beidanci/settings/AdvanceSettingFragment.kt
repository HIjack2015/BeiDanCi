package cn.jk.beidanci.settings


import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.v4.app.Fragment
import android.view.View
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.home.MainActivity
import org.jetbrains.anko.toast

/**
 * A simple [Fragment] subclass.
 */

class AdvanceSettingFragment : PreferenceFragment() {

    internal var context: AdvanceSettingActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.advance_setting)
        context = activity as AdvanceSettingActivity

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pieWordPref = findPreference(Constant.PIE_WORD)
        pieWordPref.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            MainActivity.setNeedRefreshData()
            true
        }


       /** val exportWordPref = findPreference(getString(R.string.export_word))

        exportWordPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            MaterialDialog.Builder(context!!)
                    .title(R.string.please_select_export_type)
                    .items(R.array.word_type)
                    .positiveText(R.string.confirm)
                    .itemsCallbackMultiChoice(null, MaterialDialog.ListCallbackMultiChoice { dialog, which, text ->
                        if (which.size == 0) {
                            toast( "你得至少选一项啊...")
                            return@ListCallbackMultiChoice false
                        }

                        toast( "正在将单词导出为到sdcard/kaoyandanci/word.txt,请稍候.")

                        Thread(Runnable {
                            exportWord(which)
                            context!!.runOnUiThread { toast( "已经导出到sdcard/kaoyandanci/word.txt.") }
                        }).start()
                        true
                    })
                    .show()


            true
        }
        val downloadVoicePackPref = findPreference(getString(R.string.download_voice_pack))
        downloadVoicePackPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (!NetWorkUtil.isOnline(activity)) {
               toast("请先联网")
                return@OnPreferenceClickListener false
            }
            PleaseDonateDialog().show(fragmentManager, "pleaseDonate")
            false
        }

        val backupPref = findPreference(getString(R.string.back_to_server))
//        backupPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
//            if (!NetWorkUtil.isOnline(activity)) {
//                toast("请先联网")
//                return@OnPreferenceClickListener false
//            }
//            MaterialDialog.Builder(context!!)
//                    .title(R.string.please_input_record_name_to_backup).inputType(InputType.TYPE_CLASS_TEXT)
//                    .input(R.string.sure_to_unique, R.string.input_prefill, MaterialDialog.InputCallback { dialog, input -> backUpByName(input.toString(), true) })
//                    .positiveText(R.string.confirm)
//                    .negativeText(R.string.cancel)
//                    .show()
//
//            false
//        }
//        val restorePref = findPreference(getString(R.string.restore_record))
//        restorePref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
//            if (!NetWorkUtil.isOnline(activity)) {
//                toast("请先联网")
//                return@OnPreferenceClickListener false
//            }
//            MaterialDialog.Builder(context!!)
//                    .title(R.string.please_input_record_name_to_restore).inputType(InputType.TYPE_CLASS_TEXT)
//                    .input(R.string.will_clear_local, R.string.input_prefill, MaterialDialog.InputCallback { dialog, input -> restoreByName(input.toString()) })
//                    .positiveText(R.string.confirm)
//                    .negativeText(R.string.cancel)
//                    .show()
//
//            false
//        }
   **/
    }

    private fun exportWord(which: Array<out Int>?) {

    }


    private fun showResultMsg(msg: String) {
        context!!.runOnUiThread {
           // loadingDialog.dismiss()
          toast(msg)
        }


    }









}
