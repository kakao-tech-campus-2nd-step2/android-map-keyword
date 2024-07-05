package campus.tech.kakao.map.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import campus.tech.kakao.map.repository.SearchRepository

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val searchRepo: SearchRepository = SearchRepository(application)

    fun insertDummyData(name: String, address: String, category: String) {
        searchRepo.insertPlaceDummyData(name, address, category)
    }

    fun getPlaces() {
        searchRepo.getAllPlaces()
    }
}