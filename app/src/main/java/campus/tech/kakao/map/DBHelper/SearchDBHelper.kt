package campus.tech.kakao.map.DBHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import campus.tech.kakao.map.DataContract.SearchDataContract

class SearchDBHelper(context: Context) : SQLiteOpenHelper(context, "SearchData.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        createSearchDataTable(db)
    }

    //버전 업그레이드 시 실행
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${SearchDataContract.TABLE_NAME}")
        createSearchDataTable(db)
    }

    private fun createSearchDataTable(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${SearchDataContract.TABLE_NAME}(" +
                    "  ${SearchDataContract.TABLE_COLUMN_NAME} varchar(30) not null," +
                    "  ${SearchDataContract.TABLE_COLUMN_CATEGORY} varchar(20)," +
                    "  ${SearchDataContract.TABLE_COLUMN_ADDRESS} varchar(200)" +
                    ");"
        )
    }
}