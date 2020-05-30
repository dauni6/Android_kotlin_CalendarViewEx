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
        //Toast.makeText(this, "$date2", Toast.LENGTH_SHORT).show()
    }

    private fun setMinDate() {
        calendarView.minDate = Date().time //오늘날짜포함 이후로 선택가능, 최소날짜
    }

    private fun setMaxDate() {
        //현재 년, 월, 일
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        manipulateDate(year, month, day)

    }

 /**
  *  +60일한 날짜를 구하기
  * **/
    private fun manipulateDate(year: Int, month: Int, day: Int) {

        var presentDay = day
        var presentMonth = month + 1
        var presentYear = year
        var count = 60
        val mDays = ArrayList<ArrayList<Int>>()
        val normalYear = arrayListOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31) //평년
        val leapYear = arrayListOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31) //윤년
        mDays.add(normalYear)
        mDays.add(leapYear)
        var daysOfMonth = mDays[isLeap(presentYear)][month] //그 달의 일수
        //현재 날짜에서 계속 ++ 하는데 현재날짜 >= daysOfMonth 이면 다음 달로 넘어가서 계속 구하기

        while (true) {
            if (count == 1 ) break
            if (presentDay == daysOfMonth) { //현재날짜가 그 달의 일수와 같다면
                presentMonth += 1
                presentDay = 1 //1일로 초기화
                daysOfMonth = mDays[isLeap(presentYear)][presentMonth - 1]
            } else {
                presentDay += 1
            }
            count--
        }

     Toast.makeText(this, "$presentYear 년 $presentMonth 월 $presentDay 일", Toast.LENGTH_SHORT).show()

    }

    private fun isLeap(year: Int): Int { //윤년 : 1 , 평년 : 0
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return 1
        }
        return 0
    }
}
