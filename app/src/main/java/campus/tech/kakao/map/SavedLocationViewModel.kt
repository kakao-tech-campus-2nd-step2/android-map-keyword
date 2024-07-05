package campus.tech.kakao.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedLocationViewModel : ViewModel() {
    private val _savedLocation = MutableLiveData<MutableList<SavedLocation>>()
    val savedLocation: LiveData<MutableList<SavedLocation>> get() = _savedLocation

    fun addSavedLocation(query: SavedLocation) {
        val currentList = _savedLocation.value ?: mutableListOf()
        currentList.add(query)
        _savedLocation.value = currentList
    }

    fun setSavedLocation(savedLocationList: MutableList<SavedLocation>) {
        _savedLocation.value = savedLocationList
    }

    fun deleteSavedLocation(savedLocation: SavedLocation) {
        val currentList = _savedLocation.value ?: return
        if (currentList.remove(savedLocation)) {
            _savedLocation.value = currentList
        }
    }
}