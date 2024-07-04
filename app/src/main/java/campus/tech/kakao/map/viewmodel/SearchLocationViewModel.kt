package campus.tech.kakao.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.model.Location
import campus.tech.kakao.map.model.SearchLocationRepository

class SearchLocationViewModel : ViewModel() {
	private lateinit var repository: SearchLocationRepository
	fun setRepository(repository: SearchLocationRepository) {
		this.repository = repository
	}

	private val _location = MutableLiveData<List<Location>>()
	val location: LiveData<List<Location>> = _location

	fun searchLocation(category: String) {
		_location.value = repository.searchLocation(category)
	}

	fun addHistory(locationName: String) {
		repository.addHistory(locationName)
	}
}