package cn.jk.beidanci.learnword

/**
 * Created by jack on 2018/1/16.
 */
class LearnWordPresenter(var view: LearnWordContract.View) : LearnWordContract.Presenter {
    override fun next() {

    }

    override fun start() {
        val dbWordList = WordListHelper.wordList
        val dbWord = dbWordList.getCurrentWord()
        if (dbWord != null) {
            view.inflateCardView(dbWord)
        } else {
            error("attempt to visit postion beyond list.") //TODO 上传到bugly.
        }


    }

    override fun stop() {

    }
}