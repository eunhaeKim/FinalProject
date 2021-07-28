package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class More100: AppCompatActivity() {

    lateinit var btnMore100 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.more100)

        btnMore100 = findViewById(R.id.buttonMore100)

        btnMore100.setOnClickListener {
            var intent = Intent(this, RemainActivity3::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return true
    }
}