package cn.jk.beidanci.choosebook

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
 * Created by jack on 2018/1/11.
 */
class BookListFragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private var fragmentList = ArrayList<Fragment>()
    private var titleList = ArrayList<String>()

    fun add(fragment: Fragment, str: String) {
        this.fragmentList.add(fragment)
        this.titleList.add(str)
    }

    fun clear() {
        fragmentList = ArrayList<Fragment>()
        titleList = ArrayList<String>()
    }

    fun setTitle(i: Int, str: String) {
        this.titleList.set(i, str)
    }

    override fun getItem(i: Int): Fragment? {
        return if (i >= this.fragmentList.size) {
            null
        } else this.fragmentList.get(i)
    }

    override fun getCount(): Int {
        return this.fragmentList.size
    }

    override fun getPageTitle(i: Int): CharSequence {
        return this.titleList.get(i)
    }
}