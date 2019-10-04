package cn.jk.beidanci.searchword

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import kotlinx.android.synthetic.main.activity_net_word.*

class NetWordActivity : BaseActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_word)


        val intent = intent

        val english = intent.getStringExtra(Constant.ENGLISH)
        if (english == null || english == "") {
            Log.e("error", "怎么可能开始没有english传入")
        }
        val webSettings = youdaoWebView.settings
        webSettings.javaScriptEnabled = true
        supportActionBar!!.title = getString(R.string.youdaoTitle)

        youdaoWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        youdaoWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val javaScript = "javascript:(function() {__closeBanner();})()"
                youdaoWebView.loadUrl(javaScript)
            }
        }
        youdaoWebView.loadUrl(Constant.youdaoWordPageUrl + english!!)

    }


}
