package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(applicationContext)
    }

    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private var placeList: List<Place> = emptyList()
    private lateinit var searchHistoryList: ArrayList<SearchHistory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.placeResult.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (placeList.isNullOrEmpty()) {
            mainBinding.emptyMainText.visibility = View.VISIBLE
        } else {
            mainBinding.emptyMainText.visibility = View.GONE
        }

        // 검색 필터링
        val searchEditText = mainBinding.search

        // 검색 결과 토대로 UI 업데이트
        viewModel.placeList.observe(this@MainActivity, Observer {
            (mainBinding.placeResult.adapter as? PlaceAdapter)?.setData(it)
            if (it.isNullOrEmpty()) {
                mainBinding.emptyMainText.visibility = View.VISIBLE
            } else {
                mainBinding.emptyMainText.visibility = View.GONE
            }
        })

        placeAdapter = PlaceAdapter(placeList, LayoutInflater.from(this@MainActivity))
        mainBinding.placeResult.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = placeAdapter
        }

        searchEditText.addTextChangedListener(object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchText = searchEditText.text.toString()
                viewModel.getSearchResult(searchText)
            }
        }
        )

        // X 누르면 초기화
        mainBinding.xmark.setOnClickListener {
            searchEditText.setText("")
        }

        // PlaceAdapter OnclickListener
        searchHistoryList = MyApplication.prefs.getArrayList(Constants.SEARCH_HISTORY_KEY)
        if (searchHistoryList.isEmpty()) {
            searchHistoryList = ArrayList()
        }

        historyAdapter = HistoryAdapter(searchHistoryList, LayoutInflater.from(this@MainActivity))
        mainBinding.searchHistory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = historyAdapter
        }

        placeAdapter.itemClickListener = object : PlaceAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = placeAdapter.getItem(position)
                val searchHistory = SearchHistory(item.name)

                MyApplication.prefs.savePreference(Constants.SEARCH_HISTORY_KEY, searchHistory ,searchHistoryList)
                historyAdapter.setData(searchHistoryList)
            }
        }

        // HistoryAdapter OnclickListener
        historyAdapter.itemClickListener = object : HistoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = searchHistoryList[position]
                searchEditText.setText(item.searchHistory)
            }

            override fun onXMarkClick(position: Int) {
                MyApplication.prefs.deleteArrayListItem(Constants.SEARCH_HISTORY_KEY, position)
                searchHistoryList = MyApplication.prefs.getArrayList(Constants.SEARCH_HISTORY_KEY)
                historyAdapter.setData(searchHistoryList)
            }
        }
    }
}
