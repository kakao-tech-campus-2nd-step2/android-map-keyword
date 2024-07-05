package campus.tech.kakao.map.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.model.Location
import campus.tech.kakao.map.model.Repository

class LocationViewModel(private val repository: Repository){

    val searchText = MutableLiveData<String>()

    fun clearSearch() {
        searchText.value = ""
    }

    fun select(name: String): List<Location>{
         return repository.selectData(name)
    }
    fun saveLog(logList: List<Location>){
        repository.saveLog(logList)
    }

    fun getLog(): MutableList<Location>{
        return repository.getLog().toMutableList()
    }

    fun dropLogTable(){
        repository.dropLogTable()
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