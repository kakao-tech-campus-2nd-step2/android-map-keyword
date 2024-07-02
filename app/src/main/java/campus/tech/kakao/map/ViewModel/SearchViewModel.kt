package campus.tech.kakao.map.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.Repository.PlaceRepository

class SearchViewModel(private val repository: PlaceRepository) : ViewModel() {
    fun getAllPlace() : List<Place>{
        return repository.getAllPlace()
    }

    fun deletePlace(name : String){
        repository.deletePlace(name)
    }
}