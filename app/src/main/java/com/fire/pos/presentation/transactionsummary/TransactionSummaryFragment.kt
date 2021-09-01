package com.fire.pos.presentation.transactionsummary

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.constant.AppConstant
import com.fire.pos.databinding.FragmentTransactionSummaryBinding
import com.fire.pos.di.appComponent
import com.fire.pos.model.view.ProductCart
import com.fire.pos.presentation.transactionsummary.adapter.TransactionSummaryAdapter
import com.fire.pos.presentation.transactionsummary.di.DaggerTransactionSummaryComponent
import com.fire.pos.presentation.transactionsummary.viewmodel.TransactionSummaryViewModel
import com.fire.pos.presentation.transactionsummary.viewmodel.TransactionSummaryViewModelContract
import com.fire.pos.util.showConfirmationDialog
import com.fire.pos.util.toast
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionSummaryFragment :
    BaseFragment<TransactionSummaryViewModel, TransactionSummaryViewModelContract, FragmentTransactionSummaryBinding>(),
    TransactionSummaryView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var transactionSummaryAdapter: TransactionSummaryAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_transaction_summary
    }

    override fun getViewModelClass(): Class<TransactionSummaryViewModel> {
        return TransactionSummaryViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.srlCart.isRefreshing = it
        })

        viewModel.cartSuccess.observe(viewLifecycleOwner, {
            transactionSummaryAdapter.setItems(it)
        })

        viewModel.updateCartSuccess.observe(viewLifecycleOwner, {
            transactionSummaryAdapter.update(it)
        })

        viewModel.deleteCartSuccess.observe(viewLifecycleOwner, {
            transactionSummaryAdapter.delete(it)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            context?.toast(it)
        })

        viewModel.totalUpdate.observe(viewLifecycleOwner, {
            binding.subtotal = it
        })
    }

    override fun setupDependencyInjection() {
        DaggerTransactionSummaryComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.toolbar.setupNavigationBack { setBackStackState() }

        binding.btnAddMore.setOnClickListener {
            setBackStackState()
            findNavController().popBackStack()
        }

        binding.btnPay.setOnClickListener { navigateToPayment() }

        binding.srlCart.setOnRefreshListener { getCartList() }

        transactionSummaryAdapter.callback = object : TransactionSummaryAdapter.Callback {
            override fun onPlus(item: ProductCart) {
                viewModel.updateCart(item)
            }

            override fun onMinus(item: ProductCart) {
                viewModel.updateCart(item)
            }

            override fun onRemove(position: Int, item: ProductCart) {
                context?.showConfirmationDialog(
                    R.string.dialog_title_confirmation,
                    R.string.dialog_msg_delete_product
                ) {
                    viewModel.deleteCart(item)
                }
            }
        }

        binding.rvCart.adapter = transactionSummaryAdapter
        binding.rvCart.itemAnimator?.changeDuration = 0L
    }

    override fun getCartList() {
        viewModel.getCartList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeNavigation()
        getCartList()
    }

    override fun setBackStackState() {
        findNavController().previousBackStackEntry
            ?.savedStateHandle
            ?.set(AppConstant.NEED_REFRESH, true)
    }

    override fun observeNavigation() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setBackStackState()
                    findNavController().popBackStack()
                }
            })
    }

    override fun navigateToPayment() {
        val action = TransactionSummaryFragmentDirections.actionToPayment()
        findNavController().navigate(action)
    }
}