package campus.tech.kakao.map

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputSearch: EditText
    private lateinit var buttonX: Button
    private lateinit var noResult: TextView
    private lateinit var searchListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputSearch = findViewById(R.id.inputSearch)
        buttonX = findViewById(R.id.buttonX)
        noResult = findViewById(R.id.noResult)
        searchListView = findViewById(R.id.searchListView)

        buttonX.setOnClickListener {
            inputSearch.text.clear()
        }
    }
}
