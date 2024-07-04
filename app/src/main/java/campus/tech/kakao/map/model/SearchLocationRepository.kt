package campus.tech.kakao.map.model

import android.content.Context

class SearchLocationRepository(context: Context) {
	private val dbHelper: LocationDbHelper = LocationDbHelper(context)

	fun searchLocation(category: String): List<Location> {
		val db = dbHelper.readableDatabase
		val searchQuery = "SELECT * FROM ${LocationContract.TABLE_NAME} " +
				"WHERE ${LocationContract.COLUMN_CATEGORY} = '$category'"
		val cursor = db.rawQuery(searchQuery, null)

		val result = mutableListOf<Location>()
		while (cursor.moveToNext()) {
			result.add(
				Location(
					name = cursor.getString(cursor.getColumnIndexOrThrow(LocationContract.COLUMN_NAME)),
					address = cursor.getString(cursor.getColumnIndexOrThrow(LocationContract.COLUMN_ADDRESS)),
					category = cursor.getString(cursor.getColumnIndexOrThrow(LocationContract.COLUMN_CATEGORY))
				)
			)
		}
		cursor.close()
		db.close()

		return result.toList()
	}
}