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
        if(!isDataExists(name, address, category)) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(PlaceContract.COLUMN_NAME, name)
                put(PlaceContract.COLUMN_ADDRESS, address)
                put(PlaceContract.COLUMN_CATEGORY, category)
            }
            db.insert(PlaceContract.TABLE_NAME, null, values)
        }
    }

    //DB가 비어있는지 확인
    fun isDBEmpty(dbHelper: DbHelper): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM ${PlaceContract.TABLE_NAME}", null)
        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return  count == 0
    }

    //데이터 추가 시 중복 방지
    fun isDataExists(name: String, address: String, category: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT 1 FROM ${PlaceContract.TABLE_NAME} WHERE ${PlaceContract.COLUMN_NAME} = ? AND ${PlaceContract.COLUMN_ADDRESS} = ? AND ${PlaceContract.COLUMN_CATEGORY} = ?",
            arrayOf(name, address, category)
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }
}