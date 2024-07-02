package campus.tech.kakao.map.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import campus.tech.kakao.map.R
import campus.tech.kakao.map.models.SearchDbHelper
import campus.tech.kakao.map.models.SearchResult
import campus.tech.kakao.map.models.SearchResultRepository
import campus.tech.kakao.map.view_models.SearchActivityViewModel
import campus.tech.kakao.map.view_models.SearchActivityViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var searchResultFragmentContainer: FragmentContainerView
    lateinit var searchInput: EditText
    lateinit var savedWordBar: View
    val searchViewModel: SearchActivityViewModel by viewModels {
        SearchActivityViewModelFactory((SearchResultRepository.getInstance(applicationContext)))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateViews()
        searchViewModel.searchResult.observe(this) { Log.d("KSC", it.toString()) }
        initiateSearchView()
    }

    private fun initiateViews() {
        searchResultFragmentContainer = findViewById(R.id.fragment_container_search_result)
        searchInput = findViewById(R.id.input_search)
        savedWordBar = findViewById(R.id.saved_search_bar)
    }

    private fun initiateSearchView() {
        searchInput.doAfterTextChanged {
            searchViewModel.search(it.toString())
        }
    }
}
