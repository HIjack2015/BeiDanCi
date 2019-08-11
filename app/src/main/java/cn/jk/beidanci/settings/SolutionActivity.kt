package cn.jk.beidanci.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.CommonQuestion
import kotlinx.android.synthetic.main.activity_solution.*

class SolutionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solution)
        val commonQuestion = intent.getSerializableExtra(Constant.QUESTION) as CommonQuestion
        questionTxt.text = commonQuestion.question
        solutionTxt.text = "     " + commonQuestion.solution
        supportActionBar!!.title = "问题解答"
    }
}
