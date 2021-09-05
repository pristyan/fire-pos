package com.fire.pos.presentation.cashier

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.core.base.fragment.BaseFragment
import com.fire.pos.constant.AppConstant
import com.fire.pos.databinding.FragmentCashierBinding
import com.fire.pos.di.appComponent
import com.fire.pos.model.view.ProductCart
import com.fire.pos.presentation.home.HomeFragmentDirections
import com.fire.pos.presentation.cashier.adapter.ProductCartListAdapter
import com.fire.pos.presentation.cashier.di.DaggerCashierComponent
import com.fire.pos.presentation.cashier.viewmodel.CashierViewModel
import com.fire.pos.presentation.cashier.viewmodel.CashierViewModelContract
import com.fire.pos.presentation.cashierproductqty.CashierProductQtyDialog
import com.fire.core.util.toast
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CashierFragment :
    BaseFragment<CashierViewModel, CashierViewModelContract, FragmentCashierBinding>(),
    CashierView, ProductCartListAdapter.Callback {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var productAdapter: ProductCartListAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_cashier
    }

    override fun getViewModelClass(): Class<CashierViewModel> {
        return CashierViewModel::class.java
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
        DaggerCashierComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        val searchView = binding.toolbar.menu.getItem(0).actionView as SearchView
        searchView.queryHint = "Product Name"
        searchView.setIconifiedByDefault(true)
        searchView.setOnCloseListener { searchProduct("") }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true
            override fun onQueryTextChange(newText: String?): Boolean = searchProduct(newText)
        })

        productAdapter.callback = this
        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.itemAnimator = null

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
        val action = HomeFragmentDirections.actionToCart()
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
            if (item.qty == 0) CashierProductQtyDialog.Action.ADD
            else CashierProductQtyDialog.Action.UPDATE

        val bundle = bundleOf("product" to item, "action" to action)
        val dialog = CashierProductQtyDialog.instance(bundle)
        dialog.callback = object : CashierProductQtyDialog.Callback {
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

    override fun searchProduct(keyword: String?): Boolean {
        val filteredList = viewModel.productListSuccess.value?.filter {
            it.productName.contains(keyword.orEmpty(), true)
        } ?: emptyList()
        productAdapter.clearItems()
        productAdapter.setItems(filteredList)
        return true
    }

}