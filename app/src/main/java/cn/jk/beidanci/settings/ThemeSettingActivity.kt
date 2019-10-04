package cn.jk.beidanci.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceFragmentCompat
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant.Companion.grasp_key
import cn.jk.beidanci.data.Constant.Companion.known_key
import cn.jk.beidanci.data.Constant.Companion.unknown_key
import cn.jk.beidanci.data.Constant.Companion.unlearn_key
import cn.jk.beidanci.utils.ThemeUtil
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.ColorPalette
import com.afollestad.materialdialogs.color.colorChooser

class ThemeSettingActivity : BaseActivity() {
    lateinit var displaySettingFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_setting)

        if (savedInstanceState == null) {
            displaySettingFragment = SettingsFragment()
            supportFragmentManager.beginTransaction().replace(R.id.content_frames, displaySettingFragment, "displaySettingFragment").commit()
        } else {
            displaySettingFragment = supportFragmentManager.findFragmentById(R.id.content_frames) as SettingsFragment

        }
        supportActionBar!!.title = "主题设置"

    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        }

        internal lateinit var context: ThemeSettingActivity

        lateinit var prefs: SharedPreferences
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.theme_setting)

        }

        override fun onResume() {
            super.onResume()
            this.listView.viewTreeObserver.addOnGlobalLayoutListener {
                prefs = (activity as BaseActivity).prefs
                initPrefs()
            }
        }

        fun initPrefs() {
            context = activity as ThemeSettingActivity
            fun setOnPreferenceClick(preferenceName: String, dealColor: (color: Int) -> Unit) {


                val pref = findPreference(preferenceName) as ColorPickerPreference
                val color = pref.getColor()
                pref.setOnPreferenceClickListener {
                    MaterialDialog(context).show {
                        colorChooser(
                                initialSelection = color,
                                colors = ColorPalette.Primary,
                                subColors = ColorPalette.PrimarySub,
                                allowCustomArgb = true,
                                showAlphaSelector = true
                        ) { dialog, color ->
                            dealColor(color)
                            pref.setColor(color)
                        }
                    }
                    false
                }
            }


            val grey4 = ContextCompat.getColor(activity!!, R.color.grey400)
            val grey6 = ContextCompat.getColor(activity!!, R.color.grey600)
            val grey8 = ContextCompat.getColor(activity!!, R.color.grey800)
            val grey9 = ContextCompat.getColor(activity!!, R.color.grey900)
            val map = hashMapOf(
                    unlearn_key to grey4,
                    unknown_key to grey6,
                    known_key to grey8,
                    grasp_key to grey9
            )
            val piePrefs = listOf<String>(known_key, unlearn_key, unknown_key, grasp_key)
            piePrefs.forEach { prefKey: String ->
                val pref = findPreference(prefKey) as ColorPickerPreference
                pref.setColor(prefs[prefKey, map[prefKey] as Int])
                setOnPreferenceClick(prefKey) { color: Int ->
                    prefs[prefKey] = color
                }
            }

            setOnPreferenceClick("primary_color_key"
            ) { color: Int ->
                ThemeUtil.changePrimaryColorInt(color)
            }

            setOnPreferenceClick("accent_color_key"
            ) { color: Int ->
                ThemeUtil.changeAccentColorInt(color)
            }
        }

        inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
            val editor = this.edit()
            operation(editor)
            editor.apply()
        }

        /**
         * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
         */
        operator fun SharedPreferences.set(key: String, value: Any?) {
            when (value) {
                is String? -> edit({ it.putString(key, value) })
                is Int -> edit({ it.putInt(key, value) })
                is Boolean -> edit({ it.putBoolean(key, value) })
                is Float -> edit({ it.putFloat(key, value) })
                is Long -> edit({ it.putLong(key, value) })
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }


        /**
         * finds value on given key.
         * [T] is the type of value
         * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
         */
        inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T): T {
            return when (T::class) {
                String::class -> getString(key, defaultValue as String) as T
                Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
                Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
                Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
                Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }

    }
}



