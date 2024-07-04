package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class SQLiteDb(context: Context) {
    private val dbHelper: SQLiteHelper = SQLiteHelper.getInstance(context)
    private val database: SQLiteDatabase = dbHelper.writableDatabase

    fun insertData(name: String, address: String, category: String): Any {
        if (!isDataExists(name)) {
            val values = ContentValues().apply {
                put(SQLiteHelper.COL_NAME, name)
                put(SQLiteHelper.COL_ADDRESS, address)
                put(SQLiteHelper.COL_CATEGORY, category)
            }
            return database.insert(SQLiteHelper.TABLE_NAME, null, values)
        }
        return false
    }

    fun isDataExists(name: String): Boolean {
        val cursor = database.query(
            SQLiteHelper.TABLE_NAME,
            arrayOf(SQLiteHelper.COL_NAME),
            "${SQLiteHelper.COL_NAME} = ?",
            arrayOf(name),
            null,
            null,
            null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    fun getAllData(): Cursor {
        return database.query(SQLiteHelper.TABLE_NAME, null, null, null, null, null, null)
    }

    fun logAllData() {
        val cursor = getAllData()
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_NAME))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_ADDRESS))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_CATEGORY))
                Log.d("DatabaseHelper", "Name: $name, Address: $address, Category : $category")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }



}
