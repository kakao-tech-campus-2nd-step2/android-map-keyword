package campus.tech.kakao.map

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = viewModel.getPlace()

        if (items == null) {
            binding.emptyMainText.visibility = View.VISIBLE
        } else {
            placeAdapter = PlaceAdapter(items)
            binding.placeResult.adapter = placeAdapter
        }
    }
}
