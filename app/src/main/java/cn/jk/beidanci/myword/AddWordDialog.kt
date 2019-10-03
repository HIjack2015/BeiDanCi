package cn.jk.beidanci.myword

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.R
import cn.jk.beidanci.data.model.*
import cn.jk.beidanci.wordlist.WordListActivity
import com.google.gson.Gson
import com.raizlabs.android.dbflow.kotlinextensions.insert
import kotlinx.android.synthetic.main.dialog_add_word.*
import java.util.*


/**
 * Created by jack on 2017/11/24.
 */

class AddWordDialog : androidx.fragment.app.DialogFragment() {
    // Save your custom view at the class level
    lateinit var customView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Simply return the already inflated custom view
        return customView
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(activity)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater
        customView = inflater.inflate(R.layout.dialog_add_word, null)
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customView)

        builder.setPositiveButton(R.string.confirm) { dialog, id -> addWord() }
                .setNegativeButton(R.string.cancel) { dialog, id -> }

        //异步联网拿到中文.考完在做.
        //        englishTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        //            @Override
        //            public void onFocusChange(View v, boolean hasFocus) {
        //                String english = englishTxt.getText().toString();
        //                if (!hasFocus) {
        //
        //                    if (english == null || english.isEmpty()) {
        //                        return;
        //                    }
        //                }
        //                String queryUrl = Constant.cibaUrl + english.toLowerCase();
        //                JSONObject queryResult = new JSONObject();
        //                try {
        //                    queryResult = JsonReader.readJsonFromUrl(queryUrl);
        //                    queryResult
        //                } catch (IOException e) {
        //                    e.printStackTrace();
        //                } catch (JSONException e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //        });
        return builder.create()

    }

    private fun addWord() {
        val english = englishTxt!!.text.toString()
        if (english.isBlank()) {
            toast("英文不能为空")

            return
        }

        val chinese = chineseTxt!!.text.toString()
        if (chinese.isBlank()) {
            toast("中文不能为空")
            return
        }
        val trans = Trans(chinese, "", "", "", "")
        val wordContent = WordContent(SentenceOut(listOf(), ""), "", "", "", SynoOut(listOf(), "")
                , "", "", "", listOf(trans), PhraseOut("", listOf()), RemMethod("", ""), RelWordOut(listOf(), "")
        )
        val dbWord = DbWord(UUID.randomUUID().toString(), "myCollectBook", 0, english, content = Gson().toJson(wordContent), collect = true)
        dbWord.insert()
        toast("成功添加入收藏")
        (activity as WordListActivity).addWordToShow(dbWord)
    }


}
