package campus.tech.kakao.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val placeItems = viewModel.getPlace()

        if (placeItems == null) {
            binding.emptyMainText.visibility = View.VISIBLE
        } else {
            placeAdapter = PlaceAdapter(placeItems)
            binding.placeResult.adapter = placeAdapter
        }

        val layoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customLayout = layoutInflater.inflate(R.layout.search_history_item, null)
        var textView: TextView = customLayout.findViewById<TextView>(R.id.history)
        binding.searchHistory.addView(customLayout)
    }
}
