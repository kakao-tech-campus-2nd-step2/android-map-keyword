package campus.tech.kakao.map.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlaceDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
        insertInitialRecords(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP_TABLE)
        onCreate(db)
    }

    private fun insertInitialRecords(db: SQLiteDatabase?) {
        db?.let {
            insertCafeRecords(it)
            insertPharmacyRecords(it)
        }
    }

    private fun insertCafeRecords(db: SQLiteDatabase) {
        val category = "카페"
        val titleBase = "카페"
        val locationBase = "서울 성동구 성수동"

        for (i in 1..20) {
            val title = "$titleBase$i"
            val location = "$locationBase $i"
            db.execSQL(
                "INSERT INTO ${PlaceContract.TABLE_NAME} (${PlaceContract.COLUMN_CATEGORY}, ${PlaceContract.COLUMN_TITLE}, ${PlaceContract.COLUMN_LOCATION}) VALUES ('$category', '$title', '$location');"
            )
        }
    }

    private fun insertPharmacyRecords(db: SQLiteDatabase) {
        val category = "약국"
        val titleBase = "약국"
        val locationBase = "서울 강남구 대치동"

        for (i in 1..20) {
            val title = "$titleBase$i"
            val location = "$locationBase $i"
            db.execSQL(
                "INSERT INTO ${PlaceContract.TABLE_NAME} (${PlaceContract.COLUMN_CATEGORY}, ${PlaceContract.COLUMN_TITLE}, ${PlaceContract.COLUMN_LOCATION}) VALUES ('$category', '$title', '$location');"
            )
        }
    }

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "location.db"
        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${PlaceContract.TABLE_NAME} (" +
                    "${PlaceContract.COLUMN_CATEGORY} TEXT NOT NULL, " +
                    "${PlaceContract.COLUMN_TITLE} TEXT NOT NULL, " +
                    "${PlaceContract.COLUMN_LOCATION} TEXT NOT NULL);"
        private const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS ${PlaceContract.TABLE_NAME}"
    }
}
