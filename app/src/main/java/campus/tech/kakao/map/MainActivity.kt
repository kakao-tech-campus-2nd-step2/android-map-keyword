package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var db: SearchDbHelper

    private lateinit var searchWord: EditText
    private lateinit var deleteSearchWord: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = SearchDbHelper(context = this)

        recyclerView = findViewById(R.id.recyclerView)
        searchWord = findViewById(R.id.searchWord)
        deleteSearchWord = findViewById(R.id.deleteSearchWord)

        adapter = SearchAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        deleteWord()
        saveDb()
        loadDb()

    }

    private fun saveDb() {
        val wDb = db.writableDatabase

        wDb.delete(SearchData.TABLE_NAME, null, null)

        val values = ContentValues()

        for (count in 1 until 11) {
            values.put(SearchData.TABLE_COLUMN_NAME, "카페$count")
            values.put(SearchData.TABLE_COLUMN_ADDRESS, "서울 성동구 성수동 $count")
            values.put(SearchData.TABLE_COLUMN_CATEGORY, "카페")
            wDb.insert(SearchData.TABLE_NAME, null, values)

            values.put(SearchData.TABLE_COLUMN_NAME, "약국$count")
            values.put(SearchData.TABLE_COLUMN_ADDRESS, "서울 강남구 대치동 $count")
            values.put(SearchData.TABLE_COLUMN_CATEGORY, "약국")
            wDb.insert(SearchData.TABLE_NAME, null, values)

            values.clear()
        }
    }

    private fun loadDb() {
        val rDb = db.readableDatabase

        val cursor = rDb.query(
            SearchData.TABLE_NAME,
            arrayOf(
                SearchData.TABLE_COLUMN_NAME,
                SearchData.TABLE_COLUMN_ADDRESS,
                SearchData.TABLE_COLUMN_CATEGORY
            ),
            null,
            null,
            null,
            null,
            null
        )

        val searchDataList = mutableListOf<SearchData>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(SearchData.TABLE_COLUMN_NAME))
                val address = getString(getColumnIndexOrThrow(SearchData.TABLE_COLUMN_ADDRESS))
                val category = getString(getColumnIndexOrThrow(SearchData.TABLE_COLUMN_CATEGORY))
                searchDataList.add(SearchData(name, address, category))
            }
        }
        cursor.close()

        adapter.searchDataList = searchDataList
        adapter.notifyDataSetChanged()
    }

    private fun deleteWord() {
        deleteSearchWord.setOnClickListener {
            searchWord.text.clear()
        }
    }
}



