package campus.tech.kakao.map

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaceViewModel(private val context: Context) : ViewModel() {
    private val repository: PlaceRepository = PlaceRepository(context)
    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> get() = _places

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        val loadedPlaces = repository.getPlaces()
        _places.postValue(loadedPlaces)
    }

    fun insertPlace(place: Place) {
        repository.insertPlace(place)
        loadPlaces()
    }

    fun getPlaces() {
        loadPlaces()
    }


}


