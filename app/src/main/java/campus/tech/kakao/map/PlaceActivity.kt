package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
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
    private lateinit var placeRecyclerView: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
        placeViewModelFactory = PlaceViewModelFactory(this)
        placeViewModel = ViewModelProvider(this, placeViewModelFactory).get(PlaceViewModel::class.java)

        searchEditText = findViewById(R.id.searchEditText)
        removeButton = findViewById(R.id.cancelButton)
        placeRecyclerView = findViewById(R.id.placeRecyclerView)
        placeAdapter = PlaceAdapter()

        placeRecyclerView.layoutManager = LinearLayoutManager(this)
        placeRecyclerView.adapter = placeAdapter

        placeViewModel.places.observe(this, { places ->
            placeAdapter.updateData(places)
        })

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                placeViewModel.searchPlaces(p0.toString())
                // LiveData 업데이트 후 RecyclerView에 반영
            }
            override fun afterTextChanged(p0: Editable?) {
                //
            }
        })
    }
}