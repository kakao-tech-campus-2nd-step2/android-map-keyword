package campus.tech.kakao.map

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class PlaceActivity : AppCompatActivity() {
    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var placeViewModelFactory: PlaceViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)
        placeViewModelFactory = PlaceViewModelFactory(this)
        placeViewModel = ViewModelProvider(this, placeViewModelFactory).get(PlaceViewModel::class.java)
    }
}