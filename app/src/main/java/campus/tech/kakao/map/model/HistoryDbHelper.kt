package campus.tech.kakao.map.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDbHelper(context: Context) :
	SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

	override fun onCreate(db: SQLiteDatabase?) {
		db?.let {
			val query = "CREATE TABLE ${HistoryContract.TABLE_NAME} (" +
					"${HistoryContract.COLUMN_NAME} VARCHAR(50))"
			it.execSQL(query)
		}
	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		db?.execSQL("DROP TABLE IF EXISTS ${HistoryContract.TABLE_NAME}")
		onCreate(db)
	}

	companion object {
		const val DATABASE_NAME = "history.db"
		const val DATABASE_VERSION = 1
	}
}