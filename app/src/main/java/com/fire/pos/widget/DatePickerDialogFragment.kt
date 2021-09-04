package com.fire.pos.widget

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*


/**
 * Created by Chandra.
 **/

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var callback: Callback? = null
    private var selectedDate: Calendar? = null
    private var maximumDate: Calendar? = null
    private var minimumDate: Calendar? = null

    companion object {

        private var datePickerDialogFragment: DatePickerDialogFragment? = null

        fun instance(): DatePickerDialogFragment {
            if (datePickerDialogFragment == null) {
                datePickerDialogFragment = DatePickerDialogFragment()
            }

            return datePickerDialogFragment as DatePickerDialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = when (selectedDate) {
            null -> Calendar.getInstance()
            else -> selectedDate as Calendar
        }

        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val minDate = when (minimumDate) {
            null -> Calendar.getInstance().apply { add(Calendar.MONTH, -1) }
            else -> minimumDate as Calendar
        }

        val maxDate = when (maximumDate) {
            null -> Calendar.getInstance()
            else -> maximumDate as Calendar
        }

        val dialog = DatePickerDialog(requireContext(), this, year, month, day)
        dialog.datePicker.minDate = minDate.time.time
        dialog.datePicker.maxDate = maxDate.time.time

        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        callback?.onDateSet(year, month, dayOfMonth, tag)
    }

    interface Callback {
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int, tag: String?)
    }

    fun showDialog(
        fragmentManager: FragmentManager,
        tag: String,
        selectedDate: Calendar?,
        minimumDate: Calendar?,
        maximumDate: Calendar?
    ) {
        this.selectedDate = selectedDate
        this.minimumDate = minimumDate
        this.maximumDate = maximumDate
        show(fragmentManager, tag)
    }

}