package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
    private lateinit var searchNothing: TextView

    private var searchDataList = mutableListOf<SearchData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = SearchDbHelper(context = this)

        recyclerView = findViewById(R.id.recyclerView)
        searchWord = findViewById(R.id.searchWord)
        deleteSearchWord = findViewById(R.id.deleteSearchWord)
        searchNothing = findViewById(R.id.searchNothing)

        adapter = SearchAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        deleteWord()
        saveDb()
        loadDb()

        searchWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchTerm = s.toString()
                if (searchTerm.isEmpty()) {
                    recyclerView.visibility = android.view.View.GONE
                } else {
                    filterByCategory(searchTerm)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

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

        searchDataList.clear()

        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(SearchData.TABLE_COLUMN_NAME))
                val address = getString(getColumnIndexOrThrow(SearchData.TABLE_COLUMN_ADDRESS))
                val category = getString(getColumnIndexOrThrow(SearchData.TABLE_COLUMN_CATEGORY))
                searchDataList.add(SearchData(name, address, category))
            }
        }
        cursor.close()

        if (searchWord.text.isEmpty()) {
            adapter.searchDataList = emptyList()
            recyclerView.visibility = android.view.View.GONE
            searchNothing.visibility = android.view.View.VISIBLE
        } else {
            adapter.searchDataList = searchDataList
            recyclerView.visibility = android.view.View.VISIBLE
            searchNothing.visibility = android.view.View.GONE
        }
        adapter.notifyDataSetChanged()
    }

    private fun deleteWord() {
        deleteSearchWord.setOnClickListener {
            searchWord.text.clear()
            loadDb()
        }
    }

    private fun filterByCategory(category: String) {
        val filteredList = searchDataList.filter { it.category == category }
        adapter.searchDataList = filteredList
        adapter.notifyDataSetChanged()

        if (filteredList.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            searchNothing.visibility = android.view.View.VISIBLE
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            searchNothing.visibility = android.view.View.GONE
        }
    }
}



