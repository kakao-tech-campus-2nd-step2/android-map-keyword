package campus.tech.kakao.map.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import campus.tech.kakao.map.models.SearchKeywordRepository
import campus.tech.kakao.map.models.SearchResult
import campus.tech.kakao.map.models.SearchResultRepository


class SearchActivityViewModel (application: Application): AndroidViewModel(application) {

    private val searchResultRepository: SearchResultRepository = SearchResultRepository.getInstance(application)
    private val keywordRepository: SearchKeywordRepository = SearchKeywordRepository.getInstance(application)

    val searchResult: MutableLiveData<List<SearchResult>> = searchResultRepository.searchResult
    val keywords: MutableLiveData<List<String>> = keywordRepository.keywords

    fun search(query: String){
        searchResultRepository.search(query)
        addKeyword(query)
    }

    private fun addKeyword(keyword: String){
        keywordRepository.addKeyword(keyword)
    }

    fun deleteKeyword(keyword: String){
        keywordRepository.deleteKeyword(keyword)
    }
}