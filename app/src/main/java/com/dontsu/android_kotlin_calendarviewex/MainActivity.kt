package com.dontsu.android_kotlin_calendarviewex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setMinDate()

        getToday()
    }

    private fun getToday() {
        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        simpleDateFormat.timeZone.id = timeZone.toString()

        //날짜얻기 방법1 : Calendar.getInstance().time
        val cal = Calendar.getInstance().time
        //날짜얻기 방법2 : calendarView.date
        val timestamp = calendarView.date

        val date = simpleDateFormat.format(cal)
        val date2 = simpleDateFormat.format(timestamp)
        //Toast.makeText(this, "$date", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "$date2", Toast.LENGTH_SHORT).show()
    }

    private fun setMinDate() {
        calendarView.minDate = Date().time //오늘날짜포함 이후로 선택가능, 최소날짜
    }
}
