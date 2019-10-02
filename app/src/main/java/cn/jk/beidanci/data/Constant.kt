package cn.jk.beidanci.data

/**
 * Created by jack on 2018/1/14.
 */
open class Constant {
    companion object {
        const val WEIBO_USER_ID: String = "5502148603"
        const val DB_WORD: String = "DB_WORD"
        const val CURRENT_BOOK = "CURRENT_BOOK"
        const val SHOULD_SHOW_SETTING_FRAGMENT = "SHOULD_SHOW_SETTING_FRAGMENT"

        const val SERVER_URL = "http://123.206.13.73/kydc/"
        const val FEEDBACK_PATH = "feedback/add"
        const val FEEDBACK_URL = SERVER_URL + FEEDBACK_PATH

        const val ADD_EASY_WORD_URL = SERVER_URL + "easyWord/add"

        const val MODE = "mode"

        const val REVIEW_DATE = "REVIEW_DATE"


        const val REVIEW_MODE = "review"
        const val LEARN_MODE = "学习单词"
        const val EXAM_DATE = "examDate"
        const val DATABASE_NAME = "word.db"
        const val ENGLISH = "english"
        const val TIPS_OF_NEVER_SHOW_SHOULD_SHOW = "TIPS_OF_NEVER_SHOW_SHOULD_SHOW"
        const val AUTO_DISPLAY = "autoDisplay"
        const val PIE_WORD = "pieWord"
        const val QUESTION = "QUESTION"
        const val youdaoVoiceUrl = "http://dict.youdao.com//dictvoice?type=2&audio="
        const val shanbeiVoiceUrl = "http://media.shanbay.com/audio/us/"
        const val YOUDAO_SPEECH_TYPE_PARA = "type"
        const val youdaoDictUrl = "http://dict.youdao.com/dictvoice?" + YOUDAO_SPEECH_TYPE_PARA + "="
        const val cibaUrl = "http://dict-co.iciba.com/api/dictionary.php?key=AA6C7429C3884C9E766C51187BD1D86F&type=json&w="
        const val PLAN_LEARN: String = "shouldLearnPerDay"

        const val COMMON_QUESTION = "commonQuestion"
        const val DISPLAY_SETTING = "displaySetting"
        const val AUTO_REVIEW_MODE = "AUTO_REVIEW_MODE"
        const val ENGLISH_FILE_DIR = "/Android/data/cn.jk.kaoyandanci/cache/video-cache"
        const val ENGLISH_ZIP_PATH = ENGLISH_FILE_DIR + "/english.zip"

        const val GUID = "GUID"
        const val PROJECT_ID = "bdc"
        const val DEFAULT_GUID: String = "DEFAULT_GUID"
        const val SMART_REVIEW_TIP = "SMART_REVIEW_TIP"
        const val NEVER_SHOW = "已掌握"
        const val KNOWED = "已认识"
        const val UNKNOWN = "不认识"
        const val NOT_LEARNED = "未学习"

        const val WORD_LIST_LBL = "WORD_LIST_LBL"
        const val WORD_LIST = "WORD_LIST"
        val ENCOURAGE_SENTENCE = arrayOf("敢想不敢为者,终困身牢笼", "想要和得到 ,中间还有两个字,就是做到", "又怎会晓得执着的人 拥有隐形翅膀", "若一去不回,便一去不回", "大丈夫当提三尺剑，立不世功", "夫学须静也，才须学也，非学无以广才，非志无以成学", "时人不识凌云木，直待凌云始道高", "What hurts more, the pain of hard work or the pain of regret ? ", "All things come to those who wait", "Nothing is impossible!", "Keep on going never give up", "Nothing for nothing. ", "What is the man's first duty? The answer is brief: to be himself. ", "诶,其实我有点坚持不下去了.:<")
        const val QUERY_SQL = "querySql"
        const val TITLE = "TITLE"
        const val QUERY_BUILDER = "QUERY_BUILDER"
        const val EASY = "easy"
        const val SHOW_CHINESE_LIST = "SHOW_CHINESE_LIST"
        const val NEED_LEARN = "NEED_LEARN"

        const val FIRST_OPEN = "firstOpen"
        const val DATA_CHANGED = false
        const val nextDelay = 1500
        const val allWordCount = 5492
        const val PLAN_LEARN_NO = 60
        const val youdaoWordPageUrl = "https://m.youdao.com/dict?le=eng&q="
        const val searchTipShow = "searchTipShow"
        const val shouldShowGuide = "shouldShowGuide"
        const val downloadVoicePackUrl = "http://kydc-1253381140.costj.myqcloud.com/english.zip"

        const val EXTRA_LEARN: String = "超额学习"
        const val SPEECH_COUNTRY: String = "SPEECH_COUNTRY"
        const val UK_SPEECH: Int = 1
        const val US_SPEECH: Int = 2
        const val ENGLISH_AUDIO_QUERY_PARA = "audio"
        const val REVIEW_TITLE: String = "复习模式"
        const val FORGET_CURVE_LEARN_MODE: String = "遗忘曲线复习"
        val NOT_INIT: String = "NOT_INIT"
        val INSTALL_TIME: String = "INSTALL_TIME"


    }
}