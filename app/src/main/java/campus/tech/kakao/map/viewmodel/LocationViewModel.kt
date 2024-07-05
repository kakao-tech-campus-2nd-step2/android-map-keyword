package campus.tech.kakao.map.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.model.Location
import campus.tech.kakao.map.model.Repository

class LocationViewModel(private val repository: Repository): ViewModel(){
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>>
        get() = _locations

    val searchText = MutableLiveData<String>()

    fun clearSearch() {
        searchText.value = ""
    }

    fun select(name: String): List<Location>{
         return repository.selectData(name)
    }

    fun insert(location: Location){
        repository.insertData(location)
        fetch()
    }

    fun update(location: Location){
        repository.updateData(location)
        fetch()
    }

    fun delete(name:String){
        repository.deleteData(name)
        fetch()
    }

    fun dropTable(){
        repository.dropTable()
        fetch()
    }

    fun fetch(){
        _locations.setValue(repository.getAll())
    }

    companion object {
        @Volatile
        private var INSTANCE: LocationViewModel? = null
        fun getInstance(repository: Repository): LocationViewModel {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationViewModel(repository).also { INSTANCE = it }
            }
        }
    }
}