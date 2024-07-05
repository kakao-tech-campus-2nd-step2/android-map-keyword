package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationAdapter: LocationAdapter

    private lateinit var savedLocationViewModel: SavedLocationViewModel
    private lateinit var savedLocationAdapter: SavedLocationAdapter

    private lateinit var locationRecyclerView: RecyclerView
    private lateinit var savedLocationRecyclerView: RecyclerView

    private lateinit var locationDbAccessor: LocationDbAccessor

    private lateinit var xButton: ImageView
    private lateinit var searchEditText: EditText
    private lateinit var NoResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        savedLocationViewModel = ViewModelProvider(this).get(SavedLocationViewModel::class.java)

        val locationDbHelper = LocationDbHelper(this)
        locationDbAccessor = LocationDbAccessor(locationDbHelper)

        xButton = findViewById(R.id.xButton)
        searchEditText = findViewById(R.id.searchEditText)
        NoResultTextView = findViewById(R.id.NoResultTextView)

        locationRecyclerView = findViewById(R.id.locationRecyclerView)
        setupLocationRecyclerView()

        savedLocationRecyclerView = findViewById(R.id.savedLocationRecyclerView)
        setupSavedLocationRecyclerView()


        setupSearchEditText()
        setupXButton()

//        addLocationData()
        val locationList: MutableList<Location> = readLocationData()
        locationViewModel.setLocations(locationList)
        observeFilteredLocation()

        val savedLocationList: MutableList<SavedLocation> = readSavedLocationData()
        savedLocationViewModel.setSavedLocation(savedLocationList)
        observeSavedLocation()
        savedLocationAdapter.submitList(savedLocationList)

    }

    private fun setupLocationRecyclerView() {
        locationAdapter = LocationAdapter { location ->
            savedLocationViewModel.addSavedLocation(SavedLocation(location.title))
            addSavedLocationData(location.title)
        }
        locationRecyclerView.layoutManager = LinearLayoutManager(this)
        locationRecyclerView.adapter = locationAdapter
    }

    private fun setupSavedLocationRecyclerView() {
        savedLocationAdapter = SavedLocationAdapter { savedLocation ->
            deleteSavedLocationData(savedLocation.title)
            savedLocationViewModel.deleteSavedLocation(SavedLocation(savedLocation.title))
        }
        savedLocationRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        savedLocationRecyclerView.adapter = savedLocationAdapter
    }


    private fun setupSearchEditText() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isEmpty()) {
                    locationAdapter.submitList(emptyList())
                    NoResultTextView.visibility = View.VISIBLE
                } else {
                    locationViewModel.filterLocations(query)
                    NoResultTextView.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupXButton() {
        xButton.setOnClickListener {
            searchEditText.setText("")
        }
    }

    private fun observeFilteredLocation() {
        locationViewModel.filteredLocations.observe(this, Observer {
            locationAdapter.submitList(it?.toMutableList())
        })
    }

    private fun observeSavedLocation() {
        savedLocationViewModel.savedLocation.observe(this, Observer {
            Log.d("jieun", "observeSavedLocation")
            savedLocationAdapter.submitList(it?.toMutableList())
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

    private fun readSavedLocationData(): MutableList<SavedLocation> {
        val result: MutableList<SavedLocation> = locationDbAccessor.getSavedLocationAll()
        Log.d("jieun", "$result")
        return result
    }
    private fun deleteSavedLocationData(title: String) {
        locationDbAccessor.deleteSavedLocation(title)
    }
}
