package com.example.drink.fragment

import android.support.v4.app.DialogFragment
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.annotation.NonNull
import java.util.*


class DatePickerFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity!!, activity as DatePickerDialog.OnDateSetListener?, year, month, day)
    }
}