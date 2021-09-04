package com.fire.pos.presentation.categorylist

import com.fire.pos.model.view.Category


/**
 * Created by Chandra.
 **/

interface CategoryListView {

    fun initView()

    fun getCategoryList()

    fun addCategory()

    fun showCategory(item: Category)

}