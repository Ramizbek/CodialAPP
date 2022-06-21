package ramizbek.aliyev.codialapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val arrayListFragment: ArrayList<Fragment>,
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = arrayListFragment.size

    override fun createFragment(position: Int): Fragment = arrayListFragment[position]

    override fun getItemId(position: Int): Long {
        return arrayListFragment[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return arrayListFragment.find { it.hashCode().toLong() == itemId } != null
    }
}