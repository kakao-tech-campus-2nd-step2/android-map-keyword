package campus.tech.kakao.map.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import campus.tech.kakao.map.models.contracts.SearchResultContract
import campus.tech.kakao.map.models.contracts.SearchResultContract.COLUMN_ADDRESS_INDEX
import campus.tech.kakao.map.models.contracts.SearchResultContract.COLUMN_NAME_INDEX
import campus.tech.kakao.map.models.contracts.SearchResultContract.COLUMN_TYPE_INDEX
import campus.tech.kakao.map.models.contracts.SearchResultContract.TABLE_NAME

data class SearchResult(val name: String, val address: String, val type: String)

class SearchDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SearchResultContract.CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertData(name: String, address: String, type: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(SearchResultContract.COLUMN_NAME, name)
            put(SearchResultContract.COLUMN_ADDRESS, address)
            put(SearchResultContract.COLUMN_TYPE, type)
        }
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun insertData(searchResult: SearchResult) {
        insertData(searchResult.name, searchResult.address, searchResult.type)
    }

    fun updateData(id: String, name: String, address: String, type: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(BaseColumns._ID, id)
            put(SearchResultContract.COLUMN_NAME, name)
            put(SearchResultContract.COLUMN_ADDRESS, address)
            put(SearchResultContract.COLUMN_TYPE, type)
        }
        db.update(TABLE_NAME, contentValues, "${BaseColumns._ID} = ?", arrayOf(id))
    }

    fun updateData(id: String, searchResult: SearchResult) {
        updateData(id, searchResult.name, searchResult.address, searchResult.type)
    }

    fun deleteData(id: String) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(id))
    }

    private fun getAllResultFromCursor(cursor: Cursor?): List<SearchResult> {
        val result = mutableListOf<SearchResult>()

        try {
            while (cursor?.moveToNext() == true) {
                result.add(
                    SearchResult(
                        cursor.getString(COLUMN_NAME_INDEX),
                        cursor.getString(COLUMN_ADDRESS_INDEX),
                        cursor.getString(COLUMN_TYPE_INDEX)
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }

        return result
    }

    fun queryAll(): List<SearchResult> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        return getAllResultFromCursor(cursor)
    }

    fun queryName(name: String): List<SearchResult> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE ${SearchResultContract.COLUMN_NAME} LIKE \"%$name%\"",
            null
        )

        return getAllResultFromCursor(cursor)
    }

    companion object {
        private var instance: SearchDbHelper? = null
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MapSearch"

        fun getInstance(context: Context): SearchDbHelper {
            if (instance == null) {
                instance = SearchDbHelper(context)
            }
            return instance!!
        }
    }
}