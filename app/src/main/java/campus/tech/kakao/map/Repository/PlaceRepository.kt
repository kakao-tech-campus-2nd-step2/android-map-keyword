package campus.tech.kakao.map.Repository

import campus.tech.kakao.map.Model.Datasource.Local.DbHelper
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.Util.PlaceCategory
import campus.tech.kakao.map.Util.PlaceContract

class PlaceRepository(private val database: DbHelper) {

    fun getAllPlace() : List<Place>{
        val cursor = database.read()
        var result = mutableListOf<Place>()

        while (cursor.moveToNext()) {
            val place = Place(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_ADDRESS)
                ),
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_CATEGORY)
                )
            )
            result.add(place)
        }
        return result
    }

    fun deletePlace(name : String){
        database.delete(name)
    }

}