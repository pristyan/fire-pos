package com.fire.pos.presentation.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentLoginBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.loadingdialog.LoadingDialogFragment
import com.fire.pos.presentation.login.di.DaggerLoginComponent
import com.fire.pos.presentation.login.viewmodel.LoginViewModel
import com.fire.pos.presentation.login.viewmodel.LoginViewModelContract
import com.fire.pos.util.toast
import javax.inject.Inject

/**
 * Created by Chandra.
 **/

class LoginFragment: BaseFragment<LoginViewModel, LoginViewModelContract, FragmentLoginBinding>(), LoginView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private var loadingDialog: LoadingDialogFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(this, {
            if (it) loadingDialog?.show(childFragmentManager, LoadingDialogFragment.TAG)
            else loadingDialog?.dismiss()
        })

        viewModel.isUserLoggedIn.observe(this, { loggedIn ->
            if (loggedIn) navigateToMain()
        })

        viewModel.loginSuccess.observe(this, {
            navigateToMain()
        })

        viewModel.loginError.observe(this, {
            context?.toast(it)
        })
    }

    override fun setupDependencyInjection() {
        DaggerLoginComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        checkUserSession()
    }

    override fun initView() {
        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.btnRegistration.setOnClickListener {
            navigateToRegistration()
        }

        loadingDialog = LoadingDialogFragment.instance()
    }

    override fun checkUserSession() {
        viewModel.checkLoggedInUser()
    }

    override fun login() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        viewModel.login(email, password)
    }

    override fun navigateToRegistration() {
        val action = LoginFragmentDirections.actionToRegistration()
        findNavController().navigate(action)
    }

    override fun navigateToMain() {
        val action = LoginFragmentDirections.actionToHome()
        findNavController().navigate(action)
    }
}