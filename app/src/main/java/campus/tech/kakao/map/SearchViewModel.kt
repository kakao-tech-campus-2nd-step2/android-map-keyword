package campus.tech.kakao.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SearchDataRepository = SearchDataRepository(application)

    private val _searchDataList = MutableLiveData<List<SearchData>>()
    init {
        _searchDataList.value = repository.getSearchDataList()
    }

    fun addSearchData(data: SearchData){
        val currentList = _searchDataList.value.orEmpty()
        if (!currentList.any{it.name == data.name && it.category == data.category && it.address == data.address}){
            repository.insertSearchData(data)
            _searchDataList.value = repository.getSearchDataList()
        }
    }

    fun loadSearchData(data: String): List<SearchData> {
        return repository.getResultDataList(data)
    }
}