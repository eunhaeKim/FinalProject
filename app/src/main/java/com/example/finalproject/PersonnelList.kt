package com.example.finalproject

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView

class PersonnelList : AppCompatActivity() {
//DB 관련 변수 생성
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_list)

//DBManager 객체를 받아온다 (DB의 이름은 personnelDB로 지정)
        dbManager= DBManager(this,"personnelDB",null,1)

//읽기만 가능하도록 지정
        sqlitedb=dbManager.readableDatabase

//layout 변수를 위젯과 연결
        layout=findViewById(R.id.personnel)

//DB로 부터 값을 가져온다
        var cursor: Cursor
        cursor=sqlitedb.rawQuery("SELECT * FROM personnel",null)

        var num:Int=0
        while(cursor.moveToNext()){

            var str_date=cursor.getString((cursor.getColumnIndex("date"))).toString()
            var caffeine=cursor.getInt((cursor.getColumnIndex("caffeine")))

//TextView가 있는 레이아웃 생성
            var layout_item:LinearLayout= LinearLayout(this)
            layout_item.orientation= LinearLayout.VERTICAL
            layout_item.id=num

//날짜에 해당하는 TextView 구현
            var tvdate: TextView = TextView(this)
            tvdate.text=str_date
            tvdate.textSize=30f
            tvdate.setBackgroundColor(Color.LTGRAY)
            layout_item.addView(tvdate) //레이아웃에 추가

//카페인 함량에 해당하는 TextView 구현
            var tvcaffeine: TextView = TextView(this)
            tvcaffeine.text=caffeine.toString()
            layout_item.addView(tvcaffeine) //레이아웃에 추가

//레이아웃이 클릭되었을 때 PersonnelInfo로 보냄
            layout_item.setOnClickListener{
                val intent= Intent(this, PersonnelInfo::class.java)
                intent.putExtra("intent_date",str_date)
                startActivity(intent)
            }
            layout.addView(layout_item)

//while문이 반복할 때마다 하나씩 증가하도록
            num++
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
//메뉴에서 '홈'을 클릭하면 홈 화면으로 이동
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
//메뉴에서 '카페인 섭취량 등록'을 클릭하면 등록 화면으로 이동
            R.id.action_reg -> {
                val intent = Intent(this, PersonnelReg::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}