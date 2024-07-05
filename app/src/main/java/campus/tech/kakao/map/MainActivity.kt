package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var savedLocationViewModel: SavedLocationViewModel
    private lateinit var searchResultRecyclerView: RecyclerView
    private lateinit var locationDbAccessor: LocationDbAccessor

    private lateinit var xButton: ImageView
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        savedLocationViewModel = ViewModelProvider(this).get(SavedLocationViewModel::class.java)

        val locationDbHelper = LocationDbHelper(this)
        locationDbAccessor = LocationDbAccessor(locationDbHelper)

        xButton = findViewById(R.id.xButton)
        searchEditText = findViewById(R.id.searchEditText)

        searchResultRecyclerView = findViewById(R.id.searchResultRecyclerView)
        setupRecyclerView()

        setupSearchEditText()
        setupXButton()

        addLocationData()
        val locationList: MutableList<Location> = readLocationData()
        locationViewModel.setLocations(locationList)
        observeFilteredLocations()

        locationAdapter.submitList(locationList)
    }

    private fun setupRecyclerView() {
        locationAdapter = LocationAdapter { location ->
            savedLocationViewModel.addSearchQuery(location.title)
            addSavedLocationData(location.title)
        }
        searchResultRecyclerView.layoutManager = LinearLayoutManager(this)
        searchResultRecyclerView.adapter = locationAdapter
    }

    private fun setupSearchEditText() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                locationViewModel.filterLocations(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupXButton() {
        xButton.setOnClickListener {
            searchEditText.setText("")
        }
    }

    private fun observeFilteredLocations() {
        locationViewModel.filteredLocations.observe(this, { locations ->
            locationAdapter.submitList(locations)
        })
    }

    private fun observeSavedLocation() {
        savedLocationViewModel.locationHistory.observe(this, { history ->
            // 검색어 저장 목록 어답터 변경하기!
        })
    }

    private fun addLocationData() {
        for (i in 1..9) {
            locationDbAccessor.insertLocation("카페$i", "부산 부산진구 전포대로$i", "카페")
        }
        for (i in 1..9) {
            locationDbAccessor.insertLocation("음식점$i", "부산 부산진구 중앙대로$i", "음식점")
        }
    }

    private fun addSavedLocationData(title: String) {
        locationDbAccessor.insertSavedLocation(title)
    }

    private fun readLocationData(): MutableList<Location> {
        val result: MutableList<Location> = locationDbAccessor.getLocationAll()
        Log.d("jieun", "$result")
        return result
    }
}
