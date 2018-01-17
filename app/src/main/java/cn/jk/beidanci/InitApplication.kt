package cn.jk.beidanci

import android.app.Application
import android.content.Context
import android.net.Uri
import com.danikula.videocache.HttpProxyCacheServer
import com.danikula.videocache.file.FileNameGenerator
import com.raizlabs.android.dbflow.config.FlowManager
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout.setDefaultRefreshHeaderCreater
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout


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

    private fun newProxy(): HttpProxyCacheServer {
        return HttpProxyCacheServer.Builder(context)
                .fileNameGenerator(FileNameGenerator {
                    val uri = Uri.parse(it)
                    uri.lastPathSegment
                })
                .build()
    }

    private var proxy: HttpProxyCacheServer? = null

    fun getProxy(context: Context): HttpProxyCacheServer? {
        val app = context.applicationContext as InitApplication
        return if (app.proxy == null) app.newProxy() else app.proxy
    }
}
