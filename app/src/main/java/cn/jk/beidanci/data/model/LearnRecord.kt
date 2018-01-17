package cn.jk.beidanci.data.model

import cn.jk.beidanci.data.source.AppDatabase
import cn.jk.beidanci.utils.DateUtil
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.ForeignKey
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

/**
 * Created by jack on 2018/1/16.
 * 针对某一天某个单词的学习,复习 状况,记录下来.
 *
 */
@Table(database = AppDatabase::class)
class LearnRecord(@PrimaryKey(autoincrement = true)
                  var id: Long = 0,
                  @ForeignKey(stubbedRelationship = true)
                  var dbWord: DbWord? = null,
                  @Column
                  var learnTime: String = DateUtil.formateToday(),
                  @Column(getterName = "getReviewed")
                  var reviewed: Boolean = false

) {

}