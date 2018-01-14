package cn.jk.beidanci.choosebook

import android.app.Dialog
import android.app.DialogFragment
import android.app.ProgressDialog
import android.os.Bundle
import cn.jk.beidanci.R

/**
 * Created by jack on 2018/1/14.
 */
@Suppress("DEPRECATION")
public class DownloadBookDialog : DialogFragment() {
    lateinit var progressDialog: ProgressDialog;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        progressDialog = ProgressDialog(activity)
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage(activity.getString(R.string.downloading))
        progressDialog.setCancelable(false)
        progressDialog.max = 100

        return progressDialog
    }

    fun setMessage(s: String) {
        progressDialog.setMessage(s)
    }
}