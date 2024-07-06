package campus.tech.kakao.map

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var model: MainViewModel
    private lateinit var search:EditText
    private lateinit var clear: TextView
    private lateinit var noResult: TextView
    private lateinit var searchResult: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UISetting()
        searchResult.layoutManager = LinearLayoutManager(this)
        search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isEmpty()){
                    noResult.visibility = View.VISIBLE
                    searchResult.visibility = View.GONE
                }else{
                    noResult.visibility = View.GONE
                    searchResult.visibility = View.VISIBLE
                    model.search(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        model = ViewModelProvider(this).get(MainViewModel::class.java)
        model.placeList.observe(this, Observer {
            if (it.isNullOrEmpty()){
                noResult.visibility = View.VISIBLE
                searchResult.visibility = View.GONE
            }else{
                noResult.visibility = View.GONE
                searchResult.visibility = View.VISIBLE
                searchResult.adapter = RecyclerViewAdapter(it)
            }
        })

    }

    fun UISetting(){
        search = findViewById(R.id.search)
        clear = findViewById(R.id.search_clear)
        noResult = findViewById(R.id.no_search_result)
        searchResult = findViewById(R.id.search_result_recycler_view)
    }

}
