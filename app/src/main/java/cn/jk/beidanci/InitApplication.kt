package cn.jk.beidanci

import android.app.Application
import android.content.Context

import com.raizlabs.android.dbflow.config.FlowManager


/**
 *
 */
class InitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        FlowManager.init(this)
    }

    companion object {

        var context: Context? = null

    }


}
