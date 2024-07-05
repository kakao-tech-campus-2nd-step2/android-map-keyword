package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = PlaceDbHelper(applicationContext)
        val db = dbHelper.writableDatabase
//        dbHelper.onUpgrade(db, 1, 2)
        val mainViewModel = MainViewModel(application)
        mainViewModel.insertInitData()
        var places = mutableListOf<Place>()
        places = dbHelper.searchPlaceName("")
        places.forEach {
            Log.d("testt", "onCreate: ${it.name}")
        }

    }
}
