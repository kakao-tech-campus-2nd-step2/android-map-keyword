package campus.tech.kakao.map.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import campus.tech.kakao.map.db.PlaceContract
import campus.tech.kakao.map.db.PlaceDBHelper
import campus.tech.kakao.map.model.Place

class Repository (val dbHelper: PlaceDBHelper){
    fun getAllPlace() : MutableList<Place>{
        val cursor = dbHelper.readData()
        val placeList = mutableListOf<Place>()

        while (cursor.moveToNext()) {
            val place = Place(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_LOCATION)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_CATEGORY)
                )
            )
            Log.d("readData", "이름 = ${place.name}, 위치 = ${place.location}, 분류 = ${place.category}")
            placeList.add(place)
        }

        cursor.close()
        return placeList
    }

    fun writePlace(place: Place){
        dbHelper.insertData(place.name, place.location, place.category)
    }

    fun getPlaceWithCategory(category : String): MutableList<Place>{
        val cursor = dbHelper.readDataWithCategory(category)
        val placeList = mutableListOf<Place>()

        while (cursor.moveToNext()) {
            val place = Place(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_LOCATION)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_CATEGORY)
                )
            )
            Log.d("readData", "이름 = ${place.name}, 위치 = ${place.location}, 분류 = ${place.category}")
            placeList.add(place)
        }


        cursor.close()
        return placeList
    }
}