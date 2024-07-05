package campus.tech.kakao.map.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.models.SearchDbHelper
import campus.tech.kakao.map.models.SearchResult

class MainActivity : AppCompatActivity() {
    lateinit var searchResultFragmentContainer: FragmentContainerView
    lateinit var searchInput: EditText
    lateinit var savedWordBar: View

    private fun initiateViews() {
        searchResultFragmentContainer = findViewById(R.id.fragment_container_search_result)
        searchInput = findViewById(R.id.input_search)
        savedWordBar = findViewById(R.id.saved_search_bar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateViews()

        getDataFromDb()
    }

    private fun getDataFromDb() {
        val data = SearchDbHelper.getInstance(applicationContext).queryAll()
        printDebugLog(data)
    }

    private fun printDebugLog(searchResults: List<SearchResult>) {
        val stringBuilder: StringBuilder = StringBuilder()
        for ((i, data) in searchResults.withIndex()) {
            stringBuilder.appendLine("[$i] name: ${data.name}, address: ${data.address}, type: ${data.type}")
        }
        Log.d("KSC", stringBuilder.toString())
    }
}
