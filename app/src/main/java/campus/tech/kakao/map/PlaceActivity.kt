package campus.tech.kakao.map

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class PlaceActivity : AppCompatActivity() {
    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var placeViewModelFactory: PlaceViewModelFactory
    val searchEditText = findViewById<EditText>(R.id.searchEditText)
    val removeButton = findViewById<ImageButton>(R.id.cancelButton)
    val placeRecyclerView = findViewById<RecyclerView>(R.id.placeRecyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
        placeViewModelFactory = PlaceViewModelFactory(this)
        placeViewModel = ViewModelProvider(this, placeViewModelFactory).get(PlaceViewModel::class.java)
    }
}