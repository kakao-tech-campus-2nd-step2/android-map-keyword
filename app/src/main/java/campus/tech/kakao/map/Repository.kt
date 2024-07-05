package campus.tech.kakao.map

import android.content.Context
import android.database.Cursor
import android.util.Log

class Repository(context: Context) {
    private val dbHelper = DatabaseHelper.getInstance(context)
    private val db = dbHelper.writableDatabase

    fun populateInitialData() {
        val dataCategories = listOf("cafe", "pharmacy", "cinema")

        db.beginTransaction()
        for (category in dataCategories) {
            for (i in 1..10) {
                val name = "$category$i"
                val address = "대전 유성구 봉명동 $i"
                if (!isDataExist(name)) {
                    val insertData = "INSERT INTO ${DatabaseHelper.TABLE_NAME} (${DatabaseHelper.COLUMN_NAME}, ${DatabaseHelper.COLUMN_ADDRESS}) " +
                            "VALUES ('$name', '$address')"
                    db.execSQL(insertData)
                }
            }
        }

        db.setTransactionSuccessful()
        db.endTransaction()
    }

    private fun isDataExist(name: String): Boolean {
        val cursor: Cursor = db.rawQuery("SELECT 1 FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_NAME} = ?", arrayOf(name))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun printAllData() {
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            do {
                Log.d("database", "ID: ${cursor.getInt(0)}, Name: ${cursor.getString(1)}, Address: ${cursor.getString(2)}")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    fun search(query: String): List<Keyword> {
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_NAME} LIKE ?", arrayOf("%$query%"))
        val keywords = mutableListOf<Keyword>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS))
                keywords.add(Keyword(id, name, address))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return keywords
    }


}
