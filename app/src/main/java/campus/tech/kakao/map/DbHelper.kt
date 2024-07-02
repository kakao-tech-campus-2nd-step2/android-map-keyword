package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "place.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${PlaceContract.TABLE_NAME} (" +
                    "${PlaceContract.COLUMN_NAME} VARCHAR(30) NOT NULL," +
                    "${PlaceContract.COLUMN_ADDRESS} VARCHAR(30) NOT NULL," +
                    "${PlaceContract.COLUMN_CATEGORY} VARCHAR(30) NOT NULL" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${PlaceContract.TABLE_NAME}")
        onCreate(db)
    }

    fun insertData(name: String, address: String, category: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PlaceContract.COLUMN_NAME, name)
            put(PlaceContract.COLUMN_ADDRESS, address)
            put(PlaceContract.COLUMN_CATEGORY, category)
        }
        db.insert(PlaceContract.TABLE_NAME, null, values)
    }
}