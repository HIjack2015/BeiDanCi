package cn.jk.beidanci.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.io.IOException

/**
 * Created by jack on 2018/1/14.
 */
data class ErrorBody(val code: Int, @SerializedName("reason") val message: String) {

    override fun toString(): String = "{code:$code, message:\"$message\"}"

    companion object {

        val UNKNOWN_ERROR = 0


        fun parseError(response: Response<*>?): ErrorBody? {
            return (response?.errorBody())?.let {
                try {
                    return Gson().fromJson<ErrorBody>(it.toString(), ErrorBody::class.java)
                } catch (ignored: IOException) {
                    return null
                }
            }
        }
    }

}