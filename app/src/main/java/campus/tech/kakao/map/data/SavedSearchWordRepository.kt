package campus.tech.kakao.map.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import campus.tech.kakao.map.model.SavedSearchWord

class SavedSearchWordRepository(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT)")
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun deleteAndInsertSearchWord(searchWord: SavedSearchWord) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(searchWord.name))
            val contentValues =
                ContentValues().apply {
                    put(COLUMN_NAME, searchWord.name)
                }
            db.insert(TABLE_NAME, null, contentValues)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("dbError", "Error while delete and insert: ${e.message}")
        } finally {
            db.endTransaction()
        }
    }

    fun getAllSearchWords(): List<SavedSearchWord> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val searchWords = mutableListOf<SavedSearchWord>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            searchWords.add(SavedSearchWord(name))
        }
        cursor.close()
        return searchWords
    }

    fun deleteSearchWord(searchWord: SavedSearchWord) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(searchWord.name))
    }

    companion object {
        const val DATABASE_NAME = "search_words.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "search_words_data"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }
}
