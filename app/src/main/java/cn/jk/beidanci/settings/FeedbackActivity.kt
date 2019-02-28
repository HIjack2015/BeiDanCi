package cn.jk.beidanci.settings

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.jk.beidanci.R

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        title=baseContext.getString(R.string.feedback)
    }
}
