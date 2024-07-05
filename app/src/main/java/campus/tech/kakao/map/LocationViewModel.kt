package campus.tech.kakao.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations

    private val _searchedLocations = MutableLiveData<List<Location>>()
    val searchedLocations: LiveData<List<Location>> get() = _searchedLocations

    fun setLocations(locations: List<Location>) {
        _locations.value = locations
        _searchedLocations.value = null
    }

    fun searchLocations(query: String) {
        val filteredList = _locations.value?.filter {
            it.title.contains(query, ignoreCase = true) || // ignoreCase=true -> 대소문자 무시함
                    it.address.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }
        _searchedLocations.value = filteredList
    }
}