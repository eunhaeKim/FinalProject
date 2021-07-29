package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class Under100 : AppCompatActivity() {

    lateinit var btnUnder100 : Button //100mg 미만 페이지 버튼

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.under100)

        btnUnder100 = findViewById(R.id.buttonUnder100)

        //버튼을 눌렀을 때 100mg 미만으로 카페인을 섭취할 수 있는 음료
        btnUnder100.setOnClickListener {
            var intent = Intent(this, RemainActivity2::class.java)
            startActivity(intent)
        }
    }

    //메뉴(홈, 등록, 일별)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_reg ->{
                val intent = Intent(this, PersonnelReg::class.java)
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