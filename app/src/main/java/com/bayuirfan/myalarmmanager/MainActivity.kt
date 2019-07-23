package com.bayuirfan.myalarmmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bayuirfan.myalarmmanager.fragment.DatePickerFragment
import com.bayuirfan.myalarmmanager.fragment.TimePickerFragment
import com.bayuirfan.myalarmmanager.receiver.AlarmReceiver
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.OnTimeSetListener {
    companion object{
        const val DATE_PICKER_TAG = "DatePicker"
        const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        const val TIME_PICKER_ONCE_REPEAT = "TimePickerRepeat"
    }

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onDialogDateListener(tag: String, year: Int, month: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance()
        cal.set(year, month, dayOfMonth)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        tv_once_date.text = sdf.format(cal.time)
    }

    override fun onDialogTimeSet(tag: String, hourOfDay: Int, minute: Int) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        when(tag){
            TIME_PICKER_ONCE_TAG -> {
                tv_once_time.text = sdf.format(cal.time)
            }
            TIME_PICKER_ONCE_REPEAT -> {
                tv_repeating_time.text = sdf.format(cal.time)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_once_date -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }

            R.id.btn_once_time -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }

            R.id.btn_set_once_alarm -> {
                val onceDate = tv_once_date.text.toString()
                val onceTime = tv_once_time.text.toString()
                val onceMessage = edt_once_message.text.toString().trim()

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    onceDate,
                    onceTime,
                    onceMessage)
            }

            R.id.btn_repeating_time -> {
                val timePickerFragmentRepeat = TimePickerFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_ONCE_REPEAT)
            }

            R.id.btn_set_repeating_alarm -> {
                val repeatingTime = tv_repeating_time.text.toString()
                val repeatMessage = edt_repeating_message.text.toString().trim()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    repeatingTime,
                    repeatMessage)
            }

            R.id.btn_cancel_repeating_alarm -> {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_once_date.setOnClickListener(this)
        btn_once_time.setOnClickListener(this)
        btn_set_once_alarm.setOnClickListener(this)
        btn_repeating_time.setOnClickListener(this)
        btn_set_repeating_alarm.setOnClickListener(this)
        btn_cancel_repeating_alarm.setOnClickListener(this)
        alarmReceiver = AlarmReceiver()
    }
}
