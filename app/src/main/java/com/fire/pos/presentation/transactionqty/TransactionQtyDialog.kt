package com.fire.pos.presentation.transactionqty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.fire.pos.util.toIDR
import com.fire.pos.model.view.Product
import com.fire.pos.databinding.DialogTransactionQtyBinding


/**
 * Created by Chandra.
 **/

class TransactionQtyDialog : DialogFragment() {

    private lateinit var binding: DialogTransactionQtyBinding

    private var product: Product? = null

    var callback: Callback? = null

    companion object {

        private var transactionQtyDialog: TransactionQtyDialog? = null

        fun instance(bundle: Bundle): TransactionQtyDialog {
            if (transactionQtyDialog == null) {
                transactionQtyDialog = TransactionQtyDialog()
            }
            transactionQtyDialog?.arguments = bundle
            return transactionQtyDialog as TransactionQtyDialog
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTransactionQtyBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            product = it.getSerializable("product") as? Product?
        }

        Glide.with(this).load(product?.image).into(binding.imgProduct)
        binding.tvName.text = product?.name
        binding.tvPrice.text = product?.price?.toIDR()

        binding.tvQty.text = (product?.qty ?: 1).toString()
        binding.tvStock.text = "/${product?.stock?.toString()}"

        binding.btnPlus.setOnClickListener {
            product?.qty = (product?.qty ?: 1) + 1
            binding.tvQty.text = product?.qty?.toString()
            updateBtnState()
        }

        binding.btnMinus.setOnClickListener {
            product?.qty = (product?.qty ?: 1) - 1
            binding.tvQty.text = product?.qty?.toString()
            updateBtnState()
        }

        binding.btnSave.setOnClickListener {
            product?.let { p ->
                if (p.qty == null) p.qty = 1
                callback?.onSaveQty(p)
            }
            dismiss()
        }

        binding.btnDelete.setOnClickListener {
            product?.let { p ->
                p.qty = null
                callback?.onSaveQty(p)
            }
            dismiss()
        }

        updateBtnState()
    }

    private fun updateBtnState() {
        binding.btnPlus.isEnabled = (product?.qty ?: 1) < (product?.stock ?: 0)
        binding.btnMinus.isEnabled = (product?.qty ?: 1) > 0

        if (product?.qty == 0L) {
            binding.btnSave.visibility = View.GONE
            binding.btnDelete.visibility = View.VISIBLE
        } else if (binding.btnSave.visibility == View.GONE) {
            binding.btnSave.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.GONE
        }
    }

    interface Callback {
        fun onSaveQty(product: Product)
    }
}