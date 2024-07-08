package campus.tech.kakao.map.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import campus.tech.kakao.map.databinding.ActivityMainBinding
import campus.tech.kakao.map.ui.adapter.HistoryAdapter
import campus.tech.kakao.map.ui.adapter.PlaceAdapter
import campus.tech.kakao.map.utils.clearTextWatcher
import campus.tech.kakao.map.utils.doOnTextChanged
import campus.tech.kakao.map.viewmodel.PlaceViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PlaceViewModel by viewModels()
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupView()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnDelete.setOnClickListener {
            binding.etSearch.setText(null)
        }

        binding.etSearch.doOnTextChanged { text ->
            handleSearchTextChanged(text.toString())
        }
    }

    private fun handleSearchTextChanged(searchText: String) {
        if (searchText.trim().isEmpty()) {
            binding.tvNoResults.visibility = View.VISIBLE
            binding.recyclerViewPlaces.visibility = View.INVISIBLE
        } else {
            binding.tvNoResults.visibility = View.INVISIBLE
            binding.recyclerViewPlaces.visibility = View.VISIBLE
            viewModel.searchPlaces(searchText)
        }
    }

    private fun setupView() {
        placeAdapter = PlaceAdapter { name ->
            viewModel.addHistory(name)
        }

        historyAdapter = HistoryAdapter { name ->
            viewModel.removeHistory(name)
        }

        binding.recyclerViewPlaces.apply {
            adapter = placeAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.recyclerViewHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeViewModel() {
        observePlaces()
        observeHistory()
    }

    private fun observePlaces() {
        viewModel.places.observe(this, Observer { places ->
            placeAdapter.submitList(places)
        })
    }

    private fun observeHistory() {
        viewModel.history.observe(this, Observer { history ->
            historyAdapter.submitList(history)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.etSearch.clearTextWatcher()
    }
}
