package campus.tech.kakao.map.Repository

import android.database.Cursor
import campus.tech.kakao.map.Model.Datasource.Local.Dao.FavoriteDao
import campus.tech.kakao.map.Model.Datasource.Local.Dao.PlaceDao
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.Util.CategoryUtil
import campus.tech.kakao.map.Util.PlaceContract

class PlaceRepository(private val placeDao: PlaceDao,private val favoriteDao: FavoriteDao) {
    fun getCurrentFavorite(): MutableList<Place> {
        return favoriteDao.getCurrentFavorite()
    }

    fun getSimilarPlacesByName(name: String): List<Place>? {
        return placeDao.getSimilarPlacesByName(name)
    }

    fun addFavorite(name: String): Place {
        return favoriteDao.addFavorite(name)
    }

    fun deleteFavorite(name: String) {
        favoriteDao.deleteFavorite(name)
    }


}