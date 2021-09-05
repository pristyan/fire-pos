package com.fire.pos.presentation.transactiondetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.R
import com.fire.core.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentTransactionDetailBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.transactiondetail.adapter.TransactionItemAdapter
import com.fire.pos.presentation.transactiondetail.di.DaggerTransactionDetailComponent
import com.fire.pos.presentation.transactiondetail.viewmodel.TransactionDetailViewModel
import com.fire.pos.presentation.transactiondetail.viewmodel.TransactionDetailViewModelContract
import com.fire.core.util.toast
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionDetailFragment :
    BaseFragment<TransactionDetailViewModel, TransactionDetailViewModelContract, FragmentTransactionDetailBinding>(),
    TransactionDetailView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var transactionItemAdapter: TransactionItemAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_transaction_detail
    }

    override fun getViewModelClass(): Class<TransactionDetailViewModel> {
        return TransactionDetailViewModel::class.java
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
            transactionItemAdapter.setItems(it.items)
        })
    }

    override fun setupDependencyInjection() {
        DaggerTransactionDetailComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.toolbar.setupNavigationBack()
        binding.srlDetail.setOnRefreshListener { getTransactionDetail() }
        binding.rvProduct.adapter = transactionItemAdapter
    }

    override fun getTransactionId(): String {
        return arguments?.run {
            TransactionDetailFragmentArgs.fromBundle(this).transactionId
        }.orEmpty()
    }

    override fun getTransactionDetail() {
        transactionItemAdapter.clearItems()
        viewModel.getTransactionDetail(getTransactionId())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getTransactionDetail()
    }
}