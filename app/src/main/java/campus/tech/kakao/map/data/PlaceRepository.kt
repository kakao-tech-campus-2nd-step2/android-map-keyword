package campus.tech.kakao.map.data

import android.content.Context
import campus.tech.kakao.map.model.Place

class PlaceRepository(context: Context) {
    private val dbHelper = PlaceDatabaseHelper(context)

    fun insertPlace(place: Place) {
        dbHelper.insertPlace(place)
    }

    fun clearAllPlaces() {
        dbHelper.clearAllPlaces()
    }
}
