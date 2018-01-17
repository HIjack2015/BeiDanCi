package cn.jk.beidanci.data

/**
 * Created by jack on 2018/1/14.
 */
class Constant {
    companion object {
        val CURRENT_BOOK = "CURRENT_BOOK"
        val SHOULD_SHOW_SETTING_FRAGMENT = "SHOULD_SHOW_SETTING_FRAGMENT"

        val SERVER_URL = "http://123.206.13.73/kydc/"

        val FEEDBACK_URL = SERVER_URL + "feedback/add"
        val ADD_EASY_WORD_URL = SERVER_URL + "easyWord/add"

        val MODE = "mode"

        val REVIEW_DATE = "REVIEW_DATE"


        val REVIEW_MODE = "review"
        val LEARN_MODE = "学习单词"
        val EXAM_DATE = "examDate"
        val DATABASE_NAME = "word.db"
        val ENGLISH = "english"
        val TIPS_OF_NEVER_SHOW_SHOULD_SHOW = "TIPS_OF_NEVER_SHOW_SHOULD_SHOW"
        val AUTO_DISPLAY = "autoDisplay"
        val PIE_WORD = "pieWord"
        val QUESTION = "QUESTION"
        val youdaoVoiceUrl = "http://dict.youdao.com//dictvoice?type=2&audio="
        val shanbeiVoiceUrl = "http://media.shanbay.com/audio/us/"
        var cibaUrl = "http://dict-co.iciba.com/api/dictionary.php?key=AA6C7429C3884C9E766C51187BD1D86F&type=json&w="
        val PLAN_LEARN = "shouldLearnPerDay"

        val COMMON_QUESTION = "commonQuestion"
        val DISPLAY_SETTING = "displaySetting"
        val AUTO_REVIEW_MODE = "AUTO_REVIEW_MODE"
        val ENGLISH_FILE_DIR = "/Android/data/cn.jk.kaoyandanci/cache/video-cache"
        val ENGLISH_ZIP_PATH = ENGLISH_FILE_DIR + "/english.zip"

        val GUID = "GUID"
        val PROJECT_ID = "kydc"
        val DEFAULT_GUID: Any = "DEFAULT_GUID"
        val SMART_REVIEW_TIP = "SMART_REVIEW_TIP"
        val NEVER_SHOW = "已掌握"
        val KNOWED = "已认识"
        val UNKNOWN = "不认识"
        val NOT_LEARNED = "未学习"

        val WORD_LIST_LBL = "WORD_LIST_LBL"
        val WORD_LIST = "WORD_LIST"
        val ENCOURAGE_SENTENCE = arrayOf("敢想不敢为者,终困身牢笼", "想要和得到 ,中间还有两个字,就是做到", "又怎会晓得执着的人 拥有隐形翅膀", "若一去不回,便一去不回", "大丈夫当提三尺剑，立不世功", "夫学须静也，才须学也，非学无以广才，非志无以成学", "时人不识凌云木，直待凌云始道高", "What hurts more, the pain of hard work or the pain of regret ? ", "All things come to those who wait", "Nothing is impossible!", "Keep on going never give up", "Nothing for nothing. ", "What is the man's first duty? The answer is brief: to be himself. ", "诶,其实我有点坚持不下去了.:<")
        val QUERY_SQL = "querySql"
        val TITLE = "TITLE"
        val QUERY_BUILDER = "QUERY_BUILDER"
        val EASY = "easy"
        val SHOW_CHINESE_LIST = "SHOW_CHINESE_LIST"
        val NEED_LEARN = "NEED_LEARN"

        var FIRST_OPEN = "firstOpen"
        var DATA_CHANGED = false
        var nextDelay = 1500
        var allWordCount = 5492
        var PLAN_LEARN_NO = 60
        var youdaoWordPageUrl = "https://m.youdao.com/dict?le=eng&q="
        var searchTipShow = "searchTipShow"
        var shouldShowGuide = "shouldShowGuide"
        var downloadVoicePackUrl = "http://kydc-1253381140.costj.myqcloud.com/english.zip"
        val US_PHONETIC = "US_PHONETIC"
        val EXTRA_LEARN: String = "超额学习"


    }
}