package cn.jk.beidanci

import android.app.Application
import android.content.Context

import com.raizlabs.android.dbflow.config.FlowManager
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout.setDefaultRefreshHeaderCreater
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader


/**
 *
 */
class InitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        FlowManager.init(this)

        setDefaultRefreshHeaderCreater(object : DefaultRefreshHeaderCreater {
            override fun createRefreshHeader(context: Context?, layout: RefreshLayout?): RefreshHeader {
                layout!!.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return MaterialHeader(context);
            }

        })

    }

    companion object {

        var context: Context? = null

    }


}
