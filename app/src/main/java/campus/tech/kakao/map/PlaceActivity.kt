package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaceActivity : AppCompatActivity() {
    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var placeViewModelFactory: PlaceViewModelFactory
    private lateinit var searchEditText: EditText
    private lateinit var removeButton: ImageButton
    private lateinit var emptyMessage: TextView
    private lateinit var placeRecyclerView: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
        placeViewModelFactory = PlaceViewModelFactory(this)
        placeViewModel =
            ViewModelProvider(this, placeViewModelFactory).get(PlaceViewModel::class.java)

        searchEditText = findViewById(R.id.searchEditText)
        removeButton = findViewById(R.id.cancelButton)
        placeRecyclerView = findViewById(R.id.placeRecyclerView)
        emptyMessage = findViewById(R.id.emptyMessage)
        placeAdapter = PlaceAdapter()

        placeRecyclerView.layoutManager = LinearLayoutManager(this)
        placeRecyclerView.adapter = placeAdapter

        placeViewModel.places.observe(this) { places ->
            if (places.isEmpty()) {
                showEmptyMessage()
            } else {
                emptyMessage.visibility = TextView.GONE
                placeRecyclerView.visibility = RecyclerView.VISIBLE
                placeAdapter.updateData(places)
            }
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                placeViewModel.searchPlaces(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }
        })

        removeButton.setOnClickListener {
            clearSearchEditText()
            showEmptyMessage()
        }
    }


    private fun showEmptyMessage() {
        emptyMessage.visibility = TextView.VISIBLE
        placeRecyclerView.visibility = RecyclerView.GONE
    }

    private fun clearSearchEditText() {
        searchEditText.text.clear()
    }
}