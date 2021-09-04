package com.fire.pos.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentSplashBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.splash.di.DaggerSplashComponent
import com.fire.pos.presentation.splash.viewmodel.SplashViewModel
import com.fire.pos.presentation.splash.viewmodel.SplashViewModelContract
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class SplashFragment :
    BaseFragment<SplashViewModel, SplashViewModelContract, FragmentSplashBinding>(), SplashView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun getViewModelClass(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isUserLoggedIn.observe(viewLifecycleOwner, { isLogged ->
            val action = when(isLogged) {
                true -> SplashFragmentDirections.actionToHome()
                false -> SplashFragmentDirections.actionToLogin()
            }
            findNavController().navigate(action)
        })
    }

    override fun setupDependencyInjection() {
        DaggerSplashComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun checkUser() {
        viewModel.checkUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUser()
    }
}