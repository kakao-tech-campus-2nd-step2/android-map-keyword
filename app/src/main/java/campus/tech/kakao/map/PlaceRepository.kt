package campus.tech.kakao.map

class PlaceRepository(private val dbHelper: DBHelper) {
    fun insertPlace(place: Place) {
        val db = dbHelper.writableDatabase
        dbHelper.insert(db,place)
    }

    fun getPlace(): Place?{
        return null
    }
}