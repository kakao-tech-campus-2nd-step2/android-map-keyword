package campus.tech.kakao.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private var _searchText = MutableLiveData<String>()
    val searchText: LiveData<String>
        get() = _searchText

    fun clearSearchText() {
        _searchText.value = ""
    }

}