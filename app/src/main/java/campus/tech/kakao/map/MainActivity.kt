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
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputSearch = findViewById(R.id.inputSearch)
        buttonX = findViewById(R.id.buttonX)
        noResult = findViewById(R.id.noResult)
        searchListView = findViewById(R.id.searchListView)

        dbHelper = DbHelper(this)

        insertInitialData(dbHelper)

        buttonX.setOnClickListener {
            inputSearch.text.clear()
        }
    }

    private fun insertInitialData(dbHelper: DbHelper) {
        for(i in 1..10) {
            val name = "카페 $i"
            val address = "서울 성동구 성수동 $i"
            val category = "카페"
            dbHelper.insertData(name, address, category)
        }

        for(i in 1..10) {
            val name = "약국 $i"
            val address = "서울 강남구 대치동 $i"
            val category = "약국"
            dbHelper.insertData(name, address, category)
        }
    }

    private fun searchDatabase(query: String) {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${PlaceContract.TABLE_NAME} WHERE " +
                    "${PlaceContract.COLUMN_NAME} LIKE ? OR " +
                    "${PlaceContract.COLUMN_ADDRESS} LIKE ? OR " +
                    "${PlaceContract.COLUMN_CATEGORY} LIKE ?",
            arrayOf("%$query%", "%$query%", "%$query%")
        )

        val results = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.COLUMN_NAME))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.COLUMN_ADDRESS))
            val category = cursor.getString(cursor.getColumnIndexOrThrow(PlaceContract.COLUMN_CATEGORY))
            results.add("Name: $name, Address: $address, Category: $category")
        }
        cursor.close()
    }
}
