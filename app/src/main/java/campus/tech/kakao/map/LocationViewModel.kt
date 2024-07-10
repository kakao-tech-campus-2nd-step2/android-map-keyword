package campus.tech.kakao.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel(private val locationDbAccessor: LocationDbAccessor) : ViewModel() {
    private val _locations = MutableLiveData<List<Location>>()

    private val _searchedLocations = MutableLiveData<List<Location>>()
    val searchedLocations: LiveData<List<Location>> get() = _searchedLocations

    fun setLocations() {
        _locations.value = readLocationData()
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

    fun insertLocation() {
        for (i in 1..9) {
            locationDbAccessor.insertLocation("카페$i", "부산 부산진구 전포대로$i", "카페")
        }
        for (i in 1..9) {
            locationDbAccessor.insertLocation("음식점$i", "부산 부산진구 중앙대로$i", "음식점")
        }
    }
    private fun readLocationData(): MutableList<Location> {
        val result: MutableList<Location> = locationDbAccessor.getLocationAll()
        Log.d("jieun", "$result")
        return result
    }
}