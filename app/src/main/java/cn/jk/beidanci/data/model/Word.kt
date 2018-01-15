package cn.jk.beidanci.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * Created by jack on 2018/1/10.
 */

class Word(
        var wordRank: String?,
        var headWord: String?,
        var content: WordContentOutOut) {
    constructor (dbWord: DbWord) : this(dbWord.wordId, dbWord.head,
            WordContentOutOut(WordContentOut(dbWord.head, dbWord.wordId,
                    Gson().fromJson(dbWord.content, WordContent::class.java)
            )))
}

class WordContentOutOut(var word: WordContentOut)
//此处使用any方便入库
class WordContentOut(var wordHead: String, var wordId: String, var content: Any)

class WordContent(var sentence: SentenceOut, var usphone: String, var ukphone: String,
                  var ukspeech: String, var syno: SynoOut, var star: String, var speech: String,
                  var usspeech: String, var trans: List<Trans>, var phrase: PhraseOut, var remMethod: RemMethod, var relWord: RelWordOut)

class SentenceOut(var sentences: List<Sentence>, var desc: String) //例句
class Sentence(var sContent: String, var sCn: String)
class SynoOut(var synos: List<Syno>, var desc: String)
class Syno(var pos: String, var tran: String, var hwds: List<Hwd>) //hwd 是什么,仅是同近义词
class Hwd(var w: String)
class PhraseOut(var desc: String, var phrases: List<Phrase>) //短语
class Phrase(var pCn: String, var pContent: String)
class RemMethod(var desc: String, @SerializedName("val") var value: String)//TODO 不确定行不行. 拆分记忆
class RelWordOut(var rels: List<RelWord>, var desc: String) //同根词
class RelWord(var pos: String, var words: List<RelWordDesc>) //pos 词性
class RelWordDesc(var hwd: String, var tran: String) //hwd 英文,tran,中文.

class Trans(var tranCn: String, var descOther: String, var descCn: String, var pos: String, var tranOther: String) //trans other 就是英英翻译
