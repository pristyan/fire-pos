package com.fire.pos.presentation.transaction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.fire.pos.model.view.Product
import com.fire.pos.presentation.productlist.ProductListAdapter
import com.fire.pos.presentation.transactionqty.TransactionQtyDialog


/**
 * Created by Chandra.
 **/

class TransactionFragment: Fragment(), ProductListAdapter.ProductListCallback {

    private val productListAdapter by lazy { ProductListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.rvProduct.layoutManager = GridLayoutManager(context, 2)
        //binding.rvProduct.adapter = productListAdapter

        productListAdapter.clear()


        /*binding.edtSearch.doAfterTextChanged {
            search(it.toString())
        }*/

        updateSummaryState()
    }

    private fun search(keyword: String) {
        /*val filteredList = products.filter {
            it.name.contains(keyword, true)
        }
        productListAdapter.clear()
        productListAdapter.setList(filteredList)*/
    }

    private fun updateSummaryState() {
        /*val totalPrice = products.sumOf { it.price * (it.qty ?: 0) }
        if (totalPrice > 0) {
            binding.containerSummary.visibility = View.VISIBLE
            binding.tvPriceTotal.text = totalPrice.toIDR()
        } else {
            binding.containerSummary.visibility = View.GONE
            binding.tvPriceTotal.text = 0L.toIDR()
        }*/
    }

    override fun onItemClick(product: Product) {
        val bundle = bundleOf("product" to product)
        val dialog = TransactionQtyDialog.instance(bundle)
        dialog.callback = object : TransactionQtyDialog.Callback {
            override fun onSaveQty(product: Product) {
                /*productListAdapter.update(product)
                products.findLast { it.name == product.name }.apply {
                    this?.qty = product.qty
                }
                updateSummaryState()*/
            }
        }
        dialog.show(parentFragmentManager, "QtyDialog")
    }

}