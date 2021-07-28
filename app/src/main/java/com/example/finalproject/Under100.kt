package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button

class Under100 : AppCompatActivity() {

    lateinit var btnUnder100 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.under100)

        btnUnder100 = findViewById(R.id.buttonUnder100)

        btnUnder100.setOnClickListener {
            var intent = Intent(this, RemainActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return true
    }
}