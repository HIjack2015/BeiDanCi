package cn.jk.beidanci.data.model

/**
 * Created by jack on 2018/1/12.
 */
enum class WordState {
    unlearned,
    unknown,
    known,
    neverShow;

    fun getValue(state: WordState?): Int {
        var result = 1
        when (state) {
            unlearned -> result = 1
            unknown -> result = 2
            known -> result = 3
            neverShow -> result = 4
        }
        return result
    }

    fun getState(data: Int?): WordState {
        var state = unlearned
        when (data) {
            1 -> state = unlearned
            2 -> state = unknown
            3 -> state = known
            4 -> state = neverShow
        }
        return state
    }
}