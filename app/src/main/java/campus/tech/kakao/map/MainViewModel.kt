package campus.tech.kakao.map

import androidx.lifecycle.ViewModel

class MainViewModel(private val placeRepository: PlaceRepository): ViewModel() {
    fun insertPlace(place: Place) {
        placeRepository.insertPlace(place)
    }

    fun getPlace(): List<Place>? {
        return placeRepository.getPlace()
    }

    override fun onCleared() {
        super.onCleared()
        placeRepository.dbClose()
    }
}