package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Overdose: AppCompatActivity() {

    lateinit var btnoverdose : Button //과섭취 페이지 버튼

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overdose)

        btnoverdose = findViewById(R.id.buttonOverdose)

        //버튼을 눌렀을 때 그만 마시라는 페이지
        btnoverdose.setOnClickListener {
            var intent = Intent(this, RemainActivity1::class.java)
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