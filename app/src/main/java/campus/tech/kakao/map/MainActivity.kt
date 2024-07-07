package campus.tech.kakao.map

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
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
    private lateinit var searchWordResult: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        searchResult.layoutManager = LinearLayoutManager(this)
        searchWordResult.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        
        search.doOnTextChanged { text, start, before, count ->
            val query = text.toString()
            if (query.isEmpty()){
                noResult.visibility = View.VISIBLE
                searchResult.visibility = View.GONE
            }else{
                noResult.visibility = View.GONE
                searchResult.visibility = View.VISIBLE
                model.search(query)
            }
        }
        model = ViewModelProvider(this)[MainViewModel::class.java]
        model.placeList.observe(this, Observer {
            if (it.isNullOrEmpty()){
                noResult.visibility = View.VISIBLE
                searchResult.visibility = View.GONE
            }else{
                noResult.visibility = View.GONE
                searchResult.visibility = View.VISIBLE
                searchResult.adapter = PlaceAdapter(it){ Place ->
                    model.addWord(Place)
                }
            }
        })
        model.loadWord()
        model.wordList.observe(this, Observer {
            if (it.isNullOrEmpty()){
                searchWordResult.visibility = View.GONE
            }
            else{
                searchWordResult.visibility = View.VISIBLE
                searchWordResult.adapter = WordAdapter(it) { SearchWord ->
                    model.deleteWord(
                        SearchWord
                    )
                }
            }
        })

    }

    fun setupUI(){
        search = findViewById(R.id.search)
        clear = findViewById(R.id.search_clear)
        noResult = findViewById(R.id.no_search_result)
        searchResult = findViewById(R.id.search_result_recycler_view)
        searchWordResult = findViewById(R.id.search_word_recycler_view)
        clear.setOnClickListener {
            search.setText("")
        }
    }


}
