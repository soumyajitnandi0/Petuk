import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.petuk.SuccessfulOrdersFragment
import com.example.petuk.FailedOrdersFragment


class HistoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) SuccessfulOrdersFragment() else FailedOrdersFragment()
    }
}
