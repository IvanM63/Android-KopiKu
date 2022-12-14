package com.example.loginandregister

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "account_list.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_ACCOUNT = "tbl_account"
        private const val EMAIL = "email"
        private const val USERNAME = "usename"
        private const val PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblAccount = ("CREATE TABLE $TBL_ACCOUNT " +
                "( $EMAIL TEXT PRIMARY KEY, $USERNAME TEXT, $PASSWORD TEXT) ")
        db?.execSQL(createTblAccount)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_ACCOUNT")
        onCreate(db)
    }
}