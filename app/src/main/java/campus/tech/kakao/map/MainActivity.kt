package campus.tech.kakao.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import campus.tech.kakao.map.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sqliteInit: SQLiteInit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SQLite database
        sqliteInit = SQLiteInit(this)

        // Now you can launch coroutines from here
        lifecycleScope.launch(Dispatchers.IO) {
            sqliteInit.insertSampleData()
        }
    }
}
