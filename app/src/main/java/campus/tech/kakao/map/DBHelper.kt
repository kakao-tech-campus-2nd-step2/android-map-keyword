package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context): SQLiteOpenHelper(context, "place.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${PlaceContract.TABLE_NAME} ("+
            "${PlaceContract.TABLE_COLUMN_NAME} VARCHAR(30) NOT NULL,"+
            "${PlaceContract.TABLE_COLUMN_ADDRESS} VARCHAR(50) NOT NULL,"+
            "${PlaceContract.TABLE_COLUMN_CATEGORY} VARCHAR(30) NOT NULL"+
            ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${PlaceContract.TABLE_NAME}")
        onCreate(db)
    }
}