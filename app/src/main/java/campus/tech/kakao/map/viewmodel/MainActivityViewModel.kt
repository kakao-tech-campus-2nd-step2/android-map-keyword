package campus.tech.kakao.map.viewmodel

import androidx.lifecycle.ViewModel
import campus.tech.kakao.map.repository.Repository

class MainActivityViewModel (private val repository: Repository) : ViewModel() {
    fun getPlace(){
        repository.getAllPlace()
    }
}