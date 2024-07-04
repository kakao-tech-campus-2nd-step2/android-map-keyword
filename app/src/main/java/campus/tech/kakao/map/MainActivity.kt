package campus.tech.kakao.map

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import campus.tech.kakao.map.databinding.ActivityMainBinding
import campus.tech.kakao.map.databinding.SearchHistoryItemBinding

class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val placeItems = viewModel.getPlace()

        if (placeItems == null) {
            mainBinding.emptyMainText.visibility = View.VISIBLE
        } else {
            placeAdapter = PlaceAdapter(placeItems)
            mainBinding.placeResult.adapter = placeAdapter
        }

        val itemBinding = SearchHistoryItemBinding.inflate(layoutInflater, mainBinding.searchHistory, false)
        itemBinding.history.text = ""
        val layoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customLayout = layoutInflater.inflate(R.layout.search_history_item, null)
        var textView: TextView = customLayout.findViewById<TextView>(R.id.history)
        mainBinding.searchHistory.addView(customLayout)

        val searchEditText = mainBinding.search
        searchEditText.addTextChangedListener(object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        )
    }
}
