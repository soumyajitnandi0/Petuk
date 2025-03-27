package com.example.petuk.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.petuk.fragments.SuccessfulOrdersFragment
import com.example.petuk.fragments.FailedOrdersFragment


class HistoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) SuccessfulOrdersFragment() else FailedOrdersFragment()
    }
}
