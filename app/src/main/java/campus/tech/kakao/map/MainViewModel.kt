package campus.tech.kakao.map

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val placeRepository: PlaceRepository) : ViewModel() {
    private val searchRepository: SearchRepository = SearchRepository()

    private var _placeList = MutableLiveData<List<Place>>()
    private val _searchHistoryList = MutableLiveData<List<SearchHistory>>()

    val searchHistoryList: LiveData<List<SearchHistory>>
        get() = _searchHistoryList
    init {
        _searchHistoryList.value = searchRepository.getSearchHistory()
    }
    val placeList: LiveData<List<Place>>
        get() = _placeList

    fun insertPlace(place: Place) {
        placeRepository.insertPlace(place)
    }

    fun getPlace(): List<Place>? {
        return placeRepository.getPlace()
    }

    override fun onCleared() {
        super.onCleared()
        placeRepository.dbClose()
    }

    fun getSearchResult(searchText: String) {
        if (searchText.isEmpty()) {
            _placeList.postValue(emptyList())
        } else {
            val results = placeRepository.getSearchResults(searchText)
            _placeList.postValue(results)
        }
    }

    fun saveSearchHistory(searchHistory: SearchHistory) {
        searchRepository.saveSearchHistory(searchHistory)
        _searchHistoryList.value = searchRepository.getSearchHistory()
    }

    fun deleteSearchHistory(position: Int) {
        searchRepository.deleteSearchHistory(position)
        _searchHistoryList.value = searchRepository.getSearchHistory()
    }

    fun getSearchHistoryList() {
        _searchHistoryList.value = searchRepository.getSearchHistory()
    }
}