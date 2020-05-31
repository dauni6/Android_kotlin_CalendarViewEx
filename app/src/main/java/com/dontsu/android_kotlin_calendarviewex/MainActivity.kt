package com.dontsu.android_kotlin_calendarviewex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setMinDate() //최소날짜

        getToday()

        setMaxDate() //최대날짜
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

        val maxDay = manipulateDate(year, month, day)
        val result = toMilliseconds(maxDay)
        calendarView.maxDate = result.time //최대 날짜 선택

    }

 /**
  *  +60일한 날짜를 구하기
  * **/
    private fun manipulateDate(year: Int, month: Int, day: Int): String {

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

        while (true) {
            if (count == 1 ) break
            if (presentDay == daysOfMonth) { //현재날짜가 그 달의 일수와 같다면
                if (presentMonth == 12 && presentDay == 31) { //그 해 12월 31일 이라서 해가 넘어간다면
                    presentYear += 1 //1년 추가
                    presentMonth = 1 //1월달로 초기화
                    presentDay = 1 //1일로 초기화
                } else { //맥시멈 12월 30일일 까지라면
                    presentMonth += 1 //한달 추가
                    presentDay = 1 //1일로 초기화
                    daysOfMonth = mDays[isLeap(presentYear)][presentMonth - 1] //달 일수 바꿔주기
                }
            } else {
                presentDay += 1
            }
            count--
        }

     //Log.d("dayCheck", "$presentYear 년 $presentMonth 월 $presentDay 일")

     return "${presentYear}.${presentMonth}.${presentDay}"
    }

    private fun isLeap(year: Int): Int { //윤년 : 1 , 평년 : 0
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return 1
        }
        return 0
    }

    private fun toMilliseconds(str: String): Date = SimpleDateFormat("yyyy.MM.dd").parse(str) //Milliseconds 단위로 바꿈

}
