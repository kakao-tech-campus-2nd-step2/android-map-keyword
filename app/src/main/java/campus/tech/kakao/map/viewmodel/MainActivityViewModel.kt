package campus.tech.kakao.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.model.Place
import campus.tech.kakao.map.repository.Repository
import java.util.Locale.Category

class MainActivityViewModel (private val repository: Repository) : ViewModel() {
    private var _place = MutableLiveData<MutableList<Place>>()
    val place : LiveData<MutableList<Place>> get() = _place

    fun getPlace(){
        _place.postValue(repository.getAllPlace())
    }

    fun getPlaceWithCategory(category: String){
        _place.postValue(repository.getPlaceWithCategory(category))
    }
}