package com.fire.pos.presentation.categorylist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.R
import com.fire.pos.base.fragment.BaseFragment
import com.fire.pos.databinding.FragmentCategoryListBinding
import com.fire.pos.di.appComponent
import com.fire.pos.model.view.Category
import com.fire.pos.presentation.categoryaction.CategoryActionDialog
import com.fire.pos.presentation.categorylist.adapter.CategoryListAdapter
import com.fire.pos.presentation.categorylist.di.DaggerCategoryListComponent
import com.fire.pos.presentation.categorylist.viewmodel.CategoryListViewModel
import com.fire.pos.presentation.categorylist.viewmodel.CategoryListViewModelContract
import com.fire.pos.presentation.loadingdialog.LoadingDialogFragment
import com.fire.pos.util.toast
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CategoryListFragment :
    BaseFragment<CategoryListViewModel, CategoryListViewModelContract, FragmentCategoryListBinding>(),
    CategoryListView {

    @Inject
    override lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private var loadingDialog: LoadingDialogFragment? = null

    private var categoryActionDialog: CategoryActionDialog? = null

    @Inject
    lateinit var categoryListAdapter: CategoryListAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_category_list
    }

    override fun getViewModelClass(): Class<CategoryListViewModel> {
        return CategoryListViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.srlCategory.isRefreshing = it
        })

        viewModel.updateLoading.observe(viewLifecycleOwner, {
            if (it) loadingDialog?.show(childFragmentManager, "loading")
            else loadingDialog?.dismiss()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            context?.toast(it)
        })

        viewModel.updateSuccess.observe(viewLifecycleOwner, {
            context?.toast(it)
            getCategoryList()
        })

        viewModel.categoryList.observe(viewLifecycleOwner, {
            binding.isEmpty = it.isEmpty()
            categoryListAdapter.setItems(it)
        })
    }

    override fun setupDependencyInjection() {
        DaggerCategoryListComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun initView() {
        binding.toolbar.setupNavigationBack()
        categoryListAdapter.callback = object : CategoryListAdapter.Callback {
            override fun onItemClick(item: Category) = showCategory(item)
        }

        binding.rvCategory.adapter = categoryListAdapter
        binding.rvCategory.itemAnimator = null
        binding.fabAdd.setOnClickListener { addCategory() }
        binding.srlCategory.setOnRefreshListener { getCategoryList() }

        loadingDialog = LoadingDialogFragment.instance()

        categoryActionDialog = CategoryActionDialog.instance()
        categoryActionDialog?.callback = object : CategoryActionDialog.Callback {
            override fun onAdd(name: String) {
                viewModel.insertCategory(name)
            }

            override fun onUpdate(category: Category) {
                viewModel.updateCategory(category)
            }

            override fun onDelete(category: Category) {
                viewModel.deleteCategory(category.id)
            }
        }
    }

    override fun getCategoryList() {
        viewModel.getCategoryList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getCategoryList()
    }

    override fun addCategory() {
        categoryActionDialog = CategoryActionDialog.instance(bundleOf("category" to null))
        categoryActionDialog?.show(childFragmentManager, "add")
    }

    override fun showCategory(item: Category) {
        categoryActionDialog = CategoryActionDialog.instance(bundleOf("category" to item))
        categoryActionDialog?.show(childFragmentManager, "update")
    }
}