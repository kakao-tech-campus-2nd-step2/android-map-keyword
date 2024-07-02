package campus.tech.kakao.map.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocationDbHelper(context: Context) :
	SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
	override fun onCreate(db: SQLiteDatabase?) {
		val query = "CREATE TABLE ${LocationContract.TABLE_NAME} (" +
				"${LocationContract.COLUMN_NAME} VARCHAR(50)," +
				"${LocationContract.COLUMN_ADDRESS} VARCHAR(50)," +
				"${LocationContract.COLUMN_CATEGORY} VARCHAR(20))"
		db?.execSQL(query)
	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		db?.execSQL("DROP TABLE IF EXISTS ${LocationContract.TABLE_NAME}")
		onCreate(db)
	}

	companion object {
		const val DATABASE_NAME = "location.db"
		const val DATABASE_VERSION = 1
	}
}