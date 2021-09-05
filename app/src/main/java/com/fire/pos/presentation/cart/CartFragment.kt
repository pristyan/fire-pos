package com.fire.pos.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.core.base.fragment.BaseFragment
import com.fire.pos.constant.AppConstant
import com.fire.pos.databinding.FragmentCartBinding
import com.fire.pos.di.appComponent
import com.fire.pos.model.view.ProductCart
import com.fire.pos.presentation.cart.adapter.CartAdapter
import com.fire.pos.presentation.cart.di.DaggerCartComponent
import com.fire.pos.presentation.cart.viewmodel.CartViewModel
import com.fire.pos.presentation.cart.viewmodel.CartViewModelContract
import com.fire.core.util.showConfirmationDialog
import com.fire.core.util.toast
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CartFragment :
    BaseFragment<CartViewModel, CartViewModelContract, FragmentCartBinding>(),
    CartView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var cartAdapter: CartAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_cart
    }

    override fun getViewModelClass(): Class<CartViewModel> {
        return CartViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.srlCart.isRefreshing = it
        })

        viewModel.cartSuccess.observe(viewLifecycleOwner, {
            cartAdapter.setItems(it)
        })

        viewModel.updateCartSuccess.observe(viewLifecycleOwner, {
            cartAdapter.update(it)
        })

        viewModel.deleteCartSuccess.observe(viewLifecycleOwner, {
            cartAdapter.delete(it)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            context?.toast(it)
        })

        viewModel.totalUpdate.observe(viewLifecycleOwner, {
            binding.subtotal = it
        })
    }

    override fun setupDependencyInjection() {
        DaggerCartComponent.builder()
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

        cartAdapter.callback = object : CartAdapter.Callback {
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

        binding.rvCart.adapter = cartAdapter
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
        val action = CartFragmentDirections.actionToPayment()
        findNavController().navigate(action)
    }
}