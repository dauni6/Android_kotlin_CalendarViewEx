package com.dontsu.android_kotlin_calendarviewex

import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private lateinit var mTimePickerDialog: TimePickerDialog
    private var startOrEndDayFlag = 0
    private var startOrEndTimeFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        
        //시간설정
        val listener = TimePickerDialog.OnTimeSetListener { view, _hourOfDay, _minute ->
            var hour = ""
            var minute = ""
            hour = if (_hourOfDay < 10) {
                "0$_hourOfDay"
            } else {
                "$_hourOfDay"
            }
            minute = if (_minute < 10) {
                "0$_minute"
            } else {
                "$_minute"
            }
            if (startOrEndTimeFlag == 0) {
                startTime.text = "$hour : $minute"
            } else {
                endTime.text =  "$hour : $minute"
            }
        }

        @Suppress("DEPRECATION")
        mTimePickerDialog = TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, 12, 30, true)
        mTimePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        setMinDate() //최소날짜

        setMaxDate() //최대날짜
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            calendarView.setOnDateChangeListener { view, year, _month, _dayOfMonth ->
                var month = ""
                var day = ""
                month = if (_month < 10) {
                     "0$_month"
                } else {
                     "$_month"
                }
                day = if (_dayOfMonth < 10) {
                    "0$_dayOfMonth"
                } else {
                    "$_dayOfMonth"
                }

                if (startOrEndDayFlag == 0) {
                    startDay.text = "$year.$month.$day"
                } else {
                    endDay.text = "$year.$month.$day"
                }
                Toast.makeText(this, "선택날짜 : $year 년 ${_month + 1} 월 $_dayOfMonth 일", Toast.LENGTH_SHORT).show()
            }
        }

        startDay.setOnClickListener {
            startOrEndDayFlag = 0
        }

        endDay.setOnClickListener {
            startOrEndDayFlag = 1
        }

        startTime.setOnClickListener {
            startOrEndTimeFlag = 0
            mTimePickerDialog.setTitle("시작시간")
            mTimePickerDialog.show()
        }

        endTime.setOnClickListener {
            startOrEndTimeFlag = 1
            mTimePickerDialog.setTitle("종료시간")
            mTimePickerDialog.show()
        }

    }

    /*private fun getToday() {
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
    }*/

    private fun setMinDate() {
        calendarView.minDate = Date().time //오늘날짜포함 이후로 선택가능, 최소날짜 Milliseconds 단위로만 설정가능함
    }

    private fun setMaxDate() {
        //현재 년, 월, 일
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        val maxDay = manipulateDate(year, month, day) //최대 60일 이후까지만 선택 가능하도록 날짜조정
        val result = toMilliseconds(maxDay)
        calendarView.maxDate = result.time //최대 날짜 설정, 최대날짜 까지 선택가능
    }

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
