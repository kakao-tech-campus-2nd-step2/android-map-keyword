package campus.tech.kakao.map

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import campus.tech.kakao.map.R

class MainActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.edit_search)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        initializeDatabase()
    }

    private fun initializeDatabase() {
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            val repository = Repository(this)
            repository.populateInitialData()
            prefs.edit().putBoolean("isFirstRun", false).apply()
        }
        Repository(this).printAllData()
    }
}
