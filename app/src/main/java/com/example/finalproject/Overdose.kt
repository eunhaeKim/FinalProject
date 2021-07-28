package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Overdose: AppCompatActivity() {

    lateinit var btnoverdose : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overdose)

        btnoverdose = findViewById(R.id.buttonOverdose)

        btnoverdose.setOnClickListener {
            var intent = Intent(this, RemainActivity3::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return true
    }
}