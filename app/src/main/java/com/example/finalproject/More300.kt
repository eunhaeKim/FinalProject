package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class More300: AppCompatActivity() {

    lateinit var btnMore300 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.more300)

        btnMore300 = findViewById(R.id.buttonMore300)

        btnMore300.setOnClickListener {
            var intent = Intent(this, RemainActivity3::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return true
    }
}