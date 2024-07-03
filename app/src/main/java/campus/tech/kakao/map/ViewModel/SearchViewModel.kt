package campus.tech.kakao.map.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.Repository.PlaceRepository

class SearchViewModel(private val repository: PlaceRepository) : ViewModel() {
    val currentResult : MutableLiveData<List<Place>> = MutableLiveData()
    val favoritePlace : MutableLiveData<MutableList<Place>> = MutableLiveData()

    init{
        favoritePlace.value = repository.getCurrentFavorite()
    }

    fun searchPlace(string : String) {
        currentResult.value = if(string.isEmpty()) listOf<Place>()
            else repository.getSimilarPlacesByName(string)
    }

    fun addFavorite(name : String) {
        val place = repository.addFavorite(name)
        if (favoritePlace.value == null) {
            favoritePlace.value = mutableListOf<Place>(place)
        } else {
            if (isPlaceInFavorite(name)) return
            // favoritePlace.value에 바로 add할 시 Adapter에서 변화를 감지 못함
            val favorites = favoritePlace.value!!
            favorites.add(place)
            favoritePlace.value = favorites
        }
    }

    fun deleteFromFavorite(name: String){
        val place = favoritePlace.value?.find { it.name == name }
        favoritePlace.value?.remove(place)
        repository.deleteFavorite(name)
    }

    fun getCurrentFavorite(): MutableList<Place> {
        return repository.getCurrentFavorite()
    }

    private fun isPlaceInFavorite(name: String) : Boolean {
        return (favoritePlace.value?.find { it.name == name }) != null
    }

}