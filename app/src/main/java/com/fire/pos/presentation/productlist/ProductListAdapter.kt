package com.fire.pos.presentation.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fire.pos.util.toIDR
import com.fire.pos.data.view.Product
import com.fire.pos.databinding.ListItemProductBinding
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductListAdapter @Inject constructor() : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var callback: ProductListCallback? = null

    private val list: ArrayList<Product> = arrayListOf()

    fun setList(newList: List<Product>) {
        list.addAll(newList)
        notifyItemRangeInserted(0, newList.size)
    }

    fun update(product: Product) {
        val index = list.indexOfLast { it.name == product.name }
        list[index] = product
        notifyItemChanged(index)
    }

    fun clear() {
        notifyItemRangeRemoved(0, list.size)
        list.clear()
    }

    inner class ProductViewHolder(
        private var binding: ListItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            Glide.with(binding.root).load(product.image).into(binding.imgProduct)
            binding.tvName.text = product.name
            binding.tvPrice.text = product.price.toIDR()
            binding.tvQty.text = product.qty?.toString() ?: ""
            binding.root.setOnClickListener {
                callback?.onItemClick(product)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ProductListCallback {
        fun onItemClick(product: Product)
    }

}