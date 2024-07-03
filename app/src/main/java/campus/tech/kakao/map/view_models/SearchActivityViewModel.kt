package campus.tech.kakao.map.view_models

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import campus.tech.kakao.map.models.SearchKeywordRepository
import campus.tech.kakao.map.models.SearchResult
import campus.tech.kakao.map.models.SearchResultRepository


class SearchActivityViewModel (application: Application): AndroidViewModel(application) {
    private val searchResultRepository: SearchResultRepository = SearchResultRepository.getInstance(application)
    private val keywordRepository: SearchKeywordRepository = SearchKeywordRepository.getInstance(application)

    val searchResult: MutableLiveData<List<SearchResult>> = searchResultRepository.searchResult
    val keywords: MutableLiveData<List<String>> = keywordRepository.keywords
    val searchText: MutableLiveData<String> = MutableLiveData("")

    init{
        searchResultRepository.search("")
        keywordRepository.getKeywords()
    }

    private fun search(query: String){
        searchResultRepository.search(query)
    }

    private fun addKeyword(keyword: String){
        keywordRepository.addKeyword(keyword)
    }

    private fun deleteKeyword(keyword: String){
        keywordRepository.deleteKeyword(keyword)
    }

    fun clickSearchResultItem(selectedItem: SearchResult){
        addKeyword(selectedItem.name)
    }

    fun changeSearchInputValue(value: String){
        searchText.value = value
        search(value)
    }

    fun clickKeywordDeleteButton(keyword: String){
        deleteKeyword(keyword)
    }

    fun clickKeyword(keyword: String){
        searchText.postValue(keyword)
        search(keyword)
    }
}