package campus.tech.kakao.map.views

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.view_models.SearchActivityViewModel
import campus.tech.kakao.map.views.adapters.SearchKeywordAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var searchResultFragmentContainer: FragmentContainerView
    private lateinit var searchInput: EditText
    private lateinit var keywordRecyclerView: RecyclerView
    private val searchViewModel: SearchActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateViews()
        initiateSaveKeywordRecyclerView()
        setInitialValueToAdapter()
        initiateLiveDataObservation()
    }

    private fun initiateLiveDataObservation() {
        searchViewModel.searchText.observe(this) {
            if (it != searchInput.text.toString())
                searchInput.setText(it)
        }
        searchViewModel.keywords.observe(this) {
            (keywordRecyclerView.adapter as? SearchKeywordAdapter)?.updateKeywords(it)
        }
    }

    private fun setInitialValueToAdapter() {
        searchViewModel.keywords.value?.let {
            (keywordRecyclerView.adapter as? SearchKeywordAdapter)?.updateKeywords(it)
        }
    }

    private fun initiateSaveKeywordRecyclerView() {
        val adapter = SearchKeywordAdapter(LayoutInflater.from(this), {
            searchViewModel.clickKeyword(it)
        }, {
            searchViewModel.clickKeywordDeleteButton(it)
        })
        keywordRecyclerView = findViewById(R.id.saved_search_bar)
        keywordRecyclerView.adapter = adapter
        keywordRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun initiateViews() {
        searchResultFragmentContainer = findViewById(R.id.fragment_container_search_result)
        searchInput = findViewById(R.id.input_search)
        keywordRecyclerView = findViewById(R.id.saved_search_bar)

        searchInput.doAfterTextChanged {
            searchViewModel.changeSearchInputValue(it.toString())
        }
    }
}
