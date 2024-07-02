package campus.tech.kakao.map

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val close = findViewById<ImageView>(R.id.close)
        val searchRepository = SearchRepo(DatabaseHelper())
        val searchViewModel = SearchViewModel.getInstance(searchRepository)
        searchViewModel.insertSearchData("팜하니")
        searchViewModel.insertSearchData("판교")
        searchViewModel.insertSearchData("카카오")
        searchViewModel.insertSearchData("병원")
    }

    private fun DatabaseHelper(): DatabaseHelper {
        TODO("Not yet implemented")
    }
}
