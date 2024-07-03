package campus.tech.kakao.map.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.view_models.SearchActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var searchResultFragmentContainer: FragmentContainerView
    private lateinit var searchInput: EditText
    private lateinit var savedKeywordListView: RecyclerView
    private val searchViewModel: SearchActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateViews()
        initiateSaveKeywordRecyclerView()
        setInitialValueToAdapter()
        initiateLiveDataObservation()
        initiateSearchView()
    }

    private fun initiateLiveDataObservation() {
        searchViewModel.keywords.observe(this) {
            (savedKeywordListView.adapter as? SearchKeywordAdapter)?.updateKeywords(it)
        }
    }

    private fun setInitialValueToAdapter() {
        searchViewModel.keywords.value?.let {
            (savedKeywordListView.adapter as? SearchKeywordAdapter)?.updateKeywords(it)
        }
    }

    private fun initiateSaveKeywordRecyclerView() {
        val adapter = SearchKeywordAdapter(LayoutInflater.from(this)) {
            searchInput.setText(it)
        }
        savedKeywordListView = findViewById(R.id.saved_search_bar)
        savedKeywordListView.adapter = adapter
        savedKeywordListView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun initiateViews() {
        searchResultFragmentContainer = findViewById(R.id.fragment_container_search_result)
        searchInput = findViewById(R.id.input_search)
        savedKeywordListView = findViewById(R.id.saved_search_bar)
    }

    private fun initiateSearchView() {
        searchInput.doAfterTextChanged {
            searchViewModel.search(it.toString())
        }
    }
}
