package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//데이터베이스 생성 및 업그레이드를 관리
class DatabaseHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {  //
        private const val DATABASE_NAME = "places.db"
        private const val TABLE_NAME = "places"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_ADDRESS = "address"
        private const val COLUMN_KIND = "kind"

        private const val TABLE_CREATE =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME TEXT, $COLUMN_ADDRESS TEXT, $COLUMN_KIND TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun dropTable(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

}


