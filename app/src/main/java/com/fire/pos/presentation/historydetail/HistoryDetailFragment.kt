package com.fire.pos.presentation.historydetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentHistoryDetailBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.historydetail.adapter.HistoryProductAdapter
import com.fire.pos.presentation.historydetail.di.DaggerHistoryDetailComponent
import com.fire.pos.presentation.historydetail.viewmodel.HistoryDetailViewModel
import com.fire.pos.presentation.historydetail.viewmodel.HistoryDetailViewModelContract
import com.fire.pos.util.toast
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HistoryDetailFragment :
    BaseFragment<HistoryDetailViewModel, HistoryDetailViewModelContract, FragmentHistoryDetailBinding>(),
    HistoryDetailView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var historyProductAdapter: HistoryProductAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_history_detail
    }

    override fun getViewModelClass(): Class<HistoryDetailViewModel> {
        return HistoryDetailViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.srlDetail.isRefreshing = it
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            context?.toast(it)
        })

        viewModel.detailSuccess.observe(viewLifecycleOwner, {
            binding.data = it
            historyProductAdapter.setItems(it.items)
        })
    }

    override fun setupDependencyInjection() {
        DaggerHistoryDetailComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.toolbar.setupNavigationBack()
        binding.srlDetail.setOnRefreshListener { getTransactionDetail() }
        binding.rvProduct.adapter = historyProductAdapter
    }

    override fun getTransactionId(): String {
        return arguments?.run {
            HistoryDetailFragmentArgs.fromBundle(this).transactionId
        }.orEmpty()
    }

    override fun getTransactionDetail() {
        historyProductAdapter.clearItems()
        viewModel.getTransactionDetail(getTransactionId())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getTransactionDetail()
    }
}