package  cn.jk.beidanci.settings


import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import cn.jk.beidanci.R
import com.afollestad.aesthetic.Aesthetic

/**
 * Created by Administrator on 2017/7/2.
 */

class ColorPickerPreference(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : Preference(context, attrs, defStyleAttr) {

    lateinit var imageView: ImageView

    constructor(context: Context) : this(context, null, 0) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        init(context, attrs)
    }

    init {
        init(context, attrs)

    }

    private fun init(context: Context, attrs: AttributeSet?) {
        layoutResource = R.layout.ate_preference_custom
        widgetLayoutResource = R.layout.ate_preference_widget
        isPersistent = false

    }

    fun setColor(color: Int) {
        imageView.setBackgroundColor(color)
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        val key = key
        val context = context
        val primaryKey = context.getString(R.string.primary_color_key)
        val accentKey = context.getString(R.string.accent_color_key)
        val textPrimaryKey = context.getString(R.string.text_primary_color_key)
        val textSecondKey = context.getString(R.string.text_second_color_key)
        imageView = holder.findViewById(R.id.circle) as ImageView
        var color = Aesthetic.get().colorPrimary()
        when (key) {
            primaryKey -> color = Aesthetic.get().colorPrimary()
            accentKey -> color = Aesthetic.get().colorAccent()
            textPrimaryKey -> color = Aesthetic.get().textColorPrimary()
            textSecondKey -> color = Aesthetic.get().textColorSecondary()
        }
        color.take(1).subscribe { integer -> imageView.setBackgroundColor(integer!!) }
    }

    fun getColor(): Int {

        return (imageView.background as ColorDrawable).color
    }

}