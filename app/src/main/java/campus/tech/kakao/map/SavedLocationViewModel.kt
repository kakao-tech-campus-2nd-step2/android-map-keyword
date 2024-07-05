package campus.tech.kakao.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedLocationViewModel : ViewModel() {
    private val _locationHistory = MutableLiveData<MutableList<String>>()
    val locationHistory: LiveData<MutableList<String>> get() = _locationHistory

    fun addSearchQuery(query: String) {
        val currentList = _locationHistory.value ?: mutableListOf()
        currentList.add(query)
        _locationHistory.value = currentList
    }

    fun setLocationHistory(history: MutableList<String>) {
        _locationHistory.value = history
    }
}