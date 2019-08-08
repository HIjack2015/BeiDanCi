package cn.jk.beidanci.data.api

import cn.jk.beidanci.data.Constant
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by jack on 2019/8/8.
 */
interface FeedbackService {
    @POST(Constant.FEEDBACK_URL)
    @FormUrlEncoded
    fun feedback(@FieldMap map: Map<String, String>): Observable<String>

}