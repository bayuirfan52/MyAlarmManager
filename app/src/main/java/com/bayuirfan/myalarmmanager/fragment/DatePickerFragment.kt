package com.bayuirfan.myalarmmanager.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{

    private var listener : DialogDateListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context != null) listener = context as DialogDateListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)
        return activity?.let {DatePickerDialog(it, this, year, month, date)} as DatePickerDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        tag?.let { listener?.onDialogDateListener(it, year, month, dayOfMonth) }
    }

    interface DialogDateListener{
        fun onDialogDateListener(tag: String, year: Int, month: Int, dayOfMonth: Int)
    }
}