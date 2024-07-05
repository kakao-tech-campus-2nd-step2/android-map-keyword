package campus.tech.kakao.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.model.Place
import campus.tech.kakao.map.model.SavedPlace
import campus.tech.kakao.map.repository.PlaceRepository
import campus.tech.kakao.map.repository.SavedPlaceRepository
import java.util.Locale.Category

class MainActivityViewModel(
    private val placeRepository: PlaceRepository,
    private val savedPlaceRepository: SavedPlaceRepository
) : ViewModel() {
    private val _place = MutableLiveData<MutableList<Place>>()
    private val _savedPlace = MutableLiveData<MutableList<SavedPlace>>()
    val place: LiveData<MutableList<Place>> get() = _place
    val savedPlace: LiveData<MutableList<SavedPlace>> get() = _savedPlace
    init{
        getSavedPlace()
    }
    fun getPlace() {
        _place.postValue(placeRepository.getAllPlace())
    }

    fun getPlaceWithCategory(category: String) {
        _place.postValue(placeRepository.getPlaceWithCategory(category))
    }

    fun getSavedPlace() {
        _savedPlace.postValue(savedPlaceRepository.getAllSavedPlace())
    }

    fun savePlace(place: Place) {
        savedPlaceRepository.writePlace(place)
        getSavedPlace()
    }


}