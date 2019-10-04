package cn.jk.beidanci.utils

import cn.jk.beidanci.R
import com.afollestad.aesthetic.Aesthetic

class ThemeUtil {

    companion object {
        fun changeTheme(primaryColor: Int = R.color.colorPrimary, accentColor: Int = R.color.colorAccent, isDark: Boolean = false) {
            Aesthetic.get().colorPrimary(res = primaryColor).colorAccent(res = accentColor).colorStatusBar(res = primaryColor).isDark(isDark).apply()
        }

        fun changeToDark() {
            Aesthetic.get().activityTheme(R.style.AppTheme_dark).textColorPrimary(res = R.color.text_color_primary_dark).textColorSecondary(res = R.color.text_color_secondary_dark).isDark(true).apply()
        }

        fun changeToLigth() {
            Aesthetic.get().activityTheme(R.style.AppTheme).textColorPrimary(res = R.color.text_color_primary).textColorSecondary(res = R.color.text_color_secondary).isDark(false).apply()
        }
    }
}