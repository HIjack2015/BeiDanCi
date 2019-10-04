package cn.jk.beidanci.utils

import androidx.annotation.ColorInt
import cn.jk.beidanci.R
import com.afollestad.aesthetic.Aesthetic

class ThemeUtil {

    companion object {
        fun changeTheme(primaryColor: Int? = null, accentColor: Int? = null, isDark: Boolean? = null) {
            var aesthetic = Aesthetic.get()
            if (primaryColor != null) {
                aesthetic = aesthetic.colorPrimary(res = primaryColor).colorStatusBar(res = primaryColor)
            }
            if (accentColor != null) {
                aesthetic = aesthetic.colorAccent(res = accentColor).textColorSecondary(res = accentColor)
            }
            if (isDark != null) {
                aesthetic = aesthetic.isDark(isDark)
            }
            aesthetic.apply()
        }

        fun changePrimaryColorInt(@ColorInt primaryColor: Int) {
            Aesthetic.get().colorPrimary(primaryColor).colorStatusBar(primaryColor).apply()
        }

        fun changeAccentColorInt(@ColorInt accentColor: Int) {

            Aesthetic.config {
                activityTheme(R.style.AppTheme)
                colorAccent(accentColor)
                textColorPrimary(literal = accentColor)
                textColorSecondary(literal = accentColor)
            }
        }

        fun changeToDark() {
            Aesthetic.get().activityTheme(R.style.AppTheme_dark).isDark(true).apply()
        }

        fun changeToLigth() {
            Aesthetic.get().activityTheme(R.style.AppTheme).isDark(false).apply()
        }
    }
}