package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MapViewModel
    private lateinit var searchBox: EditText
    private lateinit var searchHistoryView: RecyclerView
    private lateinit var searchResultView: RecyclerView
    private lateinit var message: TextView
    private lateinit var clear: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MapViewModel(this)
        searchBox = findViewById(R.id.search_box)
        searchHistoryView = findViewById(R.id.search_history)
        searchResultView = findViewById(R.id.search_result)
        message = findViewById(R.id.message)
        clear = findViewById(R.id.clear)

        searchBox.doAfterTextChanged {
            it?.let {
                search(it.toString(), false)
            }
        }

        clear.setOnClickListener {
            searchBox.text.clear()
        }

    }

    fun search(locName: String, isExactMatch: Boolean) {
        val searchResult = viewModel.searchLocation(locName, isExactMatch)
        searchResultView.adapter = MapRecyclerAdapter(searchResult, layoutInflater, this@MainActivity, ::writeHistory)
        if (searchResult.isNotEmpty() && locName.isNotEmpty()) {
            searchResultView.isVisible = true
            message.isVisible = false
        } else {
            searchResultView.isVisible = false
            message.isVisible = true
        }
        searchResultView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
    }
    fun writeHistory(name: String): Unit {
        viewModel.writeHistory(name)
    }

}
