package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class PersonnelList : AppCompatActivity() {
//DB 관련 변수 생성
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout
    lateinit var btnDataReset : Button
    lateinit var btnCafTotalReset : Button
    lateinit var myHelper: myDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_list)

//DBManager 객체를 받아온다 (DB의 이름은 personnelDB로 지정)
        dbManager= DBManager(this,"personnelDB",null,1)

//읽기만 가능하도록 지정
        sqlitedb=dbManager.readableDatabase

//layout 변수를 위젯과 연결
        layout=findViewById(R.id.personnel)

        btnDataReset = findViewById(R.id.btn_dataReset)
        btnCafTotalReset = findViewById(R.id.btn_cafTotalReset)
        myHelper = myDBHelper(this)

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
            /*
            layout_item.setOnClickListener{
                val intent= Intent(this, PersonnelInfo::class.java)
                intent.putExtra("intent_date",str_date)
                startActivity(intent)
            }*/
            layout.addView(layout_item)

//while문이 반복할 때마다 하나씩 증가하도록
            num++
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // '데이터 초기화'버튼 클릭 시 팝업 창 띄우기
        btnDataReset.setOnClickListener {
            showSettingPopup1()
        }
        // '카페인 총량 초기화'버튼 클릭 시 팝업 창 띄우기
        btnCafTotalReset.setOnClickListener {
            showSettingPopup2()
        }
    }

    private fun showSettingPopup1(){
        // 팝업 레이아웃 객체화
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.reset_popup, null)
        // 텍스트뷰 속성 변경
        val textView: TextView = view.findViewById(R.id.textView)
        textView.text = "정말 데이터를 초기화 하시겠습니까?"
        textView.textSize = 15f

        //팝업 생성
        val alertDialog = AlertDialog.Builder(this)
                //.setTitle("초기화")
                .create()

        // '예' 버튼 동작(DB 초기화)
        val btnYes = view.findViewById<Button>(R.id.btnYes)
        btnYes.setOnClickListener {
            sqlitedb = myHelper.writableDatabase
            myHelper.onUpgrade(sqlitedb, 1, 2)
            sqlitedb.close()

            alertDialog.dismiss()

            //액티비티 갱신
            val intent = Intent(this,PersonnelList::class.java)
            finish() //현재 액티비티 종료
            overridePendingTransition(0,0) // 인텐트 애니메이션 없애기
            startActivity(intent) // 현재 액티비티 재실행
            overridePendingTransition(0,0) // 인텐트 애니메이션 없애기

            var toast = Toast.makeText(applicationContext, "초기화 됨", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, Gravity.CENTER,200) //토스트 메시지 위치 변경
            toast.show()
        }

        // '아니오' 버튼 동작
        val btnNo = view.findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.setView(view)
        alertDialog.show()
    }

    //추가한 부분
    private fun showSettingPopup2(){
        // 팝업 레이아웃 객체화
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.reset_popup, null)
        // 텍스트뷰 속성 변경
        val textView: TextView = view.findViewById(R.id.textView)
        textView.text = "남은 카페인 총량을 초기화 하시겠습니까?"
        textView.textSize = 15f

        //팝업 생성
        val alertDialog = AlertDialog.Builder(this)
                //.setTitle("초기화")
                .create()

        // '예' 버튼 동작(DB 초기화)
        val btnYes = view.findViewById<Button>(R.id.btnYes)
        btnYes.setOnClickListener {
            var reg = PersonnelReg() //객체 생성
            reg.Initializing() // 카페인 총량 변수 초기화
            alertDialog.dismiss()

            Toast.makeText(applicationContext, "초기화 됨", Toast.LENGTH_SHORT).show()
        }

        // '아니오' 버튼 동작
        val btnNo = view.findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.setView(view)
        alertDialog.show()
    }

    //추가한 부분
    // DB 생성, 삭제하는 함수
    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "personnelDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE personnel (date text, type text, drink INTEGER,caffeine INTEGER)")
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS personnel")
            onCreate(db)
        }
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