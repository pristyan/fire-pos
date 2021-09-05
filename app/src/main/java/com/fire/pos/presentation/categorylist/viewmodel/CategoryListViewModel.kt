package com.fire.pos.presentation.categorylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.pos.domain.categorylist.CategoryListInteractor
import com.fire.core.model.Result
import com.fire.pos.model.view.Category
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CategoryListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val categoryListInteractor: CategoryListInteractor
): BaseViewModel(), CategoryListViewModelContract {

    private val _categoryList = MutableLiveData<List<Category>>()
    override val categoryList: LiveData<List<Category>>
        get() = _categoryList

    private val _updateSuccess = MutableLiveData<String>()
    override val updateSuccess: LiveData<String>
        get() = _updateSuccess

    private val _updateLoading = MutableLiveData<Boolean>()
    override val updateLoading: LiveData<Boolean>
        get() = _updateLoading

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override fun getCategoryList(): Job = launch(schedulerProvider.ui()) {
        setLoading(true)
        val result = withContext(schedulerProvider.io()) {
            categoryListInteractor.getCategoryList()
        }
        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _categoryList.value = result.data
        }
        setLoading(false)
    }

    override fun insertCategory(name: String): Job = launch(schedulerProvider.ui()) {
        _updateLoading.value = true
        val result = withContext(schedulerProvider.io()) {
            categoryListInteractor.insertCategory(name)
        }
        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _updateSuccess.value = "Category added"
        }
        _updateLoading.value = false
    }

    override fun updateCategory(category: Category): Job = launch(schedulerProvider.ui()) {
        _updateLoading.value = true
        val result = withContext(schedulerProvider.io()) {
            categoryListInteractor.updateCategory(category)
        }
        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _updateSuccess.value = "Category updated"
        }
        _updateLoading.value = false
    }

    override fun deleteCategory(id: String): Job = launch(schedulerProvider.ui()) {
        _updateLoading.value = true
        val result = withContext(schedulerProvider.io()) {
            categoryListInteractor.deleteCategory(id)
        }
        when (result) {
            is Result.Error -> _errorMessage.value = result.message
            is Result.Success -> _updateSuccess.value = "Category deleted"
        }
        _updateLoading.value = false
    }
}