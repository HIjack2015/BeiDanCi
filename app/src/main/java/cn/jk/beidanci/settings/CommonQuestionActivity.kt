package cn.jk.beidanci.settings

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.R
import cn.jk.beidanci.utils.AssetsUtil
import kotlinx.android.synthetic.main.activity_common_question.*

class CommonQuestionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_question)


        val commonQuestions = AssetsUtil.loadCardFromFile(this, "question.json")

        questionRcl.setHasFixedSize(true)

        questionRcl.layoutManager = LinearLayoutManager(this)
        questionRcl.adapter = QuestionAdapter(commonQuestions, this)
        supportActionBar!!.title = "常见问题"
    }
}
