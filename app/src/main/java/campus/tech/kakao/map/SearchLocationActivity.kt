package campus.tech.kakao.map

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import campus.tech.kakao.map.db.LocationDbHelper

class SearchLocationActivity : AppCompatActivity() {
	private val dbHelper = LocationDbHelper(this)
	private lateinit var db: SQLiteDatabase

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_location)

		db = dbHelper.writableDatabase
	}
}