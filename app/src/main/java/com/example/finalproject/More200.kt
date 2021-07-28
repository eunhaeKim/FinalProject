package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class More200: AppCompatActivity() {

    lateinit var btnMore200 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.more200)

        btnMore200 = findViewById(R.id.buttonMore200)

        btnMore200.setOnClickListener {
            var intent = Intent(this, RemainActivity3::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return true
    }
}