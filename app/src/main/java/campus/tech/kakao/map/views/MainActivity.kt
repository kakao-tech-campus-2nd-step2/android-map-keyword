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

class MainActivity : AppCompatActivity() {
    private lateinit var searchResultFragmentContainer: FragmentContainerView
    private lateinit var searchInput: EditText
    private lateinit var savedWordBar: View
    private val searchViewModel: SearchActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateViews()
        initiateLiveDataObservation()
        initiateSearchView()
    }

    private fun initiateLiveDataObservation(){
        searchViewModel.keywords.observe(this) {Log.d("KSC", it.toString())}
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
