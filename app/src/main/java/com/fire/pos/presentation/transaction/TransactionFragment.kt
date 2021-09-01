package com.fire.pos.presentation.transaction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.constant.AppConstant
import com.fire.pos.databinding.FragmentTransactionBinding
import com.fire.pos.di.appComponent
import com.fire.pos.model.view.ProductCart
import com.fire.pos.presentation.home.HomeFragmentDirections
import com.fire.pos.presentation.transaction.adapter.ProductCartListAdapter
import com.fire.pos.presentation.transaction.di.DaggerTransactionComponent
import com.fire.pos.presentation.transaction.viewmodel.TransactionViewModel
import com.fire.pos.presentation.transaction.viewmodel.TransactionViewModelContract
import com.fire.pos.presentation.transactionqty.TransactionQtyDialog
import com.fire.pos.util.toast
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionFragment :
    BaseFragment<TransactionViewModel, TransactionViewModelContract, FragmentTransactionBinding>(),
    TransactionView, ProductCartListAdapter.Callback {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var productAdapter: ProductCartListAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_transaction
    }

    override fun getViewModelClass(): Class<TransactionViewModel> {
        return TransactionViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.srlTransaction.isRefreshing = it
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            context?.toast(it)
        })

        viewModel.productListSuccess.observe(viewLifecycleOwner, {
            productAdapter.setItems(it)
        })

        viewModel.addCartSuccess.observe(viewLifecycleOwner, {
            productAdapter.update(it)
        })

        viewModel.updateCartSuccess.observe(viewLifecycleOwner, {
            productAdapter.update(it)
        })

        viewModel.deleteCartSuccess.observe(viewLifecycleOwner, {
            productAdapter.update(it)
        })

        viewModel.showCartModal.observe(this, {
            binding.showSum = it.first
            binding.total = it.second
        })
    }

    override fun setupDependencyInjection() {
        DaggerTransactionComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        productAdapter.callback = this
        binding.rvProduct.adapter = productAdapter
        binding.srlTransaction.setOnRefreshListener { getProductList() }
        binding.btnSummary.setOnClickListener { navigateToSummary() }
    }

    override fun observeNavigation() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>(AppConstant.NEED_REFRESH)
            ?.observe(viewLifecycleOwner, { getProductList() })
    }

    override fun navigateToSummary() {
        val action = HomeFragmentDirections.actionToSummary()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeNavigation()
        getProductList()
    }

    override fun getProductList() {
        productAdapter.clearItems()
        viewModel.getProductList()
    }

    override fun onItemClick(item: ProductCart) {
        val action =
            if (item.qty == 0) TransactionQtyDialog.Action.ADD
            else TransactionQtyDialog.Action.UPDATE

        val bundle = bundleOf("product" to item, "action" to action)
        val dialog = TransactionQtyDialog.instance(bundle)
        dialog.callback = object : TransactionQtyDialog.Callback {
            override fun onAdd(product: ProductCart) {
                viewModel.addCart(product)
            }

            override fun onUpdate(product: ProductCart) {
                viewModel.updateCart(product)
            }

            override fun onDelete(product: ProductCart) {
                viewModel.deleteCart(product)
            }
        }
        dialog.show(childFragmentManager, "dialog")
    }

}