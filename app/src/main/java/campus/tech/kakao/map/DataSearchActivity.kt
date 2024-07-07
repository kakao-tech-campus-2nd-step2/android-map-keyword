package campus.tech.kakao.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DataSearchActivity : AppCompatActivity() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var recentViewModel: RecentViewModel
    private lateinit var editText: EditText
    private lateinit var resultDataAdapter:SearchDataAdapter
    private lateinit var resultData: List<SearchData>
    private lateinit var searchDataListView: RecyclerView
    private lateinit var recentSearchListView: ListView
    private lateinit var noResultNotice: TextView
    private lateinit var deleteBtn: ImageButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_search)

        //ViewModel 생성
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        //검색에 사용될 DB 생성
        searchViewModel.makeSearchData()

        recentViewModel = ViewModelProvider(this)[RecentViewModel::class.java]

        searchDataListView= findViewById(R.id.searchResulListView)
        editText = findViewById(R.id.searchBar)
        noResultNotice = findViewById(R.id.noResult)
        deleteBtn = findViewById(R.id.deleteInput)
        recentSearchListView = findViewById(R.id.recentSearchListView)

        recentViewModel.getRecentDataLiveData().observe(this, Observer{
            recentData -> recentSearchListView.adapter = RecentSearchAdapter(recentData)
        })

        resetButtonListener()

        searchDataListView.layoutManager = LinearLayoutManager(this)
        //어뎁터 초기화
        resultDataAdapter = SearchDataAdapter(emptyList(), recentViewModel)
        searchDataListView.adapter = resultDataAdapter


        setTextWatcher()
    }

    private fun setTextWatcher(){
       editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchInput = editText.text.trim().toString()
                resultData = searchViewModel.loadSearchData(searchInput)
                if (searchInput.isNotEmpty() && resultData.isNotEmpty()) {
                    noResultNotice.visibility = View.GONE
                    resultDataAdapter.updateData(resultData)
                } else {
                    noResultNotice.visibility = View.VISIBLE
                    resultDataAdapter.updateData(emptyList())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun resetButtonListener(){
        deleteBtn.setOnClickListener{
            editText.text.clear()
        }
    }
}
