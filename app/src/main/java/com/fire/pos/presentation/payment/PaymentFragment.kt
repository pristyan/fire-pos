package com.fire.pos.presentation.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.constant.AppConstant
import com.fire.pos.databinding.FragmentPaymentBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.loadingdialog.LoadingDialogFragment
import com.fire.pos.presentation.payment.di.DaggerPaymentComponent
import com.fire.pos.presentation.payment.viewmodel.PaymentViewModel
import com.fire.pos.presentation.payment.viewmodel.PaymentViewModelContract
import com.fire.pos.util.showConfirmationDialog
import com.fire.pos.util.toIDR
import com.fire.pos.util.toast
import com.google.android.material.chip.Chip
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class PaymentFragment :
    BaseFragment<PaymentViewModel, PaymentViewModelContract, FragmentPaymentBinding>(),
    PaymentView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private var loadingDialog: LoadingDialogFragment? = null

    private var payOptions: ArrayList<Long>? = arrayListOf()

    private lateinit var amountWatcher: TextWatcher

    companion object {
        private const val M_50K = 50_000L
        private const val M_100K = 100_000L
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_payment
    }

    override fun getViewModelClass(): Class<PaymentViewModel> {
        return PaymentViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) loadingDialog?.show(childFragmentManager, "loading")
            else loadingDialog?.dismiss()
        })

        viewModel.cartTotal.observe(viewLifecycleOwner, {
            binding.total = it.toIDR()
            initPayOption()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            context?.toast(it)
        })

        viewModel.paymentSuccess.observe(viewLifecycleOwner, {
            context?.toast("Payment Success")
            navigateToHome()
        })
    }

    override fun setupDependencyInjection() {
        DaggerPaymentComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        loadingDialog = LoadingDialogFragment.instance()
        binding.toolbar.setupNavigationBack { setBackStackState() }
        binding.edtAmount.requestFocus()

        binding.btnPay.setOnClickListener {
            context?.showConfirmationDialog(
                R.string.dialog_title_confirmation,
                R.string.dialog_msg_payment
            ) {
                pay()
            }
        }

        amountWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.cgPayOption.clearCheck()
                calculateChange()
            }

        }

        binding.edtAmount.addTextChangedListener(amountWatcher)
    }

    override fun initPayOption() {
        val total = viewModel.cartTotal.value ?: 0L
        payOptions?.add(total)

        val nearest50 = total + (M_50K - (total % M_50K))
        payOptions?.add(nearest50)

        if (nearest50 % M_100K != 0L) {
            payOptions?.add(nearest50 + M_50K)
        }

        payOptions?.forEach {
            val inflater = LayoutInflater.from(binding.cgPayOption.context)
            (inflater.inflate(R.layout.chip_pay_option, binding.cgPayOption, false) as Chip)
                .apply {
                    id = View.generateViewId()
                    text = it.toIDR()
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            binding.cgPayOption.check(id)
                            binding.edtAmount.removeTextChangedListener(amountWatcher)
                            binding.edtAmount.setText(it.toString())
                            binding.edtAmount.addTextChangedListener(amountWatcher)
                            calculateChange()
                        }
                    }
                }
                .run {
                    binding.cgPayOption.addView(this)
                }
        }
    }

    override fun getCartTotal() {
        viewModel.getCartTotal()
    }

    override fun calculateChange() {
        val total = viewModel.cartTotal.value ?: 0L
        val amount =
            if (binding.edtAmount.text?.toString().isNullOrBlank()) 0L
            else binding.edtAmount.text?.toString()?.toLong() ?: 0L
        val change = amount - total
        binding.btnPay.isEnabled = change >= 0
        binding.edtChange.setText(change.toIDR())
    }

    override fun pay() {
        viewModel.pay()
    }

    override fun setBackStackState() {
        findNavController().previousBackStackEntry
            ?.savedStateHandle
            ?.set(AppConstant.NEED_REFRESH, true)
    }

    override fun observeBackPress() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setBackStackState()
                    findNavController().popBackStack()
                }
            })
    }

    override fun navigateToHome() {
        val action = PaymentFragmentDirections.actionToHome()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeBackPress()
        initView()
        getCartTotal()
    }

    override fun onDestroy() {
        payOptions = null
        super.onDestroy()
    }
}