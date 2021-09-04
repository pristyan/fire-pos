package com.fire.pos.presentation.cashierproductqty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.fire.pos.databinding.DialogCashierProductQtyBinding
import com.fire.pos.util.toIDR
import com.fire.pos.model.view.ProductCart


/**
 * Created by Chandra.
 **/

class CashierProductQtyDialog : DialogFragment() {

    private lateinit var binding: DialogCashierProductQtyBinding

    private var product: ProductCart? = null
    private var action: Action? = null

    var callback: Callback? = null

    companion object {

        private var cashierProductQtyDialog: CashierProductQtyDialog? = null

        fun instance(bundle: Bundle): CashierProductQtyDialog {
            if (cashierProductQtyDialog == null) {
                cashierProductQtyDialog = CashierProductQtyDialog()
            }
            cashierProductQtyDialog?.arguments = bundle
            return cashierProductQtyDialog as CashierProductQtyDialog
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
        binding = DialogCashierProductQtyBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            product = it.getSerializable("product") as? ProductCart?
            action = it.get("action") as Action?
        }

        Glide.with(this).load(product?.productImage).into(binding.imgProduct)
        binding.tvName.text = product?.productName
        binding.tvPrice.text = product?.productPrice?.toIDR()

        binding.tvQty.text = (product?.qty ?: 1).toString()
        binding.tvStock.text = "/${product?.productStock?.toString()}"

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
                if (action == Action.ADD) callback?.onAdd(p)
                else callback?.onUpdate(p)
            }
            dismiss()
        }

        binding.btnDelete.setOnClickListener {
            product?.let { p ->
                p.qty = 0
                callback?.onDelete(p)
            }
            dismiss()
        }

        updateBtnState()
    }

    private fun updateBtnState() {
        binding.btnPlus.isEnabled = (product?.qty ?: 1) < (product?.productStock ?: 0)
        binding.btnMinus.isEnabled = (product?.qty ?: 1) > 0

        if (product?.qty == 0) {
            binding.btnSave.visibility = View.GONE
            binding.btnDelete.visibility = View.VISIBLE
        } else if (binding.btnSave.visibility == View.GONE) {
            binding.btnSave.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.GONE
        }
    }

    enum class Action {
        ADD, UPDATE
    }

    interface Callback {
        fun onAdd(product: ProductCart)
        fun onUpdate(product: ProductCart)
        fun onDelete(product: ProductCart)
    }
}