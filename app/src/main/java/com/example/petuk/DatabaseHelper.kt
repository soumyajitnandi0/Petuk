package com.example.petuk

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val TAG = "DatabaseHelper"
        private const val DATABASE_NAME = "PetukUserDB.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            val createTableQuery = """
                CREATE TABLE $TABLE_NAME (
                    $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_NAME TEXT NOT NULL,
                    $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                    $COLUMN_PASSWORD TEXT NOT NULL
                )
            """.trimIndent()

            db.execSQL(createTableQuery)
            Log.d(TAG, "Database table created successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error creating database table", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
            Log.d(TAG, "Database upgraded from $oldVersion to $newVersion")
        } catch (e: Exception) {
            Log.e(TAG, "Error upgrading database", e)
        }
    }

    private fun hashPassword(password: String): String {
        return try {
            val messageDigest = java.security.MessageDigest.getInstance("SHA-256")
            val hashBytes = messageDigest.digest(password.toByteArray())
            val hexString = StringBuilder()

            for (byte in hashBytes) {
                val hex = Integer.toHexString(0xff and byte.toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }

            hexString.toString()
        } catch (e: Exception) {
            Log.e(TAG, "Error hashing password", e)
            // Fallback if hashing fails
            password
        }
    }

    fun insertUser(name: String, email: String, password: String): Long {
        var db: SQLiteDatabase? = null
        try {
            val hashedPassword = hashPassword(password)
            val values = ContentValues().apply {
                put(COLUMN_NAME, name)
                put(COLUMN_EMAIL, email)
                put(COLUMN_PASSWORD, hashedPassword)
            }

            db = this.writableDatabase
            val result = db.insert(TABLE_NAME, null, values)
            Log.d(TAG, "User insertion result: $result")
            return result
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting user", e)
            return -1
        } finally {
            db?.close()
        }
    }

    fun isEmailExists(email: String): Boolean {
        var db: SQLiteDatabase? = null
        var cursor: android.database.Cursor? = null
        try {
            db = this.readableDatabase
            val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?"
            cursor = db.rawQuery(query, arrayOf(email))
            val exists = cursor.count > 0
            Log.d(TAG, "Email exists check: $email - $exists")
            return exists
        } catch (e: Exception) {
            Log.e(TAG, "Error checking if email exists", e)
            return false
        } finally {
            cursor?.close()
            db?.close()
        }
    }

    fun readUser(email: String, password: String): Boolean {
        var db: SQLiteDatabase? = null
        var cursor: android.database.Cursor? = null
        try {
            val hashedPassword = hashPassword(password)
            db = this.readableDatabase
            val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
            cursor = db.rawQuery(query, arrayOf(email, hashedPassword))
            val exists = cursor.count > 0
            Log.d(TAG, "User login attempt: $email - Success: $exists")
            return exists
        } catch (e: Exception) {
            Log.e(TAG, "Error checking user credentials", e)
            return false
        } finally {
            cursor?.close()
            db?.close()
        }
    }

    // For debugging: get all users
    fun getAllUsers(): List<String> {
        val userList = mutableListOf<String>()
        var db: SQLiteDatabase? = null
        var cursor: android.database.Cursor? = null
        try {
            db = this.readableDatabase
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                    val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                    userList.add("ID: $id, Name: $name, Email: $email")
                } while (cursor.moveToNext())
            }

            Log.d(TAG, "Total users in database: ${userList.size}")
            return userList
        } catch (e: Exception) {
            Log.e(TAG, "Error getting all users", e)
            return emptyList()
        } finally {
            cursor?.close()
            db?.close()
        }
    }
}