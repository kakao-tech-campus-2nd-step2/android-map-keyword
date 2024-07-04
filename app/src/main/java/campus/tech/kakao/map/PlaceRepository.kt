package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class PlaceRepository(context: Context) {
    private val dbHelper = PlaceDBHelper.getInstance(context)

    fun insertPlace(place: Place) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(PlaceDBContract.PlaceEntry.COLUMN_NAME, place.name)
            put(PlaceDBContract.PlaceEntry.COLUMN_ADDRESS, place.address)
            put(PlaceDBContract.PlaceEntry.COLUMN_TYPE, place.type)
        }
        db.insert(PlaceDBContract.PlaceEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun getPlaces() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            PlaceDBContract.PlaceEntry.TABLE_NAME,
            arrayOf(
                PlaceDBContract.PlaceEntry.COLUMN_NAME,
                PlaceDBContract.PlaceEntry.COLUMN_ADDRESS,
                PlaceDBContract.PlaceEntry.COLUMN_TYPE
            ),
            null, null, null, null, null
        )

        val places = mutableListOf<Place>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(PlaceDBContract.PlaceEntry.COLUMN_NAME))
                val address =
                    getString(getColumnIndexOrThrow(PlaceDBContract.PlaceEntry.COLUMN_ADDRESS))
                val type = getString(getColumnIndexOrThrow(PlaceDBContract.PlaceEntry.COLUMN_TYPE))
                places.add(Place(name, address, type))
            }
            close()
        }
        db.close()
    }
}