package cn.jk.beidanci.wordlist

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.InitApplication
import cn.jk.beidanci.R
import cn.jk.beidanci.animate.ScaleDownThenUp
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.utils.MediaUtil
import com.raizlabs.android.dbflow.kotlinextensions.update
import kotlinx.android.synthetic.main.layout_word_card.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat

class WordDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detail)
        var dbWord = intent.extras.getSerializable(Constant.DB_WORD) as DbWord
        inflateCardView(dbWord)
    }

    private fun displayPronunciation(english: String) {
        val speech = prefs[Constant.SPEECH_COUNTRY, Constant.US_SPEECH]
        // String voiceUrl = Constant.youdaoVoiceUrl + words.getCurrent().getEnglish();
        //注意单词必须为最后一个参数.否则缓存会有问题
        val voiceUrl = Constant.youdaoDictUrl + speech + "&" + Constant.ENGLISH_AUDIO_QUERY_PARA + "=" + english
        val urlOk = MediaUtil.display(voiceUrl, InitApplication.context)
    }

    fun inflateCardView(dbWord: DbWord) {


        with(dbWord) {
            wordDescLyt.visibility = View.VISIBLE
            learnRecordLyt.visibility = View.GONE
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

                wordDescLyt.forEach {
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

}
