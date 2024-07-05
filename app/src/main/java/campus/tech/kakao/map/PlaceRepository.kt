package campus.tech.kakao.map

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class PlaceRepository(context: Context) {
    private val dbHelper: DBHelper = DBHelper(context)
    private val db = dbHelper.writableDatabase
    fun insertPlace(place: Place) {
        dbHelper.insert(db,place)
    }

    fun getPlace(): List<Place>?{
        return null
    }

    fun dbClose() {
        if(db.isOpen) db.close()
    }

    fun getSearchResults(searchText: String): List<Place> {
        val rDb = dbHelper.readableDatabase
        val places = mutableListOf<Place>()
        val query = "SELECT * FROM ${PlaceContract.TABLE_NAME} WHERE ${PlaceContract.TABLE_COLUMN_NAME} LIKE ?"
        val cursor = rDb.rawQuery(query, arrayOf("%$searchText%"))

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.TABLE_COLUMN_NAME))
                    val address = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.TABLE_COLUMN_ADDRESS))
                    val category = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.TABLE_COLUMN_CATEGORY))
                    val place = Place(name, address, category)
                    places.add(place)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return places
    }
}