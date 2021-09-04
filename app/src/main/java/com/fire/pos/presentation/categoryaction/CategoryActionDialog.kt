package com.fire.pos.presentation.categoryaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fire.pos.databinding.DialogCategoryActionBinding
import com.fire.pos.model.view.Category
import com.fire.pos.util.toast


/**
 * Created by Chandra.
 **/

class CategoryActionDialog : DialogFragment() {

    private lateinit var binding: DialogCategoryActionBinding
    var callback: Callback? = null

    private var category: Category? = null

    companion object {
        private var categoryActionDialog: CategoryActionDialog? = null

        fun instance(bundle: Bundle? = null): CategoryActionDialog {
            if (categoryActionDialog == null) {
                categoryActionDialog = CategoryActionDialog()
            }
            categoryActionDialog?.arguments = bundle
            return categoryActionDialog as CategoryActionDialog
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
        binding = DialogCategoryActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            category = it.getSerializable("category") as? Category?
        }

        binding.isUpdate = category != null
        category?.let { binding.data = it }

        binding.btnDelete.setOnClickListener {
            category?.let { callback?.onDelete(it) }
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.edtCategory.text.toString()
            if (name.isBlank()) {
                context?.toast("Name can not be empty")
                return@setOnClickListener
            }

            when (category) {
                null -> callback?.onAdd(binding.edtCategory.text.toString())
                else -> callback?.onUpdate((category as Category).copy(name = name))
            }

            dismiss()
        }
    }

    interface Callback {
        fun onAdd(name: String)
        fun onUpdate(category: Category)
        fun onDelete(category: Category)
    }
}