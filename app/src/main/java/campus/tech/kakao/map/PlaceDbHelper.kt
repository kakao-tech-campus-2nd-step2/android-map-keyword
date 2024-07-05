package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlaceDbHelper(context: Context):SQLiteOpenHelper(
	context, "place.db", null, 1) {
	override fun onCreate(db: SQLiteDatabase?) {
		db?.execSQL("CREATE TABLE ${PlaceContract.TABLE_NAME} " +
				"(${PlaceContract.COLUMN_NAME_NAME} TEXT, " +
				"${PlaceContract.COLUMN_NAME_ADDRESS} TEXT, " +
				"${PlaceContract.COLUMN_NAME_TYPE} TEXT)")
	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		db?.execSQL("DROP TABLE IF EXISTS ${PlaceContract.TABLE_NAME}")
		onCreate(db)
	}

	fun addPlace(place: Place) {
		val db = writableDatabase
		if (!existPlace(place, db)){
			val values = ContentValues()
			values.put(PlaceContract.COLUMN_NAME_NAME, place.name)
			values.put(PlaceContract.COLUMN_NAME_ADDRESS, place.address)
			values.put(PlaceContract.COLUMN_NAME_TYPE, place.type)
			db.insert(PlaceContract.TABLE_NAME, null, values)
			db.close()
		}
	}

	fun existPlace(place: Place, db: SQLiteDatabase): Boolean{
		val selection = "${PlaceContract.COLUMN_NAME_NAME} like ? AND " +
				"${PlaceContract.COLUMN_NAME_ADDRESS} like ? AND " +
				"${PlaceContract.COLUMN_NAME_TYPE} like ?"
		val cursor = db.query(
			PlaceContract.TABLE_NAME,
			arrayOf(PlaceContract.COLUMN_NAME_NAME),
			selection,
			arrayOf(place.name, place.address, place.type),
			null,
			null,
			"${PlaceContract.COLUMN_NAME_NAME} DESC"
		)

		val result = cursor.moveToFirst()
		cursor.close()
		return if (result) true else false
	}
}