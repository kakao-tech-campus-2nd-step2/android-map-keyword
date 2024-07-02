package campus.tech.kakao.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel(private val repository: Repository): ViewModel(){
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>>
        get() = _locations

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