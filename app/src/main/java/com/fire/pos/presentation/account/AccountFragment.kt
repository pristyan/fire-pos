package com.fire.pos.presentation.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentAccountBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.account.di.DaggerAccountComponent
import com.fire.pos.presentation.account.viewmodel.AccountViewModel
import com.fire.pos.presentation.account.viewmodel.AccountViewModelContract
import com.fire.pos.presentation.home.HomeFragmentDirections
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountFragment :
    BaseFragment<AccountViewModel, AccountViewModelContract, FragmentAccountBinding>(),
    AccountView {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    override fun getLayoutId(): Int {
        return R.layout.fragment_account
    }

    override fun getViewModelClass(): Class<AccountViewModel> {
        return AccountViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.logoutSuccess.observe(this, {
            val action = HomeFragmentDirections.actionToLogin()
            findNavController().navigate(action)
        })
    }

    override fun setupDependencyInjection() {
        DaggerAccountComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    override fun logout() {
        viewModel.logout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

}