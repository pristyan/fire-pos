package com.fire.pos.presentation.loadingdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fire.pos.databinding.DialogLoadingBinding


/**
 * Created by Chandra.
 **/

class LoadingDialogFragment : DialogFragment() {

    private lateinit var binding: DialogLoadingBinding

    companion object {
        private var loadingDialogFragment: LoadingDialogFragment? = null

        const val TAG = "LoadingDialogFragment"

        fun instance(): LoadingDialogFragment {
            if (loadingDialogFragment == null) {
                loadingDialogFragment = LoadingDialogFragment()
            }

            return loadingDialogFragment as LoadingDialogFragment
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }
}