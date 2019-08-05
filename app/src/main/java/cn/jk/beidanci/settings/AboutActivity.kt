package cn.jk.beidanci.settings

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.R
import kotlinx.android.synthetic.main.activity_about.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)
        title=getString(R.string.about_app_str)
        try {
            versionLbl.text = packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: Exception) {
            toast(e.toString())
        }
        commonQuestionLbl.setOnClickListener {
            startActivity<CommonQuestionActivity>()
            false
        }
        rateLbl.setOnClickListener {
            try {

                val uri = Uri.parse("market://details?id=$packageName")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } catch (e: Exception) {
              toast("没有找到应用市场.sorry")
            }

        }
        feedbackLbl.setOnClickListener {
            startActivity<FeedbackActivity>()
            false
        }
        donateLbl.setOnClickListener {
            startActivity<DonateActivity>()
            false
        }

    }
}
