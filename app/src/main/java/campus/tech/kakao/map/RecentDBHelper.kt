package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecentDBHelper(context: Context) : SQLiteOpenHelper(context,"RecentData.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${RecentDataContract.TABLE_NAME}(" +
                    "  ${RecentDataContract.TABLE_COLUMN_NAME} varchar(30) not null);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${RecentDataContract.TABLE_NAME}")
        onCreate(db)
    }
}