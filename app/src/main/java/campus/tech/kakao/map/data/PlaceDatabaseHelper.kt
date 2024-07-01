package campus.tech.kakao.map.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import campus.tech.kakao.map.model.Place

class PlaceDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    @Override
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, category TEXT)")
    }

    @Override
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    /**
     * db에 place를 insert하는 함수.
     */
    fun insertPlace(place: Place) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("name", place.name)
            put("address", place.address)
            put("category", place.category)
        }
        db.insert(TABLE_NAME, null, contentValues)
    }

    /**
     * 테이블을 삭제하는 함수.
     */
    fun clearAllPlaces() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "place.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "place_data"
    }
}
