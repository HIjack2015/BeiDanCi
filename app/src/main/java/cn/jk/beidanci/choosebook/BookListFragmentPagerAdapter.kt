package cn.jk.beidanci.choosebook


/**
 * Created by jack on 2018/1/11.
 */
class BookListFragmentPagerAdapter(fragmentManager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {
    private var fragmentList = ArrayList<androidx.fragment.app.Fragment>()
    private var titleList = ArrayList<String>()

    fun add(fragment: androidx.fragment.app.Fragment, str: String) {
        this.fragmentList.add(fragment)
        this.titleList.add(str)
    }

    fun clear() {
        fragmentList = ArrayList<androidx.fragment.app.Fragment>()
        titleList = ArrayList<String>()
    }

    fun setTitle(i: Int, str: String) {
        this.titleList[i] = str
    }

    override fun getItem(i: Int): androidx.fragment.app.Fragment? {
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