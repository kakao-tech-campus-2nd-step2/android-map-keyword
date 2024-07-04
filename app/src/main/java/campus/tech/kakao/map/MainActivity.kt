package campus.tech.kakao.map

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var cancelButton: ImageButton
    private lateinit var noResultsTextView: TextView
    private lateinit var resultsListView: ListView

    private lateinit var dbHelper: PlaceDBHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)
        cancelButton = findViewById(R.id.cancelButton)
        noResultsTextView = findViewById(R.id.noResultsTextView)
        resultsListView = findViewById(R.id.resultsListView)

        dbHelper = PlaceDBHelper(this)

        try {
            db = dbHelper.writableDatabase
        } catch (e: Exception) {
            e.printStackTrace()
        }

        cancelButton.setOnClickListener {
            searchEditText.text.clear()
        }
    }
}


