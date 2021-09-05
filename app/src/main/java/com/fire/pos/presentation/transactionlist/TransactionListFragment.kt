package com.fire.pos.presentation.transactionlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fire.pos.R
import com.fire.core.base.fragment.BaseFragment
import com.fire.pos.constant.AppConstant
import com.fire.pos.databinding.FragmentTransactionListBinding
import com.fire.pos.di.appComponent
import com.fire.pos.model.view.Transaction
import com.fire.pos.presentation.transactionlist.adapter.TransactionListAdapter
import com.fire.pos.presentation.transactionlist.di.DaggerTransactionListComponent
import com.fire.pos.presentation.transactionlist.viewmodel.TransactionListViewModel
import com.fire.pos.presentation.transactionlist.viewmodel.TransactionListViewModelContract
import com.fire.pos.presentation.home.HomeFragmentDirections
import com.fire.core.util.getStringDate
import com.fire.core.util.toast
import com.fire.core.widget.DatePickerDialogFragment
import java.util.*
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionListFragment :
    BaseFragment<TransactionListViewModel, TransactionListViewModelContract, FragmentTransactionListBinding>(),
    TransactionListView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var transactionListAdapter: TransactionListAdapter

    private var startDateCalendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    private var endDateCalendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
    }

    private val startDate: String
        get() = startDateCalendar.time.getStringDate()

    private val endDate: String
        get() = endDateCalendar.time.getStringDate()

    private var datePickerDialog: DatePickerDialogFragment? = null

    companion object {
        private const val START_DATE = "START_DATE"
        private const val END_DATE = "END_DATE"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_transaction_list
    }

    override fun getViewModelClass(): Class<TransactionListViewModel> {
        return TransactionListViewModel::class.java
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
            transactionListAdapter.setItems(it)
        })
    }

    override fun setupDependencyInjection() {
        DaggerTransactionListComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        transactionListAdapter.callback = object : TransactionListAdapter.Callback {
            override fun onItemClick(item: Transaction) {
                navigateToDetail(item.id)
            }
        }

        binding.srlHistory.setOnRefreshListener { getTransactionList() }
        binding.rvHistory.adapter = transactionListAdapter

        binding.startDate = startDate
        binding.endDate = endDate

        datePickerDialog = DatePickerDialogFragment.instance()
        datePickerDialog?.callback = object : DatePickerDialogFragment.Callback {
            override fun onDateSet(year: Int, month: Int, dayOfMonth: Int, tag: String?) {
                when (tag) {
                    START_DATE -> {
                        startDateCalendar.set(year, month, dayOfMonth, 0, 0, 0)
                        binding.startDate = startDate
                    }
                    END_DATE -> {
                        endDateCalendar.set(year, month, dayOfMonth, 23, 59, 59)
                        binding.endDate = endDate
                    }
                }

                getTransactionList()
            }
        }

        binding.edtStartDate.setOnClickListener { chooseStartDate() }
        binding.edtEndDate.setOnClickListener { chooseEndDate() }
    }

    override fun observeBackStack() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>(AppConstant.NEED_REFRESH)
            ?.observe(viewLifecycleOwner, { getTransactionList() })
    }

    override fun navigateToDetail(id: String) {
        val action = HomeFragmentDirections.actionToTransactionDetail(id)
        findNavController().navigate(action)
    }

    override fun getTransactionList() {
        transactionListAdapter.clearItems()
        viewModel.getTransactionList(startDateCalendar.time, endDateCalendar.time)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeBackStack()
        initView()
        getTransactionList()
    }

    override fun chooseStartDate() {
        datePickerDialog?.showDialog(
            childFragmentManager,
            START_DATE,
            startDateCalendar,
            null,
            endDateCalendar
        )
    }

    override fun chooseEndDate() {
        datePickerDialog?.showDialog(
            childFragmentManager,
            END_DATE,
            endDateCalendar,
            startDateCalendar,
            Calendar.getInstance()
        )
    }
}