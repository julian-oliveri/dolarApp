package com.example.dolarApp.dialog
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.widget.*
import com.example.jubblerdesafiotecnico.R
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.reflect.KFunction3

class CustomDialog(context: Context) : AlertDialog.Builder(context) {

    fun show(onEnd: KFunction3<Int, String, String, Unit>) {

        val dialog = Dialog(context)
        var mydate = LocalDate.parse("2020-12-31")
        var date = Date.from(mydate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        val options = context.resources.getStringArray(R.array.options_picker)

        var nowFor = LocalDate.now()
        var nowDay = if (nowFor.dayOfMonth < 10) "0" + nowFor.dayOfMonth.toString() else nowFor.dayOfMonth.toString()
        val nowMonth = if (nowFor.monthValue < 10) "0" + (nowFor.monthValue).toString() else (nowFor.monthValue).toString()

        var date1: String = nowDay + nowMonth.toString() + (nowFor.year).toString()
        var date2: String =  date1

        dialog.setContentView(R.layout.dialog_layout)
        dialog.setCancelable(false)

        val spinner: Spinner = dialog.findViewById(R.id.spinner)

        val adapter = ArrayAdapter(context, R.layout.spinner_item, options)
        spinner.adapter = adapter
        spinner.setSelection(0)

        val datePicker: DatePicker = dialog.findViewById(R.id.datePicker1)
        val datePicker2: DatePicker = dialog.findViewById(R.id.datePicker2)

        val today = Calendar.getInstance()
        val maxDate = Calendar.getInstance()

        maxDate.time = date

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val date1day = if (day < 10) "0$day" else day.toString()
            val date1Mont = if (month + 1 < 10) "0" + (month + 1).toString() else (month + 1).toString()
            date1 = date1day + date1Mont + year
        }
        datePicker.maxDate = today.timeInMillis
        datePicker.minDate = maxDate.timeInMillis

        datePicker2.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val date2day = if (day < 10) "0$day" else day.toString()
            val date2Mont = if (month + 1 < 10) "0" + (month + 1).toString() else (month + 1).toString()
            date2 = date2day + date2Mont + year
        }

        datePicker2.maxDate = today.timeInMillis
        datePicker2.minDate = maxDate.timeInMillis

        val btn : Button = dialog.findViewById(R.id.btn_search)

        btn.setOnClickListener{
            if (date1.isNotBlank() && date2.isNotBlank()) {
                onEnd(spinner.selectedItemPosition, date1, date2)
                dialog.dismiss()
            }
        }

        dialog.setTitle(R.string.dialog_title)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show()

    }

}