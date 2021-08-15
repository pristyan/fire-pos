package com.fire.pos.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentHomeBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.account.AccountFragment
import com.fire.pos.presentation.history.HistoryFragment
import com.fire.pos.presentation.home.di.DaggerHomeComponent
import com.fire.pos.presentation.home.viewmodel.HomeViewModel
import com.fire.pos.presentation.home.viewmodel.HomeViewModelContract
import com.fire.pos.presentation.productlist.ProductListFragment
import com.fire.pos.presentation.transaction.TransactionFragment
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HomeFragment: BaseFragment<HomeViewModel, HomeViewModelContract, FragmentHomeBinding>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val pages: Map<Int, Pair<Fragment, String>> = mapOf(
        R.id.nav_transaction to Pair(TransactionFragment(), "Transaction"),
        R.id.nav_product to Pair(ProductListFragment(), "Product"),
        R.id.nav_history to Pair(HistoryFragment(), "History"),
        R.id.nav_account to Pair(AccountFragment(), "Account")
    )

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {

    }

    override fun setupDependencyInjection() {
        DaggerHomeComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setOnItemSelectedListener {
            val selectedPage = pages[it.itemId]
            selectedPage?.let { map -> setActivePage(map.first, map.second) }
            selectedPage != null
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_transaction
    }

    private fun setActivePage(fragment: Fragment, tag: String) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, tag)
        transaction.commitAllowingStateLoss()
    }

}