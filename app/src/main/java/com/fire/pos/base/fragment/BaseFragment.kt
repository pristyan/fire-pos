package com.fire.pos.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.base.viewmodel.BaseViewModel
import com.fire.pos.base.viewmodel.BaseViewModelContract
import com.google.android.material.appbar.MaterialToolbar


/**
 * Created by Chandra.
 **/

abstract class BaseFragment<VM : BaseViewModel, VMC : BaseViewModelContract, VB : ViewDataBinding> :
    Fragment() {

    lateinit var binding: VB

    private lateinit var _viewModel: VM

    @Suppress("UNCHECKED_CAST")
    val viewModel: VMC
        get() = _viewModel as VMC

    abstract var viewModelProviderFactory: ViewModelProvider.Factory

    abstract fun getLayoutId(): Int

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getViewModelFactory(): ViewModelProvider.Factory

    abstract fun subscribeLiveData()

    abstract fun setupDependencyInjection()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    fun MaterialToolbar.setupNavigationBack(beforeBack: (() -> Unit)? = null) {
        setNavigationOnClickListener {
            beforeBack?.invoke()
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@BaseFragment
            executePendingBindings()
        }
        subscribeLiveData()
    }

    override fun onAttach(context: Context) {
        setupDependencyInjection()
        super.onAttach(context)
        _viewModel = ViewModelProvider(this, getViewModelFactory()).get(getViewModelClass())
    }
}