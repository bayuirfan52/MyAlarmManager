package com.bayuirfan.myalarmmanager.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var listener : OnTimeSetListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as OnTimeSetListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val formatHour24 = true
        return activity?.let { TimePickerDialog(it, this, hour, minute, formatHour24) } as TimePickerDialog
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        tag?.let { listener?.onDialogTimeSet(it, hourOfDay, minute) }
    }

    interface OnTimeSetListener{
        fun onDialogTimeSet(tag: String, hourOfDay: Int, minute: Int)
    }
}