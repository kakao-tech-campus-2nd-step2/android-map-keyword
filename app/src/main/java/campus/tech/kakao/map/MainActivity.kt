package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
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

        // 검색어 기록 토대로 UI 업데이트
        viewModel.searchHistoryList.observe(this@MainActivity, Observer {
            historyAdapter.setData(it)
        })
        viewModel.getSearchHistoryList()

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

        historyAdapter = HistoryAdapter(
            viewModel.searchHistoryList.value ?: emptyList()
            , LayoutInflater.from(this@MainActivity))
        mainBinding.searchHistory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = historyAdapter
        }

        // PlaceAdapter OnclickListener
        placeAdapter.itemClickListener = object : PlaceAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = placeAdapter.getItem(position)
                val searchHistory = SearchHistory(item.name)
                viewModel.saveSearchHistory(searchHistory)
            }
        }

        // HistoryAdapter OnclickListener
        historyAdapter.itemClickListener = object : HistoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = viewModel.searchHistoryList.value?.get(position)
                if (item != null) {
                    searchEditText.setText(item.searchHistory)
                }
            }

            override fun onXMarkClick(position: Int) {
                viewModel.deleteSearchHistory(position)
            }
        }
    }
}
