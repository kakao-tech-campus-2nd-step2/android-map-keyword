package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SearchDBHelper(context: Context) : SQLiteOpenHelper(context,"SearchData.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${SearchDataContract.TABLE_NAME}(" +
                "  ${SearchDataContract.TABLE_COLUMN_NAME} varchar(30) not null," +
                    "  ${SearchDataContract.TABLE_COLUMN_CATEGORY} varchar(20)," +
                    "  ${SearchDataContract.TABLE_COLUMN_ADDRESS} varchar(200)" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${SearchDataContract.TABLE_NAME}")
        onCreate(db)
    }
}