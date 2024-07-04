package campus.tech.kakao.map.model

import android.content.Context

class SearchLocationRepository(context: Context) {
	private val locationDbHelper: LocationDbHelper = LocationDbHelper(context)
	private val historyDbHelper: HistoryDbHelper = HistoryDbHelper(context)

	fun searchLocation(category: String): List<Location> {
		val db = locationDbHelper.readableDatabase
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

	private fun isExistHistory(locationName: String): Boolean {
		val db = historyDbHelper.readableDatabase
		val searchQuery = "SELECT * FROM ${HistoryContract.TABLE_NAME} " +
				"WHERE ${HistoryContract.COLUMN_NAME} = '$locationName'"
		val cursor = db.rawQuery(searchQuery, null)

		val result = cursor.count > 0
		cursor.close()
		db.close()

		return result
	}
}