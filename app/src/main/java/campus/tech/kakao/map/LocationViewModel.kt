package campus.tech.kakao.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations

    private val _SearchedLocations = MutableLiveData<List<Location>>()
    val SearchedLocations: LiveData<List<Location>> get() = _SearchedLocations

    fun setLocations(locations: List<Location>) {
        _locations.value = locations
        _SearchedLocations.value = null
    }

    fun searchLocations(query: String) {
        val filteredList = _locations.value?.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.address.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }
        _SearchedLocations.value = filteredList
    }
}