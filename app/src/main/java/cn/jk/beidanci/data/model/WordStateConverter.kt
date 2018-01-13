package cn.jk.beidanci.data.model

import com.raizlabs.android.dbflow.converter.TypeConverter


/**
 * Created by jack on 2018/1/12.
 */
@com.raizlabs.android.dbflow.annotation.TypeConverter
class WordStateConverter : TypeConverter<Int, WordState>() {
    override fun getModelValue(data: Int?): WordState {
        return WordState.unknown.getState(data)
    }

    override fun getDBValue(model: WordState?): Int {
        return WordState.unknown.getValue(model)
    }


}