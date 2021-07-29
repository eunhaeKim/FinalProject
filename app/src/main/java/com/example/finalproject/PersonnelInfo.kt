package com.example.finalproject

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class PersonnelInfo : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tvDate: TextView
    lateinit var tvType:TextView
    lateinit var tvDrink:TextView
    lateinit var tvCaffeine: TextView

    lateinit var str_date:String
    lateinit var str_ageType:String
    var drink:Int=0
    var caffeine:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_info)

        tvDate=findViewById(R.id.edtDate)
        tvType=findViewById(R.id.type)
        tvDrink=findViewById(R.id.edtDrink)
        tvCaffeine=findViewById(R.id.edtCaffeine)

        val intent=intent
        str_date=intent.getStringExtra("intent_date").toString()

        dbManager= DBManager(this,"personnelDB",null,1)
        sqlitedb=dbManager.readableDatabase

        var cursor: Cursor
        cursor=sqlitedb.rawQuery("SELECT * FROM personnel WHERE date = '" +str_date+"';",null)

        if(cursor.moveToNext()){
            str_ageType=cursor.getString((cursor.getColumnIndex("type"))).toString()
            drink=cursor.getInt((cursor.getColumnIndex("drink")))
            caffeine=cursor.getInt((cursor.getColumnIndex("caffeine")))
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        tvDate.text=str_date
        tvType.text=str_ageType
        tvDrink.text=""+drink
        tvCaffeine.text=""+caffeine+"\n"

    }
    }
