package campus.tech.kakao.map

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModel(private val context: Context) {
    private val repository: Repository = Repository(context)

    private val _searchResults = MutableLiveData<List<Keyword>>()
    val searchResults: LiveData<List<Keyword>> = _searchResults

    fun search(query: String) {
        val results = repository.search(query)
        _searchResults.value = results
    }

    fun deleteKeyword(keyword: Keyword) {

    }
}

class SearchViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


