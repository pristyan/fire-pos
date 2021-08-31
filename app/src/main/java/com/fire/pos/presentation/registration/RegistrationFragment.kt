package com.fire.pos.presentation.registration

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentRegistrationBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.loadingdialog.LoadingDialogFragment
import com.fire.pos.presentation.registration.di.DaggerRegistrationComponent
import com.fire.pos.presentation.registration.viewmodel.RegistrationViewModel
import com.fire.pos.presentation.registration.viewmodel.RegistrationViewModelContract
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class RegistrationFragment :
    BaseFragment<RegistrationViewModel, RegistrationViewModelContract, FragmentRegistrationBinding>(),
    RegistrationView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private var loadingDialog: LoadingDialogFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_registration
    }

    override fun getViewModelClass(): Class<RegistrationViewModel> {
        return RegistrationViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(this, {
            if (it) loadingDialog?.show(childFragmentManager, LoadingDialogFragment.TAG)
            else loadingDialog?.dismiss()
        })

        viewModel.isRegistrationSuccess.observe(this, {
            Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show()
            navigateToMain()
        })

        viewModel.isRegistrationError.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun setupDependencyInjection() {
        DaggerRegistrationComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnRegistration.setOnClickListener { register() }
        loadingDialog = LoadingDialogFragment.instance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun register() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val storeName = binding.edtStoreName.text.toString()
        viewModel.registration(email, password, storeName)
    }

    override fun navigateToMain() {
        val action = RegistrationFragmentDirections.actionToHome()
        findNavController().navigate(action)
    }
}