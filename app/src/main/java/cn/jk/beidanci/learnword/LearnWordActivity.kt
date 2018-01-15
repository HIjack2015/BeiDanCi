package cn.jk.beidanci.learnword

import android.os.Bundle
import android.view.View
import cn.jk.beidanci.BaseActivity
import cn.jk.beidanci.R
import cn.jk.beidanci.data.Constant
import cn.jk.beidanci.data.model.DbWord
import cn.jk.beidanci.data.model.DbWord_Table
import com.raizlabs.android.dbflow.sql.language.SQLite
import kotlinx.android.synthetic.main.activity_learn_word.*
import java.text.SimpleDateFormat

class LearnWordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)

        val bookId: String? = prefs[Constant.CURRENT_BOOK]
        val dbWordList = SQLite.select().from(DbWord::class.java).where(DbWord_Table.bookId.eq(bookId)).queryList()
        val dbWord = dbWordList.get(0)

        inflateCardView(dbWord)

    }

    private fun inflateCardView(dbWord: DbWord) {
        with(dbWord) {
            englishTxt.text = head
            coreImg.visibility = if (important) View.VISIBLE else View.INVISIBLE
            voiceBtn.setOnClickListener {
                //TODO
            }
            with(getWordContent()) {
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
