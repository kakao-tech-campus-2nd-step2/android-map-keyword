package campus.tech.kakao.map.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import campus.tech.kakao.map.DataRepository.SearchDataRepository
import campus.tech.kakao.map.Data.SearchData

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SearchDataRepository = SearchDataRepository(application)

    private val _searchDataList = MutableLiveData<List<SearchData>>()

    init {
        _searchDataList.value = repository.getSearchDataList()
    }

    //검색에 사용될 DB 생성
    fun makeSearchData() {
        val currentList = _searchDataList.value.orEmpty().toMutableList()

        for (i in 1..10) {
            val cafeData = SearchData("카페$i", "카페", "서울 성동구 성수동 $i")
            val pharmacyData = SearchData("약국$i", "약국", "서울 강남구 대치동 $i")

            if (checkNoExist(currentList, cafeData)) {
                repository.insertSearchData(cafeData)
            }

            if (checkNoExist(currentList, pharmacyData)) {
                repository.insertSearchData(pharmacyData)
            }
        }
        _searchDataList.value = repository.getSearchDataList()
    }

    //DB에 데이터 추가 전 중복 검사 (현재 DB에 없으면 true)
    private fun checkNoExist(currentList: MutableList<SearchData>, data: SearchData): Boolean {
        return !currentList.any { it.name == data.name && it.category == data.category && it.address == data.address }
    }

    fun loadSearchData(data: String): List<SearchData> {
        return repository.getResultDataList(data)
    }
}