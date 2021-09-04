package com.fire.pos.presentation.categorylist.adapter

import com.fire.pos.R
import com.fire.pos.base.recyclerview.BaseRecyclerAdapter
import com.fire.pos.databinding.ListItemCategoryBinding
import com.fire.pos.model.view.Category
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CategoryListAdapter @Inject constructor() :
    BaseRecyclerAdapter<Category, ListItemCategoryBinding>() {

    var callback: Callback? = null

    override fun getLayoutId(): Int {
        return R.layout.list_item_category
    }

    override fun onBind(binding: ListItemCategoryBinding, position: Int, item: Category) {
        binding.data = item
        binding.root.setOnClickListener { callback?.onItemClick(item) }
        binding.executePendingBindings()
    }

    interface Callback {
        fun onItemClick(item: Category)
    }
}