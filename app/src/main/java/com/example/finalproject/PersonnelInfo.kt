package com.example.finalproject

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

//날짜별 정보 출력하는 페이지
class PersonnelInfo : AppCompatActivity() {
//DB 관련 변수 생성
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

//정보 등록할 때 필요한 변수 생성
    lateinit var tvDate: TextView
    lateinit var tvType:TextView
    lateinit var tvDrink:TextView
    lateinit var tvCaffeine: TextView

    lateinit var str_date:String
    lateinit var str_ageType:String

//Int형 변수는 0으로 초기화
    var drink:Int=0
    var caffeine:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_info)

//위젯과 변수를 연결
        tvDate=findViewById(R.id.edtDate)
        tvType=findViewById(R.id.type)
        tvDrink=findViewById(R.id.edtDrink)
        tvCaffeine=findViewById(R.id.edtCaffeine)

//날짜로 전달받은 intent 값을 열어보기 위해 intent 변수 선언
        val intent=intent
        str_date=intent.getStringExtra("intent_date").toString()

//DBManager 객체를 받아온다 (DB의 이름은 personnelDB로 지정)
        dbManager= DBManager(this,"personnelDB",null,1)

//읽기만 가능하도록 지정
        sqlitedb=dbManager.readableDatabase

//date에 해당하는 값을 가져온다
        var cursor: Cursor
        cursor=sqlitedb.rawQuery("SELECT * FROM personnel WHERE date = '" +str_date+"';",null)

        if(cursor.moveToNext()){
            str_ageType=cursor.getString((cursor.getColumnIndex("type"))).toString()
            drink=cursor.getInt((cursor.getColumnIndex("drink")))
            caffeine=cursor.getInt((cursor.getColumnIndex("caffeine")))
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

//TextView에 값을 넣음
        tvDate.text=str_date
        tvType.text=str_ageType
        tvDrink.text=""+drink
        tvCaffeine.text=""+caffeine+"\n"

    }
}
