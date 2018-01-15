package cn.jk.beidanci.home

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.beidanci.R

/**
 * Created by jack on 2018/1/14.
 */
class ReviewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_review, container, false)
        return view
    }
}