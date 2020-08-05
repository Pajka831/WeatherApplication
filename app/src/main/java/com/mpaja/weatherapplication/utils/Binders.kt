package com.mpaja.weatherapplication.utils


import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.mpaja.weatherapplication.R
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("formatedDate")
fun setDateFromUnix(textView: TextView, unixDate: Int) {
    val millis = (unixDate.toString() + "000")

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis.toLong()

    val dateFormat = SimpleDateFormat("dd MMMM: HH:mm, yyyy", Locale.UK)
    textView.text = dateFormat.format(calendar.time)
}

@BindingAdapter("temperatureTextColor")
fun setTemperatureColor(textView: TextView, temperature: String?) {
    if(temperature!= null){
        val temperatureValue = temperature.toInt()
        when {
            temperatureValue < 10 -> textView.setTextColor(
                ContextCompat.getColor(textView.context, R.color.colorPrimaryDark)
            )
            temperatureValue in 10..20 -> textView.setTextColor(
                ContextCompat.getColor(textView.context, R.color.black)
            )
            temperatureValue > 20 -> textView.setTextColor(
                ContextCompat.getColor(textView.context, R.color.red)
            )
        }
    }
}


