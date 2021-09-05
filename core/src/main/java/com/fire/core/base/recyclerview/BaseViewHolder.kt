package com.fire.core.base.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Chandra.
 **/

class BaseViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)