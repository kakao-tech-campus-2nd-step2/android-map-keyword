package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DataSearchActivity : AppCompatActivity() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var editText: EditText
    private lateinit var textWatcher :TextWatcher
    private lateinit var adapter:SearchDataAdapter
    private lateinit var resultData: List<SearchData>
    private lateinit var searchDataList: RecyclerView
    private lateinit var noResultNotice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_search)

        searchViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[SearchViewModel::class.java]

        searchDataList= findViewById(R.id.searchResulList)
        editText = findViewById(R.id.searchBar)
        noResultNotice = findViewById(R.id.noResult)

        addSearchData()

        searchDataList.layoutManager = LinearLayoutManager(this)

        setTextWatcher()
        editText.addTextChangedListener(textWatcher)
    }

    private fun addSearchData(){
        for (i in 1..10){
            searchViewModel.addSearchData(SearchData("카페 $i","카페","서울 성동구 성수동 $i"))
            searchViewModel.addSearchData(SearchData("약국 $i","약국","서울 강남구 대치동 $i"))
        }
    }

    private fun setTextWatcher(){
        textWatcher = object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchInput = editText.text.trim().toString()
                Log.d("yb0x00","현재 값 : $searchInput")
                resultData = searchViewModel.loadSearchData(searchInput)
                if (searchInput.isNotEmpty() && resultData.isNotEmpty()){
                    noResultNotice.visibility = View.GONE
                    adapter = SearchDataAdapter(resultData)
                    searchDataList.adapter = adapter
                }else{noResultNotice.visibility = View.VISIBLE
                    adapter = SearchDataAdapter(emptyList())
                    searchDataList.adapter = adapter
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }
}
