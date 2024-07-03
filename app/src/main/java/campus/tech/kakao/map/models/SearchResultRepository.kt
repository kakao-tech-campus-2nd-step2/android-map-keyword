package campus.tech.kakao.map.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchResultRepository(context: Context) {
    val searchResult: MutableLiveData<List<SearchResult>> = MutableLiveData(listOf())
    private lateinit var searchDb: SearchDbHelper

    init {
        searchDb = SearchDbHelper(context)
    }

    fun search(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = searchDb.querySearchResultsByName(text)
            searchResult.postValue(result)
        }
    }

    companion object {
        private var instance: SearchResultRepository? = null

        fun getInstance(context: Context): SearchResultRepository {
            if (instance == null) {
                instance = SearchResultRepository(context)
            }
            return instance!!
        }
    }
}