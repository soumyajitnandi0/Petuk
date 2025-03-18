package com.example.petuk

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object{

            private const val DATABASE_NAME = "UserDatabase.db"
            private const val DATABASE_VERSION = 1
            private const val TABLE_NAME = "users"
            private const val COLUMN_ID = "id"
            private const val COLUMN_NAME = "name"
            private const val COLUMN_EMAIL = "email"
            private const val COLUMN_PASSWORD = "password"

        }

        override fun onCreate(db: SQLiteDatabase?) {
            val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_EMAIL TEXT,$COLUMN_PASSWORD TEXT)"
            db?.execSQL(createTableQuery)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
            db?.execSQL(dropTableQuery)
            onCreate(db)
        }
        fun insertUser(name: String,email: String, password: String): Long {
            val values = ContentValues().apply {
                put(COLUMN_NAME, name)
                put(COLUMN_EMAIL, email)
                put(COLUMN_PASSWORD, password)
            }
            val db = writableDatabase
            return db.insert(TABLE_NAME, null, values)
        }
        fun readUser(email: String,password: String): Boolean {
            val db = readableDatabase
            val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
            val selectionArguments = arrayOf(email, password)
            val cursor = db.query(TABLE_NAME, null, selection, selectionArguments, null, null, null)
            val userExists = cursor.count > 0
            cursor.close()
            return userExists
        }
    }
