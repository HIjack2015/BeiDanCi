package cn.jk.beidanci.learnword

import android.os.Bundle
import android.view.View
import cn.jk.beidanci.BaseViewActivity
import cn.jk.beidanci.InitApplication.Companion.context
import cn.jk.beidanci.R

import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.utils.MediaUtil
import kotlinx.android.synthetic.main.activity_learn_word.*
import org.jetbrains.anko.forEachChild
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
        supportActionBar!!.setTitle(dbWordList.title)
        setLogic()

    }

    fun next() {
        var dbWord = WordListHelper.wordList.next();
        if (dbWord == null) {
            toast(dbWordList.getFinishMessage())
            finish()
        } else {
            inflateCardView(dbWord)
        }
        unknownBtn.text = "不认识"
        knowBtn.text = "认识"
    }

    fun setLogic() {
        voiceBtn.setOnClickListener(View.OnClickListener {
            netErrorShouldShow = true
            displayPronunciation(dbWordList.getCurrentWord()!!.head)
        })
        unknownBtn.setOnClickListener {
            if (unknownBtn.text == "下一个") {
                dbWordList.currentUnknown()
                next()
            } else {
                dbWordList.currentUnknown()
                unknownBtn.text = "下一个"
                knowBtn.text = "认识"
                wordDescLyt.setVisibility(View.VISIBLE)
                learnRecordLyt.visibility = View.GONE
            }
        }
        knowBtn.setOnClickListener {
            if (knowBtn.text == "下一个") {
                dbWordList.currentKnown()
                next()
            } else {
                knowBtn.text = "下一个"
                unknownBtn.text = "不认识"
                wordDescLyt.setVisibility(View.VISIBLE)
                learnRecordLyt.visibility = View.GONE
            }
        }
        autoDisplay = prefs[Constant.AUTO_DISPLAY, false]

        if (autoDisplay as Boolean) {
            displayPronunciation(dbWordList.getCurrentWord()!!.head)
        }

    }

    fun displayPronunciation(english: String) {


        // String voiceUrl = Constant.youdaoVoiceUrl + words.getCurrent().getEnglish();
        val voiceUrl = Constant.shanbeiVoiceUrl + english + ".mp3"
        val urlOk = MediaUtil.display(voiceUrl, context)
        if (!urlOk) {
            if (netErrorShouldShow) {
                toast("单词发音需要连接网络,或在高级设置中下载语音包")
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
                //TODO
            }
            with(getWordContent()) {
                wordDescLyt.forEachChild {
                    it.visibility = View.VISIBLE
                }
                val isUsPhonetic: Boolean? = prefs[Constant.US_PHONETIC]
                phoneticTxt.text = if (isUsPhonetic == null || isUsPhonetic)
                    usphone else ukphone
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
                    } else {
                        hwdLyt.visibility = View.GONE
                    }
                } else {
                    hwdLyt.visibility = View.GONE
                }

            }


        }
    }
}
