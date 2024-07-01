package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataDbHelper(context: Context) : SQLiteOpenHelper(context, "data.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql: String = "CREATE TABLE if not exists locations(" +
                "name String," +
                "location String," +
                "category String);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE if exists locations"
        db?.execSQL(sql)
        onCreate(db)
    }
}