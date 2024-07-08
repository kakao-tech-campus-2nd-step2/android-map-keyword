package campus.tech.kakao.map.Model.Datasource.Local.Dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.Util.PlaceContract

class FavoriteDaoImpl(private val db : SQLiteDatabase) : FavoriteDao {

    override fun getCurrentFavorite(): MutableList<Place> {
        val cursor = db.rawQuery("SELECT * FROM ${PlaceContract.FavoriteEntry.TABLE_NAME}", null)
        return PlaceContract.getPlaceListByCursor(cursor).toMutableList()
    }

    override fun deleteFavorite(name: String) {
        db.delete(
            PlaceContract.FavoriteEntry.TABLE_NAME,
            "${PlaceContract.PlaceEntry.COLUMN_NAME}=?",
            arrayOf(name)
        )
    }

    override fun addFavorite(name: String): Place {
        val place = PlaceContract.getPlaceByCursor(
            getCursorByName(name)
        )

        val values = ContentValues().apply {
            this.put(PlaceContract.PlaceEntry.COLUMN_NAME, place.name)
            this.put(PlaceContract.PlaceEntry.COLUMN_ADDRESS, place.address)
            this.put(PlaceContract.PlaceEntry.COLUMN_CATEGORY, place.category?.ordinal)
        }

        db.insert(PlaceContract.FavoriteEntry.TABLE_NAME, null, values)
        return place
    }

    private fun getCursorByName(name: String): Cursor {
        return db.rawQuery(
            "SELECT * FROM ${PlaceContract.PlaceEntry.TABLE_NAME} WHERE name=?",
            arrayOf(name)
        )
    }
}