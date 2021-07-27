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
    lateinit var tvCaffeine: TextView

    lateinit var str_date:String
    var caffeine:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personnel_info)

        tvDate=findViewById(R.id.edtDate)
        tvCaffeine=findViewById(R.id.edtCaffeine)

        val intent=intent
        str_date=intent.getStringExtra("intent_date").toString()

        dbManager= DBManager(this,"personnelDB",null,1)
        sqlitedb=dbManager.readableDatabase

        var cursor: Cursor
        cursor=sqlitedb.rawQuery("SELECT * FROM personnel WHERE name = '" +str_date+"';",null)

        if(cursor.moveToNext()){
            caffeine=cursor.getInt((cursor.getColumnIndex("caffeine")))
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        tvDate.text=str_date
        tvCaffeine.text=""+caffeine+"\n"

    }

   /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_personnel_info, menu)
        return true
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }

            /*R.id.action_list -> {
                val intent = Intent(this, personnelList::class.java)
                startActivity(intent)
                return true
            }

            R.id.action_reg -> {
                val intent = Intent(this, personnelReg::class.java)
                startActivity(intent)
                return true
            }

            R.id.action_remove -> {
                dbManager= DBManager(this,"personnelDB",null,1)
                sqlitedb=dbManager.readableDatabase

                sqlitedb.execSQL("DELETE FROM personnel WHERE name='"+str_name+"';")
                sqlitedb.close()
                dbManager.close()

                val intent = Intent(this, personnelList::class.java)
                startActivity(intent)
                return true
            }*/
        }

        return super.onOptionsItemSelected(item)
    }
}