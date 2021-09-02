package com.fire.pos.presentation.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.constant.AppConstant
import com.fire.pos.databinding.FragmentHistoryBinding
import com.fire.pos.di.appComponent
import com.fire.pos.model.view.Transaction
import com.fire.pos.presentation.history.adapter.HistoryAdapter
import com.fire.pos.presentation.history.di.DaggerHistoryComponent
import com.fire.pos.presentation.history.viewmodel.HistoryViewModel
import com.fire.pos.presentation.history.viewmodel.HistoryViewModelContract
import com.fire.pos.presentation.home.HomeFragmentDirections
import com.fire.pos.util.toast
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HistoryFragment :
    BaseFragment<HistoryViewModel, HistoryViewModelContract, FragmentHistoryBinding>(),
    HistoryView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var historyAdapter: HistoryAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_history
    }

    override fun getViewModelClass(): Class<HistoryViewModel> {
        return HistoryViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.srlHistory.isRefreshing = it
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            context?.toast(it)
        })

        viewModel.historySuccess.observe(viewLifecycleOwner, {
            historyAdapter.setItems(it)
        })
    }

    override fun setupDependencyInjection() {
        DaggerHistoryComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        historyAdapter.callback = object : HistoryAdapter.Callback {
            override fun onItemClick(item: Transaction) {
                navigateToDetail(item.id)
            }
        }

        binding.srlHistory.setOnRefreshListener { getTransactionList() }
        binding.rvHistory.adapter = historyAdapter
    }

    override fun observeBackStack() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>(AppConstant.NEED_REFRESH)
            ?.observe(viewLifecycleOwner, { getTransactionList() })
    }

    override fun navigateToDetail(id: String) {
        val action = HomeFragmentDirections.actionToHistoryDetail(id)
        findNavController().navigate(action)
    }

    override fun getTransactionList() {
        historyAdapter.clearItems()
        viewModel.getTransactionList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeBackStack()
        initView()
        getTransactionList()
    }

}