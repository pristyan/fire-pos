package com.fire.pos.presentation.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fire.pos.presentation.account.AccountFragment
import com.fire.pos.presentation.history.HistoryFragment
import com.fire.pos.presentation.productdetail.ProductDetailFragment
import com.fire.pos.presentation.productlist.ProductListFragment
import com.fire.pos.presentation.transaction.TransactionFragment


/**
 * Created by Chandra.
 **/

class PageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    val offsetLimit: Int
        get() = itemCount - 1

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TransactionFragment()
            1 -> ProductListFragment()
            2 -> HistoryFragment()
            else -> AccountFragment()
        }
    }
}