package campus.tech.kakao.map.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import campus.tech.kakao.map.data.SavedSearchWordRepository
import campus.tech.kakao.map.model.SavedSearchWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedSearchWordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SavedSearchWordRepository = SavedSearchWordRepository(application)
    private val _savedSearchWords = MutableLiveData<List<SavedSearchWord>>()

    val savedSearchWords: LiveData<List<SavedSearchWord>> get() = _savedSearchWords

    init {
        getAllSearchWords()
    }

    fun insertSearchWord(searchWord: SavedSearchWord) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertOrUpdateSearchWord(searchWord)
                getAllSearchWords()
            }
        }
    }

    fun deleteSearchWordById(searchWord: SavedSearchWord) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteSearchWordById(searchWord.id)
                val currentList = _savedSearchWords.value ?: emptyList()
                _savedSearchWords.postValue(currentList - searchWord)
            }
        }
    }

    private fun getAllSearchWords() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val searchWords = repository.getAllSearchWords()
                _savedSearchWords.postValue(searchWords)
            }
        }
    }
}
