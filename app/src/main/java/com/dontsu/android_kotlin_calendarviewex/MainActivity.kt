package com.dontsu.android_kotlin_calendarviewex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setMinDate()

        getToday()

        setMaxDate()
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

    private fun setMaxDate() {
        //현재 년, 월, 일
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        Log.d("결괏값", " $year 년 ${month + 1} 월 $day 일")

        manipulateDate(year, month, day)

    }

    private fun manipulateDate(year: Int, month: Int, day: Int) {
        //최대 60일간 빌릴 수 있음 maxDay는 +60일 한 날짜로 만들어준다.
        var count = 60
        var flag = true
        val mdays = ArrayList<ArrayList<Int>>()
        val normalYear = arrayListOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31) //평년
        val leapYear = arrayListOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31) //윤년
        mdays.add(normalYear)
        mdays.add(leapYear)

        while (flag) {
            
        }
    }

    private fun isLeap(year: Int): Int { //윤년 : 1 , 평년 : 0
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return 1
        }
        return 0
    }
}
