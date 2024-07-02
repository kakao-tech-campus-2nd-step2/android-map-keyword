package campus.tech.kakao.map.models

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchResultRepository(context: Context) {
    private val searchResult: MutableLiveData<List<SearchResult>> = MutableLiveData(listOf())
    private lateinit var searchDb: SearchDbHelper

    init {
        searchDb = SearchDbHelper(context)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<SearchResult>>) {
        searchResult.observe(owner, observer)
    }

    fun search(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = searchDb.queryName(text)
            searchResult.value = result
        }
    }

    companion object {
        private var instance: SearchResultRepository? = null

        fun getInstance(context: Context): SearchResultRepository {
            if (SearchResultRepository.instance == null) {
                SearchResultRepository.instance = SearchResultRepository(context)
            }
            return SearchResultRepository.instance!!
        }
    }
}