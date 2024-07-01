package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataDbHelper(context: Context) : SQLiteOpenHelper(context, "data.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}