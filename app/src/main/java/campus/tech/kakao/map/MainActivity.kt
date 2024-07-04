package campus.tech.kakao.map

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import campus.tech.kakao.map.databinding.ActivityMainBinding
import campus.tech.kakao.map.databinding.SearchHistoryItemBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(applicationContext)
    }

    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var container: LinearLayout
    private var placeList: List<Place> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.placeResult.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 검색 기록
//        val itemBinding = SearchHistoryItemBinding.inflate(layoutInflater, mainBinding.searchHistory, false)
//        itemBinding.history.text = ""
//        val layoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val customLayout = layoutInflater.inflate(R.layout.search_history_item, null)
//        var textView: TextView = customLayout.findViewById<TextView>(R.id.history)
//        mainBinding.searchHistory.addView(customLayout)

        if (placeList.isNullOrEmpty()) {
            mainBinding.emptyMainText.visibility = View.VISIBLE
        } else {
            mainBinding.emptyMainText.visibility = View.GONE
        }

        // 검색 필터링
        val searchEditText = mainBinding.search

        // 검색 결과 토대로 UI 업데이트

        viewModel.placeList.observe(this@MainActivity, Observer {
            Log.d("여기인가", "호출!")
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
                Log.d("여기인가",""+ searchText)
                viewModel.getSearchResult(searchText)
            }
        }
        )
    }
}
