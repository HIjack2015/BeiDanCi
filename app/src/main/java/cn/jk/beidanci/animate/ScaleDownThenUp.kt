package cn.jk.beidanci.animate

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import cn.jk.beidanci.InitApplication.Companion.context
import cn.jk.beidanci.R

object ScaleDownThenUp {
    /**
     * 对icon进行缩放的方法.
     *
     * @param iconImg    要缩放的view
     * @param finalImgId 缩放结束后的img id
     */
    fun animate(iconImg: View, finalImgId: Int) {
        val scaleDownAnimation = AnimationUtils.loadAnimation(context, R.anim.icon_scale_down)
        val scaleUpAnimation = AnimationUtils.loadAnimation(context, R.anim.icon_scale_up)

        scaleDownAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                if (iconImg is ImageView) {
                    iconImg.setImageResource(finalImgId)
                } else {
                    iconImg.setBackgroundResource(finalImgId) //这里不太对？
                }

                iconImg.startAnimation(scaleUpAnimation)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        iconImg.startAnimation(scaleDownAnimation)

    }
}