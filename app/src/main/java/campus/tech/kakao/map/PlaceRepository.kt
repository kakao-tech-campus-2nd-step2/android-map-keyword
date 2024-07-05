package campus.tech.kakao.map

class PlaceRepository(private val dbHelper: DBHelper) {
    val db = dbHelper.writableDatabase
    fun insertPlace(place: Place) {
        dbHelper.insert(db,place)
    }

    fun getPlace(): List<Place>?{
        return null
    }

    fun dbClose() {
        if(db.isOpen) db.close()
    }
}