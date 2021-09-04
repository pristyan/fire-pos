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
import com.fire.pos.presentation.loadingdialog.LoadingDialogFragment
import com.fire.pos.util.showConfirmationDialog
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountFragment :
    BaseFragment<AccountViewModel, AccountViewModelContract, FragmentAccountBinding>(),
    AccountView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private var loadingDialog: LoadingDialogFragment? = null

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
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) loadingDialog?.show(childFragmentManager, "loading")
            else loadingDialog?.dismiss()
        })

        viewModel.logoutSuccess.observe(viewLifecycleOwner, {
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
        loadingDialog = LoadingDialogFragment.instance()
        binding.btnLogout.setOnClickListener {
            context?.showConfirmationDialog(
                title = R.string.dialog_title_confirmation,
                message = R.string.dialog_msg_logout,
                positiveCallback = { logout() }
            )
        }

        binding.btnCategory.setOnClickListener { navigateToCategory() }
    }

    override fun logout() {
        viewModel.logout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog = null
    }

    override fun navigateToCategory() {
        val action = HomeFragmentDirections.actionToCategoryList()
        findNavController().navigate(action)
    }
}