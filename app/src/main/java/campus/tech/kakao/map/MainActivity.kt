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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        val dbAccessor = LocationDbAccessor(context = this)
        val xButton: ImageView = findViewById(R.id.xButton)
        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val searchResultRecyclerView: RecyclerView = findViewById(R.id.searchResultRecyclerView)

        setupRecyclerView(searchResultRecyclerView)
        setupSearchEditText(searchEditText)
        setupXButton(xButton, searchEditText)

        val locationList: MutableList<Location> = readLocationData(dbAccessor)
        locationViewModel.setLocations(locationList)
        observeFilteredLocations()

        locationAdapter.submitList(locationList)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        locationAdapter = LocationAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = locationAdapter
    }

    private fun setupSearchEditText(searchEditText: EditText) {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                locationViewModel.filterLocations(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupXButton(xButton: ImageView, searchEditText: EditText) {
        xButton.setOnClickListener {
            searchEditText.setText("")
        }
    }

    private fun observeFilteredLocations() {
        locationViewModel.filteredLocations.observe(this, { locations ->
            locationAdapter.submitList(locations)
        })
    }

    private fun readLocationData(dbAccessor: LocationDbAccessor): MutableList<Location> {
        val result: MutableList<Location> = dbAccessor.getLocationAll()
        Log.d("jieun", "$result")
        return result
    }

    private fun addLocationData(dbAccessor: LocationDbAccessor) {
        for (i in 1..9) {
            dbAccessor.insertLocation("카페$i", "부산 부산진구 전포대로$i", "카페")
        }
        for (i in 1..9) {
            dbAccessor.insertLocation("음식점$i", "부산 부산진구 중앙대로$i", "음식점")
        }
    }
}
