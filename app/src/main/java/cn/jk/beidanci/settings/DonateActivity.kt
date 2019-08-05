package cn.jk.beidanci.settings

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.jk.beidanci.R
import cn.jk.beidanci.utils.FileUtil
import kotlinx.android.synthetic.main.activity_donate.*
import org.jetbrains.anko.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class DonateActivity : AppCompatActivity() {
    val weixin = "微信"
    val alipay = "支付宝"

    internal var currentImgType = weixin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)


        title=baseContext.getString(R.string.donate_developer)

        alipayBtn.setOnClickListener {
            openAlipayBtn.visibility= View.VISIBLE
            donateImg.setImageResource(R.drawable.donate_alipay)
            currentImgType=alipay
        }

        weixinBtn.setOnClickListener {
            openAlipayBtn.visibility= View.INVISIBLE
            donateImg.setImageResource(R.drawable.donate_weixin)
            currentImgType=weixin

        }
        openAlipayBtn.setOnClickListener {
            val alipayUrl = getString(R.string.alipayUrl)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(alipayUrl)
            startActivity(i)
        }

        saveDonateImgBtn.setOnClickListener {
          saveImgWithPermissionCheck()
        }
    }
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun  saveImg() {

        val resId = if (currentImgType.equals(weixin)) R.drawable.donate_weixin else R.drawable.donate_alipay
        val donateBm = BitmapFactory.decodeResource(resources, resId)

        val fileName = if (currentImgType.equals(weixin) ) "IMG_donateByWeiXin.png" else "IMG_donateByAlipay.png"
        val saveSuccess=FileUtil.saveImg(applicationContext, fileName, donateBm)
        if (saveSuccess) {
            toast(String.format("已将二维码保存至本地,请打开%s扫一扫", currentImgType))
        }
    }
}
