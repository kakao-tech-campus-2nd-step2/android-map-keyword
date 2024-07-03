package campus.tech.kakao.map.Repository

import android.database.Cursor
import android.util.Log
import campus.tech.kakao.map.Model.Datasource.Local.SqliteDB
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.Util.CategoryUtil
import campus.tech.kakao.map.Util.PlaceContract

class PlaceRepository(private val database: SqliteDB) {

    fun getAllPlace(): List<Place> {
        val cursor = database.getAllPlace()
        return getPlaceListByCursor(cursor)
    }

    fun deletePlace(name: String) {
        database.deletePlace(name)
    }

    fun deleteFavorite(name: String) {
        database.deleteFavorite(name)
    }

    fun getSimilarPlacesByName(name: String): List<Place> {
        val cursor = database.getSimilarCursorByName(name)
        return getPlaceListByCursor(cursor)
    }

    private fun getPlaceByName(name: String): Place {
        val cursor = database.getCursorByName(name)
        return getPlaceByCursor(cursor)
    }

    fun addFavorite(name: String): Place {
        Log.d("repo", "a")
        database.insertFavorite(name)
        return getPlaceByName(name)
    }

    private fun getPlaceListByCursor(cursor: Cursor): List<Place> {
        var result = mutableListOf<Place>()

        while (cursor.moveToNext()) {
            val place = Place(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME)
                ),
                cursor.getString(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_ADDRESS)
                ),
                CategoryUtil.intToCategory(
                    cursor.getInt(
                        cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_CATEGORY)
                    )
                )
            )
            result.add(place)
        }
        return result
    }

    private fun getPlaceByCursor(cursor: Cursor): Place {
        cursor.moveToFirst()
        return Place(
            cursor.getString(
                cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME)
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_ADDRESS)
            ),
            CategoryUtil.intToCategory(
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_CATEGORY)
                )
            )
        )

    }

    fun getCurrentFavorite(): MutableList<Place> {
        val cursor = database.selectAllFavorite()
        return getPlaceListByCursor(cursor).toMutableList()
    }

}