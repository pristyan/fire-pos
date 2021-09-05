package com.fire.core.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.base.viewmodel.BaseViewModelContract


/**
 * Created by Chandra.
 **/

abstract class BaseActivity<VM : BaseViewModel, VMC : BaseViewModelContract, VB : ViewDataBinding> :
    AppCompatActivity() {

    lateinit var binding: VB

    private lateinit var _viewModel: VM

    @Suppress("UNCHECKED_CAST")
    val viewModel: VMC
        get() = _viewModel as VMC

    abstract fun getLayoutId(): Int

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getViewModelFactory(): ViewModelProvider.Factory

    abstract fun subscribeLiveData()

    abstract fun setupDependencyInjection()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupDependencyInjection()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        _viewModel = ViewModelProvider(this, getViewModelFactory()).get(getViewModelClass())

        subscribeLiveData()
    }
}