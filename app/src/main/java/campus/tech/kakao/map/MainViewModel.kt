package campus.tech.kakao.map

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.temporal.TemporalQuery
import android.util.Log

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DbHelper(application)
    private val sharedPreferences = application.getSharedPreferences("search_prefs", Context.MODE_PRIVATE)

    private val _searchResults = MutableLiveData<List<String>>()
    val searchResults: LiveData<List<String>> get() = _searchResults

    private val _savedSearches = MutableLiveData<List<String>>()
    val savedSearches: LiveData<List<String>> get() = _savedSearches

    init {
        loadSavedSearches()
    }

    fun insertInitialData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(dbHelper.isDBEmpty()) {
                    for (i in 1..10) {
                        dbHelper.insertData("카페 $i", "서울 성동구 성수동 $i", "카페")
                        dbHelper.insertData("약국 $i", "서울 강남구 대치동 $i", "약국")
                    }
                    Log.d("MainViewModel", "Initial data inserted")
                }
            }
        }
    }

    fun searchDatabase(query: String) {
        viewModelScope.launch {
            val results = withContext(Dispatchers.IO) {
                dbHelper.searchDatabase(query)
            }
            Log.d("MainViewModel", "Search results: $results")
            _searchResults.postValue(results)
        }
    }

    fun addSearch(search: String) {
        val currentSearches = _savedSearches.value?.toMutableList() ?: mutableListOf()
        if (!currentSearches.contains(search)) {
            currentSearches.add(0, search)
            _savedSearches.postValue(currentSearches)
            saveSearchesToPreferences(currentSearches)
        }
    }

    fun removeSearch(search: String) {
        val currentSearches = _savedSearches.value?.toMutableList() ?: mutableListOf()
        currentSearches.remove(search)
        _savedSearches.postValue(currentSearches)
        saveSearchesToPreferences(currentSearches)
    }

    private fun loadSavedSearches() {
        val searches = sharedPreferences.getStringSet("saved_searches", emptySet())?.toList() ?: emptyList()
        _savedSearches.postValue(searches)
    }

    private fun saveSearchesToPreferences(searches: List<String>) {
        sharedPreferences.edit().putStringSet("saved_searches", searches.toSet()).apply()
    }
}