package campus.tech.kakao.map.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import campus.tech.kakao.map.models.SearchResult
import campus.tech.kakao.map.models.SearchResultRepository

class SearchActivityViewModelFactory(private val repository: SearchResultRepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchActivityViewModel(repository) as T
    }
}

class SearchActivityViewModel (private val repository: SearchResultRepository): ViewModel() {
    val searchResult: MutableLiveData<List<SearchResult>> = repository.searchResult

    fun search(query: String){
        repository.search(query)
    }
}