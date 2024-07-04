package campus.tech.kakao.map.view

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import campus.tech.kakao.map.R
import campus.tech.kakao.map.model.LocationDbHelper

class SearchLocationActivity : AppCompatActivity() {
	private val dbHelper = LocationDbHelper(this)
	private lateinit var db: SQLiteDatabase

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_location)

		db = dbHelper.writableDatabase
	}
}