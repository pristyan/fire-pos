package com.fire.pos.presentation.productlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.data.view.Product
import com.fire.pos.databinding.FragmentProductListBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.home.HomeFragmentDirections
import com.fire.pos.presentation.productlist.di.DaggerProductListComponent
import com.fire.pos.presentation.productlist.viewmodel.ProductListViewModel
import com.fire.pos.presentation.productlist.viewmodel.ProductListViewModelContract
import com.fire.pos.util.visible
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductListFragment :
    BaseFragment<ProductListViewModel, ProductListViewModelContract, FragmentProductListBinding>(),
    ProductListView {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var productListAdapter: ProductListAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_product_list
    }

    override fun getViewModelClass(): Class<ProductListViewModel> {
        return ProductListViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun setupDependencyInjection() {
        DaggerProductListComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(this, {
            binding.srlProduct.isRefreshing = it
        })

        viewModel.productListSuccess.observe(this, {
            binding.tvEmpty.visible(it.isEmpty())
            binding.rvProduct.visible(it.isNotEmpty())
            productListAdapter.clear()
            productListAdapter.setList(it)
        })

        viewModel.productListError.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun initView() {
        productListAdapter.callback = object : ProductListAdapter.ProductListCallback {
            override fun onItemClick(product: Product) {
                navigateToProductDetail(product)
            }
        }

        productListAdapter.clear()
        binding.rvProduct.adapter = productListAdapter

        binding.edtSearch.doAfterTextChanged {
            searchProduct(it.toString())
        }

        binding.fabAdd.setOnClickListener {
            navigateToAddProduct()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getProductList()
    }

    override fun getProductList() {
        viewModel.getProductList()
    }

    override fun searchProduct(keyword: String) {
        /*val filteredList = products.filter {
            it.name.contains(keyword, true)
        }
        productListAdapter.clear()
        productListAdapter.setList(filteredList)*/
    }

    override fun navigateToAddProduct() {
        val action = HomeFragmentDirections.actionToProductDetail(false, null)
        findNavController().navigate(action)
    }

    override fun navigateToProductDetail(product: Product) {
        val action = HomeFragmentDirections.actionToProductDetail(true, product)
        findNavController().navigate(action)
    }
}