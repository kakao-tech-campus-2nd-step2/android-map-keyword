package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class PlaceRepository(context: Context) {
    private val dbHelper = PlaceDBHelper.getInstance(context)

    init {
        insertDummies()
    }

    fun insertPlace(place: Place) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(PlaceDBContract.PlaceEntry.COLUMN_NAME, place.name)
            put(PlaceDBContract.PlaceEntry.COLUMN_ADDRESS, place.address)
            put(PlaceDBContract.PlaceEntry.COLUMN_TYPE, place.type)
        }
        db.insert(PlaceDBContract.PlaceEntry.TABLE_NAME, null, values)
       // db.close()
    }

    fun getPlaces(): MutableList<Place> {
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
        // db.close()
        return places
    }

    fun insertDummies() {
        clearPlaces()

        val db = dbHelper.writableDatabase
        val dummyPlaces = ((1..30).map {
            Place("카페 $it", "대전 서구 만년동 $it", "카페")
        })
        db.beginTransaction()
        for (place in dummyPlaces) {
            val value = ContentValues().apply {
                put(PlaceDBContract.PlaceEntry.COLUMN_NAME, place.name)
                put(PlaceDBContract.PlaceEntry.COLUMN_ADDRESS, place.address)
                put(PlaceDBContract.PlaceEntry.COLUMN_TYPE, place.type)
            }
            db.insert(PlaceDBContract.PlaceEntry.TABLE_NAME, null, value)
        }
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    private fun clearPlaces() {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            db.delete(PlaceDBContract.PlaceEntry.TABLE_NAME, null, null)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        db.close()
    }
}