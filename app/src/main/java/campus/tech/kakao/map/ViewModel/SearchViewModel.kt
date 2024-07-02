package campus.tech.kakao.map.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.Repository.PlaceRepository

class SearchViewModel(private val repository: PlaceRepository) : ViewModel() {
    val currentResult : MutableLiveData<List<Place>> = MutableLiveData()
    fun getAllPlace() {
        currentResult.value = repository.getAllPlace()
    }

    fun searchPlace(string : String) {
        currentResult.value = if(string.isEmpty()) listOf<Place>()
            else repository.getSimilarPlacesByName(string)
    }

    fun deletePlace(name : String){
        repository.deletePlace(name)
    }

}