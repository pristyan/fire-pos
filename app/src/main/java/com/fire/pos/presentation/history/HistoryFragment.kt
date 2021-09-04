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
import com.fire.pos.util.getStringDate
import com.fire.pos.util.toast
import com.fire.pos.widget.DatePickerDialogFragment
import java.util.*
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
        val action = HomeFragmentDirections.actionToHistoryDetail(id)
        findNavController().navigate(action)
    }

    override fun getTransactionList() {
        historyAdapter.clearItems()
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