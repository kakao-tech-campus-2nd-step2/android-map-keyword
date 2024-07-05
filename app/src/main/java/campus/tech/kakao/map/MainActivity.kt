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
    private lateinit var locationRecyclerView: RecyclerView

    private lateinit var savedLocationViewModel: SavedLocationViewModel
    private lateinit var savedLocationAdapter: SavedLocationAdapter
    private lateinit var savedLocationRecyclerView: RecyclerView

    private lateinit var locationDbHelper: LocationDbHelper
    private lateinit var locationDbAccessor: LocationDbAccessor

    private lateinit var xButton: ImageView
    private lateinit var searchEditText: EditText
    private lateinit var NoResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupSearchFeature()
        setupSavedLocationsFeature()
        setupLocationsFeature()
    }

    private fun setupSavedLocationsFeature() {
        initializeSavedLocationViewModel()
        initializeSavedLocationRecyclerView()
        observeSavedLocation()
    }

    private fun setupLocationsFeature() {
        initializeLocationViewModel()
        initializeLocationRecyclerView()
    }

    private fun setupSearchFeature() {
        setupSearchEditText()
        observeSearchedLocations()
        setupXButton()
    }

    private fun initializeSavedLocationViewModel() {
        val savedLocationList: MutableList<SavedLocation> = readSavedLocationData()
        savedLocationViewModel.setSavedLocation(savedLocationList)
    }

    private fun initializeLocationViewModel() {
        addLocationData() // 앱 설치 시 최초 1번만 실행하게 하려면 어떻게 해야할까?
        val locationList: MutableList<Location> = readLocationData()
        locationViewModel.setLocations(locationList)
    }

    private fun initViews() {
        locationDbHelper = LocationDbHelper(this)
        locationDbAccessor = LocationDbAccessor(locationDbHelper)

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        locationRecyclerView = findViewById(R.id.locationRecyclerView)

        savedLocationViewModel = ViewModelProvider(this).get(SavedLocationViewModel::class.java)
        savedLocationRecyclerView = findViewById(R.id.savedLocationRecyclerView)

        xButton = findViewById(R.id.xButton)
        searchEditText = findViewById(R.id.searchEditText)
        NoResultTextView = findViewById(R.id.NoResultTextView)
    }

    private fun initializeLocationRecyclerView() {
        locationAdapter = LocationAdapter { location ->
            savedLocationViewModel.addSavedLocation(SavedLocation(location.title))
            addSavedLocationData(location.title)
        }
        locationRecyclerView.layoutManager = LinearLayoutManager(this)
        locationRecyclerView.adapter = locationAdapter
    }

    private fun initializeSavedLocationRecyclerView() {
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
                    locationViewModel.searchLocations(query)
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

    private fun observeSearchedLocations() {
        locationViewModel.SearchedLocations.observe(this, Observer {
            locationAdapter.submitList(it?.toMutableList())
        })
    }

    private fun observeSavedLocation() {
        savedLocationViewModel.savedLocation.observe(this, Observer {
            Log.d("jieun", "observeSavedLocation"+it)
            if(it.size != 0) {
                savedLocationAdapter.submitList(it.toMutableList())
                savedLocationRecyclerView.visibility = View.VISIBLE
            }
            else {
                Log.d("jieun", "비었음")
                savedLocationAdapter.submitList(emptyList())
                savedLocationRecyclerView.visibility = View.GONE
            }

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
