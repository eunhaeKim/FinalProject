package com.example.finalproject

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

class PersonnelReg : AppCompatActivity() {
//DB 관련 변수 생성
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

//정보 등록할 때 필요한 변수 생성
    lateinit var btnRegister: Button
    lateinit var edtDate: EditText
    lateinit var edtDrink : EditText
    lateinit var edtCaffeine: EditText
    lateinit var ageType : RadioGroup
    lateinit var adult : RadioButton
    lateinit var pregnant : RadioButton
    lateinit var child : RadioButton
    var totalCaA = 400 //성인 일일 적정 카페인 섭취량
    var totalCaP = 300 //임산부 일일 적정 카페인 섭취량
    var totalCaC = 200 //어린이 일일 적정 카페인 섭취량

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_reg)
//위젯과 변수를 연결
        btnRegister = findViewById(R.id.btnRegister)
        edtDate = findViewById(R.id.edtDate)
        edtDrink = findViewById(R.id.edtDrink)
        edtCaffeine = findViewById(R.id.edtCaffeine)
        ageType = findViewById(R.id.type)
        adult = findViewById(R.id.adult)
        pregnant = findViewById(R.id.pregnant)
        child = findViewById(R.id.child)

//DBManager 객체를 받아온다 (DB의 이름은 personnelDB로 지정)
        dbManager = DBManager(this, "personnelDB", null, 1)

        val intent1 = Intent(this, Overdose::class.java)
        val intent2 = Intent(this, More300::class.java)
        val intent3 = Intent(this, More200::class.java)
        val intent4 = Intent(this, More100::class.java)
        val intent5 = Intent(this, Under100::class.java)
        btnRegister.setOnClickListener {

            var str_date: String = edtDate.text.toString()
            var str_Drink: String = edtDrink.text.toString()
            var str_caffeine: String = edtCaffeine.text.toString()

            var str_ageType: String = ""
            if (ageType.checkedRadioButtonId == R.id.adult) {
                str_ageType = adult.text.toString()
            }
            if (ageType.checkedRadioButtonId == R.id.pregnant) {
                str_ageType = pregnant.text.toString()
            }
            if (ageType.checkedRadioButtonId == R.id.child) {
                str_ageType = child.text.toString()
            }
//읽고 쓰기가 가능하도록 지정
            sqlitedb = dbManager.writableDatabase
//날짜, 음료량, 카페인 함량 정보를 DB에 삽입
            sqlitedb.execSQL("INSERT INTO personnel VALUES('" + str_date + "', '" + str_ageType + "'," + str_Drink + ", '" + str_caffeine + "')")
            sqlitedb.close()

            val intent = Intent(this, PersonnelInfo::class.java)
            intent.putExtra("intent_date", str_date)
            //startActivity(intent)

            var ca = edtCaffeine.text.toString().toInt()
//성인으로 체크했을 때 적정 카페인량은 400mg
            if (ageType.checkedRadioButtonId == R.id.adult) {
                totalCaA -= ca
                when {
                    totalCaA >= 300 -> startActivity(intent2)
                    totalCaA >= 200 -> startActivity(intent3)
                    totalCaA >= 100 -> startActivity(intent4)
                    totalCaA >= 0 -> startActivity(intent5)
                    else -> startActivity(intent1)
                }
            }
//임산부로 체크했을 때 적정 카페인량은 300mg
            if (ageType.checkedRadioButtonId == R.id.pregnant) {
                totalCaP -= ca
                when {
                    totalCaP >= 200 -> startActivity(intent3)
                    totalCaP >= 100 -> startActivity(intent4)
                    totalCaP >= 0 -> startActivity(intent5)
                    else -> startActivity(intent1)
                }
            }
//어린이로 체크했을 때 적정 카페인량은 200mg
            if (ageType.checkedRadioButtonId == R.id.child) {
                totalCaC -= ca
                when {
                    totalCaC >= 100 -> startActivity(intent4)
                    totalCaC >= 0 -> startActivity(intent5)
                    else -> startActivity(intent1)
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_reg, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.action_list -> {
                val intent = Intent(this, PersonnelList::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
