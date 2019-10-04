package cn.jk.beidanci.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.Constant.Companion.DEFAULT_GUID
import cn.jk.beidanci.data.api.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.design.snackbar

import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*


class FeedbackActivity : BaseActivity() {
    fun showMsg(msg: String) {
        window.decorView.rootView.snackbar(msg)
    }

    fun jumpToWeiboProfileInfo(context: Context, uid: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        val weiboInstalled = PackageUtils.isSinaWeiboInstalled(context)
        if (weiboInstalled) {
            intent.data = Uri.parse("sinaweibo://userinfo?uid=$uid")
        } else {
            intent.data = Uri.parse("http://weibo.cn/qr/userinfo?uid=$uid")
        }
        context.startActivity(intent)
    }

    internal object PackageUtils {

        // 新浪微博是否安装
        fun isSinaWeiboInstalled(context: Context): Boolean {
            return isPackageInstalled(context, "com.sina.weibo")
        }

        // 包名对应的App是否安装
        fun isPackageInstalled(context: Context, packageName: String): Boolean {
            val packageManager = context.packageManager ?: return false
            val packageInfoList = packageManager.getInstalledPackages(0)
            for (info in packageInfoList) {
                if (info.packageName == packageName)
                    return true
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cn.jk.beidanci.R.layout.activity_feedback)
        title = baseContext.getString(cn.jk.beidanci.R.string.feedback)
        openWeiboBtn.onClick {
            if (PackageUtils.isSinaWeiboInstalled(applicationContext)) {
                jumpToWeiboProfileInfo(applicationContext, Constant.WEIBO_USER_ID)
            } else {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.weibo.cn/u/" + Constant.WEIBO_USER_ID))
                startActivity(browserIntent)
            }
        }
        submitBtn.onClick {
            submitBtn.isClickable = false
            val contactInfo = contactInfoEdt.text.toString()

            val content = contentEdt.text.toString()
            if (content == null || content.isEmpty()) {
                showMsg("反馈内容还没填写")
                submitBtn.isClickable = true
                return@onClick
            }

            var guid = (prefs[Constant.GUID, DEFAULT_GUID]).toString()
            if (guid.equals(DEFAULT_GUID)) {
                prefs[Constant.GUID] = java.util.UUID.randomUUID().toString()
                guid = (prefs[Constant.GUID, DEFAULT_GUID]).toString()
            }
            val project = Constant.PROJECT_ID

            val params = HashMap<String, String>()
            if (contactInfo != null && !contactInfo.isEmpty()) {
                params["contactInfo"] = contactInfo
            }
            params["guid"] = guid
            params["project"] = project
            params["content"] = content + Date().toString()

            ApiManager.feedbackService.feedback(params).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        showMsg(it)
                        submitBtn.isClickable = true
                    }

        }

    }
}
