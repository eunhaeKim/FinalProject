package com.example.finalproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//SQLiteOpenHelper 클래스를 상속받은 DBManager 클래스 생성
class DBManager(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {

//테이블 생성(컬럼명과 데이터 타입 조건)
        db!!.execSQL("CREATE TABLE personnel (date text, type text, drink INTEGER, caffeine INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}