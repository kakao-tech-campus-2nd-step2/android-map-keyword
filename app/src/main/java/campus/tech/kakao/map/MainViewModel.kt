package campus.tech.kakao.map

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val placeRepository: PlaceRepository) : ViewModel() {
    private var _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>>
        get() = _placeList

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

    fun getSearchResult(searchText: String) {
        if (searchText.isEmpty()) {
            _placeList.postValue(emptyList())
        } else {
            val results = placeRepository.getSearchResults(searchText)
            _placeList.postValue(results)
        }
    }
}