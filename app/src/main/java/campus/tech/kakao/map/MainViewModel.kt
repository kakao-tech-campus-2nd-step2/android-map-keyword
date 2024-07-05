package campus.tech.kakao.map

import android.app.Application
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

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DbHelper(application)

    private val _searchResults = MutableLiveData<List<String>>()
    val searchResults: LiveData<List<String>> get() = _searchResults

    fun insertInitialData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(dbHelper.isDBEmpty()) {
                    for (i in 1..10) {
                        dbHelper.insertData("카페 $i", "서울 성동구 성수동 $i", "카페")
                        dbHelper.insertData("약국 $i", "서울 강남구 대치동 $i", "약국")
                    }
                }
            }
        }
    }

    fun searchDatabase(query: String) {
        viewModelScope.launch {
            val results = withContext(Dispatchers.IO) {
                dbHelper.searchDatabase(query)
            }
            _searchResults.postValue(results)
        }
    }
}