package cn.jk.beidanci.learnword

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.jk.beidanci.BaseViewActivity
import cn.jk.beidanci.InitApplication.Companion.context
import cn.jk.beidanci.R
import cn.jk.beidanci.animate.ScaleDownThenUp
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.utils.MediaUtil
import cn.jk.beidanci.utils.OnSwipeTouchListener
import com.raizlabs.android.dbflow.kotlinextensions.update
import kotlinx.android.synthetic.main.activity_learn_word.*
import kotlinx.android.synthetic.main.layout_word_card.*
import org.jetbrains.anko.forEachChild
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat

class LearnWordActivity : BaseViewActivity<LearnWordContract.Presenter>(), LearnWordContract.View {
    lateinit var dbWordList: WordList
    internal var netErrorShouldShow = true
    var autoDisplay: Boolean? = false
    override fun setPercent(percent: Int) {
        progressBar.progress = percent
    }

    override var mPresenter: LearnWordContract.Presenter = LearnWordPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)

        dbWordList = WordListHelper.wordList
        if (dbWordList.isEmpty()) {
            toast(dbWordList.getEmptyMessage())
            finish()
            return
        }
        supportActionBar!!.title = dbWordList.title
        setLogic()
        mPresenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_learn_word, menu)
        val autoDisplayChk = menu.findItem(R.id.autoDisplayChk)

        autoDisplayChk.isChecked = prefs[Constant.AUTO_DISPLAY, false]
        val chooseShowItemBtn=menu.findItem(R.id.chooseShowItemBtn)
        chooseShowItemBtn.setOnMenuItemClickListener {
            startActivity<AdjustWordCardActivity>()
            false
        }

        return true
    }


    private fun currentNeverShow() {
        prefs[Constant.TIPS_OF_NEVER_SHOW_SHOULD_SHOW] = false
        dbWordList.currentNeverShow()
        next()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.neverShowBtn -> {
                if (prefs[Constant.TIPS_OF_NEVER_SHOW_SHOULD_SHOW, true]) {
                    NeverShowWordDialog().show(fragmentManager, "neverShowTag")//TODO
                } else {
                    currentNeverShow()
                }
                return true
            }
            R.id.autoDisplayChk -> {
                item.isChecked = !item.isChecked
                autoDisplay = item.isChecked
                prefs[Constant.AUTO_DISPLAY] = autoDisplay
                return true
            }
            R.id.speechBtn -> {
                val countries = listOf(getString(R.string.UK_SPEECH_DESC), getString(R.string.US_SPEECH_DESC))
                selector(getString(R.string.CHOOSE_SPEECH), countries, { dialogInterface, i ->
                    when (i) {
                        0 -> prefs[Constant.SPEECH_COUNTRY] = Constant.UK_SPEECH
                        1 -> prefs[Constant.SPEECH_COUNTRY] = Constant.US_SPEECH
                    }
                })
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun next() {
        var dbWord = WordListHelper.wordList.next()
        if (dbWord == null) {
            toast(dbWordList.getFinishMessage())
            finish()
            return
        }
        dealWordChange(dbWord)
    }

    private fun dealWordChange(dbWord: DbWord) {
        if (prefs[Constant.AUTO_DISPLAY, false]) {
            displayPronunciation(dbWord.head)
        }
        setPercent(dbWordList.getPercent())
        inflateCardView(dbWord)
        unknownBtn.setText(R.string.dontKnow)
        knowBtn.setText(R.string.know)
    }

    private fun previous() {
        var dbWord = WordListHelper.wordList.previous()
        if (dbWord == null) {
            showMsg(R.string.ALREADY_FIRST)
            return
        }
        dealWordChange(dbWord)
    }
    private fun setLogic() {
        voiceBtn.setOnClickListener(View.OnClickListener {
            netErrorShouldShow = true
            displayPronunciation(dbWordList.getCurrentWord()!!.head)
        })
        setPercent(dbWordList.getPercent())
        unknownBtn.setOnClickListener {
            if (unknownBtn.text == getString(R.string.next)) {
                dbWordList.currentUnknown()
                next()
            } else {
                unknownBtn.text = getString(R.string.next)
                knowBtn.text = getString(R.string.know)
                wordDescLyt.visibility = View.VISIBLE
                learnRecordLyt.visibility = View.GONE
            }
        }
        knowBtn.setOnClickListener {
            if (knowBtn.text == getString(R.string.next)) {
                dbWordList.currentKnown()
                next()
            } else {
                knowBtn.text = getString(R.string.next)
                unknownBtn.text = getString(R.string.dontKnow)
                wordDescLyt.visibility = View.VISIBLE
                learnRecordLyt.visibility = View.GONE
            }
        }
        autoDisplay = prefs[Constant.AUTO_DISPLAY, false]

        if (autoDisplay as Boolean) {
            displayPronunciation(dbWordList.getCurrentWord()!!.head)
        }
        wordCardView.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeLeft() {
                previous()
            }

        })

    }


    private fun displayPronunciation(english: String) {
        val speech = prefs[Constant.SPEECH_COUNTRY, Constant.US_SPEECH]
        // String voiceUrl = Constant.youdaoVoiceUrl + words.getCurrent().getEnglish();
        //注意单词必须为最后一个参数.否则缓存会有问题
        val voiceUrl = Constant.youdaoDictUrl + speech + "&" + Constant.ENGLISH_AUDIO_QUERY_PARA + "=" + english
        val urlOk = MediaUtil.display(voiceUrl, context)
        if (!urlOk) {
            if (netErrorShouldShow) {
                toast(R.string.voice_need_net)
                netErrorShouldShow = false
            }
        }
    }

    override fun inflateCardView(dbWord: DbWord) {


        with(dbWord) {
            wordDescLyt.visibility = View.GONE
            learnRecordLyt.visibility = View.VISIBLE
            englishTxt.text = head
            coreImg.visibility = if (important) View.VISIBLE else View.GONE
            voiceBtn.setOnClickListener {
                displayPronunciation(head)
            }

            if (dbWord.collect) {
                collectBtn.background = resources.getDrawable(R.drawable.ic_star_deep_blue_24dp)
            } else {
                collectBtn.background = resources.getDrawable(R.drawable.ic_star_border_blue_24dp)
            }

            collectBtn.onClick {
                dbWord.collect = !dbWord.collect
                dbWord.update()
                if (dbWord.collect) {
                    ScaleDownThenUp.animate(collectBtn, R.drawable.ic_star_deep_blue_24dp)
                } else {
                    ScaleDownThenUp.animate(collectBtn, R.drawable.ic_star_border_blue_24dp)
                }

            }

            with(getWordContent()) {
                wordDescLyt.forEachChild {
                    it.visibility = View.VISIBLE
                }
                val isUsPhonetic: Boolean = prefs[Constant.SPEECH_COUNTRY, Constant.US_SPEECH] == Constant.US_SPEECH
                var phoneticStr = if (isUsPhonetic)
                    usphone else ukphone
                if (phoneticStr == null || phoneticStr.isEmpty()) {
                    phoneticTxt.visibility = View.GONE
                } else {
                    phoneticTxt.visibility = View.VISIBLE
                    phoneticTxt.text = "/" + phoneticStr + "/"
                }

                knownTimeTxt.text = knownCount.toString()
                unknownTimeTxt.text = unknownCount.toString()
                if (lastLearnTime == null) {
                    lastLearnTimeTxt.text = applicationContext.getText(R.string.NOT_LEARNED_YET)
                } else {
                    lastLearnTimeTxt.text = SimpleDateFormat("MM-dd HH:mm").format(lastLearnTime)
                }
                //只考虑中文翻译现在
                var sb = StringBuilder()
                trans.forEach {
                    val pos = if (it.pos == null) "" else it.pos
                    sb.append(pos + " " + it.tranCn + "\n")
                }
                if (sb.isNotEmpty()) {
                    sb.deleteCharAt(sb.length - 1)
                    transTxt.text = sb.toString()
                }
                sb = StringBuilder()
                if (phrase != null) {
                    phrase.phrases.forEach {
                        sb.append(it.pContent + " " + it.pCn + "\n")
                    }
                    if (sb.isNotEmpty()) {
                        sb.deleteCharAt(sb.length - 1)
                        phraseTxt.text = sb.toString()
                        phraseLyt.visibility = View.VISIBLE
                    } else {
                        phraseLyt.visibility = View.GONE
                    }
                } else {
                    phraseLyt.visibility = View.GONE
                }

                sb = StringBuilder()
                if (sentence != null) {

                    sentence.sentences.forEach {
                        sb.append(it.sContent + "\n" + it.sCn + "\n")
                    }
                    if (sb.isNotEmpty()) {
                        sb.deleteCharAt(sb.length - 1)
                        sentenceTxt.text = sb.toString()
                        sentenceLyt.visibility = View.VISIBLE
                    } else {
                        sentenceLyt.visibility = View.GONE
                    }
                } else {
                    sentenceLyt.visibility = View.GONE
                }
                //记忆方法
                if (remMethod == null) {
                    remMethodLyt.visibility = View.GONE
                } else {
                    remMethodLyt.visibility = View.VISIBLE
                    remMthodTxt.text = remMethod.value
                }
                sb = StringBuilder()
                if (relWord != null) {
                    relWord.rels.forEach {
                        sb.append(it.pos + "\n")
                        it.words.forEach {
                            sb.append(it.hwd + " " + it.tran + "\n")
                        }
                    }
                    if (sb.isNotEmpty()) {
                        sb.deleteCharAt(sb.length - 1)
                        relsTxt.text = sb.toString()
                        relsLyt.visibility = View.VISIBLE
                    } else {
                        relsLyt.visibility = View.GONE
                    }
                } else {
                    relsLyt.visibility = View.GONE
                }

                //近义词
                sb = StringBuilder()
                if (syno != null) {
                    syno.synos.forEach {
                        sb.append(it.pos + " " + it.tran + "\n")
                        for (hwd in it.hwds) {
                            sb.append(hwd.w + ",")
                        }
                        if (sb.isNotEmpty() && sb.get(sb.length - 1) == (',')) {
                            sb.deleteCharAt(sb.length - 1)
                        }
                        sb.append("\n")
                    }
                    if (sb.isNotEmpty()) {
                        sb.deleteCharAt(sb.length - 1)
                        hwdTxt.text = sb.toString()
                        hwdLyt.visibility = View.VISIBLE
                    } else {
                        hwdLyt.visibility = View.GONE
                    }
                } else {
                    hwdLyt.visibility = View.GONE
                }

            }


        }
    }

    internal class NeverShowWordDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(R.string.tips_never_show)
                    .setPositiveButton(R.string.confirm_never_show) { dialog, id ->


                        (activity as LearnWordActivity).currentNeverShow()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, id ->
                        toast("哈，还真有人点取消啊")
                    }
            // Create the AlertDialog object and return it
            return builder.create()
        }
    }



}
