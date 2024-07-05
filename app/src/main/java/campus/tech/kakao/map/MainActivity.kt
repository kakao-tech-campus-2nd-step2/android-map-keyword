package campus.tech.kakao.map

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import androidx.activity.viewModels
import campus.tech.kakao.map.R
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SearchViewModel by viewModels { SearchViewModelFactory(applicationContext) }
    private lateinit var searchResultsAdapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchEditText = binding.editSearch
        val searchResultsRecyclerView = binding.recyclerView
        val savedKeywordsRecyclerView = binding.savedKeywordsRecyclerView
        val noResultsTextView = binding.noResultsTextView

        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        savedKeywordsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val savedKeywordsAdapter = SavedKeywordsAdapter(emptyList()) { keyword ->
            viewModel.deleteKeyword(keyword)
        }
        savedKeywordsRecyclerView.adapter = savedKeywordsAdapter

        viewModel.searchResults.observe(this) { results ->
            searchResultsAdapter.updateData(results)
            noResultsTextView.visibility = if (results.isEmpty()) View.VISIBLE else View.GONE
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                viewModel.search(query)
                noResultsTextView.visibility = if (query.isEmpty()) View.VISIBLE else View.GONE
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        initializeDatabase()
    }

    private fun initializeDatabase() {
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            val repository = Repository(this)
            repository.populateInitialData()
            prefs.edit().putBoolean("isFirstRun", false).apply()
        }
        Repository(this).printAllData()
    }
}
