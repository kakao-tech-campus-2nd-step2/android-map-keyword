package campus.tech.kakao.map

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    //private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //dbHelper = DbHelper(this)

        //DB가 비어있을 때만 기본 데이터 추가
        /*
        if (dbHelper.isDBEmpty(dbHelper)) {
            insertInitialData(dbHelper)
        } */
        viewModel.insertInitialData()

        viewModel.searchResults.observe(this, Observer { results ->
            binding.searchRecyclerView.adapter = SearchAdapter(results)
            binding.searchRecyclerView.visibility = if (results.isEmpty()) View.GONE else View.VISIBLE
        })


        binding.buttonX.setOnClickListener {
            binding.inputSearch.text.clear()
        }

        //RecyclerView 설정
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(this)
    }

}
