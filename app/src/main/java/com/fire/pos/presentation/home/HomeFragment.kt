package com.fire.pos.presentation.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentHomeBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.home.adapter.PageAdapter
import com.fire.pos.presentation.home.di.DaggerHomeComponent
import com.fire.pos.presentation.home.viewmodel.HomeViewModel
import com.fire.pos.presentation.home.viewmodel.HomeViewModelContract
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HomeFragment : BaseFragment<HomeViewModel, HomeViewModelContract, FragmentHomeBinding>() {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private lateinit var pageAdapter: PageAdapter

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
        pageAdapter = PageAdapter(requireActivity())
        with(binding.vpHome) {
            adapter = pageAdapter
            isUserInputEnabled = false
            offscreenPageLimit = pageAdapter.offsetLimit
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_transaction -> setActivePage(0)
                R.id.nav_product -> setActivePage(1)
                R.id.nav_history -> setActivePage(2)
                R.id.nav_account -> setActivePage(3)
                else -> false
            }
        }
    }

    private fun setActivePage(position: Int): Boolean {
        binding.vpHome.setCurrentItem(position, false)
        return true
    }

}