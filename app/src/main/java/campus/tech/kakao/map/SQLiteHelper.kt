package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "SearchData.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Data"
        const val COL_NAME = "name"
        const val COL_ADDRESS = "address"
        const val COL_CATEGORY = "category"

        private var instance: SQLiteHelper? = null

        @Synchronized
        fun getInstance(context: Context): SQLiteHelper {
            if (instance == null) {
                instance = SQLiteHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_NAME TEXT PRIMARY KEY, " +
                "$COL_ADDRESS TEXT, " +
                "$COL_CATEGORY TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}
