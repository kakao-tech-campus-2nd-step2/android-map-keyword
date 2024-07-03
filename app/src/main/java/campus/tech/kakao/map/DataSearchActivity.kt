package campus.tech.kakao.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class DataSearchActivity : AppCompatActivity() {
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_search)

        searchViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[SearchViewModel::class.java]

        addSearchData()
    }

    private fun addSearchData(){
        for (i in 1..10){
            searchViewModel.addSearchData(SearchData("카페 $i","카페","서울 성동구 성수동 $i"))
        }
    }
}
