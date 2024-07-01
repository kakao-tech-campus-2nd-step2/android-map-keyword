package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):
    SQLiteOpenHelper(context, "location.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${LocationContract.TABLE_NAME} (" +
                    "${LocationContract.COLUMN_NAME} TEXT NOT NULL, " +
                    "${LocationContract.COLUMN_LOCATION} TEXT NOT NULL, " +
                    "${LocationContract.COLUMN_TYPE} TEXT NOT NULL" +
                    ");"

        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${LocationContract.TABLE_NAME}")
        onCreate(db)
    }
}