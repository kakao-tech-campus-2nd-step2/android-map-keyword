package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context

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
}