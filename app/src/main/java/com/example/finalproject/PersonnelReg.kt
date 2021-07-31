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

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var btnRegister: Button
    lateinit var edtDate: EditText
    lateinit var edtDrink : EditText
    lateinit var edtCaffeine: EditText
    lateinit var ageType : RadioGroup
    lateinit var adult : RadioButton
    lateinit var pregnant : RadioButton
    lateinit var child : RadioButton
    var totalCa = 400 //성인 일일 적정 카페인 섭취량

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_reg)

        btnRegister = findViewById(R.id.btnRegister)
        edtDate = findViewById(R.id.edtDate)
        edtDrink = findViewById(R.id.edtDrink)
        edtCaffeine = findViewById(R.id.edtCaffeine)
        ageType = findViewById(R.id.type)
        adult = findViewById(R.id.adult)
        pregnant = findViewById(R.id.pregnant)
        child = findViewById(R.id.child)

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

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO personnel VALUES('" + str_date + "', '" + str_ageType + "'," + str_Drink + ", '" + str_caffeine + "')")
            sqlitedb.close()

            val intent = Intent(this, PersonnelInfo::class.java)
            intent.putExtra("intent_date", str_date)
            //startActivity(intent)

            var ca = edtCaffeine.text.toString().toInt()
            totalCa -= ca
            when {
                totalCa >= 300 -> startActivity(intent2)
                totalCa >= 200 -> startActivity(intent3)
                totalCa >= 100 -> startActivity(intent4)
                totalCa >= 0 -> startActivity(intent5)
                else -> startActivity(intent1)
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
