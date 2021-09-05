package com.fire.pos.presentation.categorylist.viewmodel

import androidx.lifecycle.LiveData
import com.fire.core.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.Category
import kotlinx.coroutines.Job


/**
 * Created by Chandra.
 **/

interface CategoryListViewModelContract : BaseViewModelContract {

    val categoryList: LiveData<List<Category>>

    val updateSuccess: LiveData<String>

    val updateLoading: LiveData<Boolean>

    val errorMessage: LiveData<String>

    fun getCategoryList(): Job

    fun insertCategory(name: String): Job

    fun updateCategory(category: Category): Job

    fun deleteCategory(id: String): Job

}