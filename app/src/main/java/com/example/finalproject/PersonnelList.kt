package com.example.finalproject

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView

class PersonnelList : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_list)

        dbManager= DBManager(this,"personnelDB",null,1)
        sqlitedb=dbManager.readableDatabase

        layout=findViewById(R.id.personnel)

        var cursor: Cursor
        cursor=sqlitedb.rawQuery("SELECT * FROM personnel",null)

        var num:Int=0
        while(cursor.moveToNext()){

            var str_date=cursor.getString((cursor.getColumnIndex("date"))).toString()
            var caffeine=cursor.getInt((cursor.getColumnIndex("caffeine")))

            var layout_item:LinearLayout= LinearLayout(this)
            layout_item.orientation= LinearLayout.VERTICAL
            layout_item.id=num

            var tvdate: TextView = TextView(this)
            tvdate.text=str_date
            tvdate.textSize=30f
            tvdate.setBackgroundColor(Color.LTGRAY)
            layout_item.addView(tvdate)

            var tvcaffeine: TextView = TextView(this)
            tvcaffeine.text=caffeine.toString()
            layout_item.addView(tvcaffeine)


            layout_item.setOnClickListener{
                val intent= Intent(this, PersonnelInfo::class.java)
                intent.putExtra("intent_date",str_date)
                startActivity(intent)
            }

            layout.addView(layout_item)
            num++;
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.action_reg -> {
                val intent = Intent(this, PersonnelReg::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}