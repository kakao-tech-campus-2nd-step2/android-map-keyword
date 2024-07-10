package campus.tech.kakao.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedLocationViewModel(private val locationDbAccessor: LocationDbAccessor) : ViewModel() {
    private val _savedLocation = MutableLiveData<MutableList<SavedLocation>>()
    val savedLocation: LiveData<MutableList<SavedLocation>> get() = _savedLocation

    fun addSavedLocation(title: String) {
        val savedLocation = SavedLocation(title)
        val currentList = _savedLocation.value ?: mutableListOf()
        if (!currentList.contains(savedLocation)) {
            currentList.add(savedLocation)
            _savedLocation.value = currentList
        }
        locationDbAccessor.insertSavedLocation(savedLocation.title)
    }

    fun setSavedLocation() {
        _savedLocation.value = readSavedLocationData()
    }

    fun deleteSavedLocation(savedLocation: SavedLocation) {
        val currentList = _savedLocation.value ?: return
        if (currentList.remove(savedLocation)) {
            _savedLocation.value = currentList
        }
        deleteSavedLocationData(savedLocation.title)
    }
    private fun readSavedLocationData(): MutableList<SavedLocation> {
        val result: MutableList<SavedLocation> = locationDbAccessor.getSavedLocationAll()
        Log.d("jieun", "$result")
        return result
    }
    private fun deleteSavedLocationData(title: String) {
        locationDbAccessor.deleteSavedLocation(title)
    }
}
