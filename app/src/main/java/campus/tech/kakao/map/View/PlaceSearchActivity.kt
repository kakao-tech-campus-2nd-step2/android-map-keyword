package campus.tech.kakao.map.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.Model.Datasource.Local.DbHelper
import campus.tech.kakao.map.R
import campus.tech.kakao.map.Repository.PlaceRepository
import campus.tech.kakao.map.Util.PlaceContract
import campus.tech.kakao.map.Util.ViewModelFactory
import campus.tech.kakao.map.ViewModel.SearchViewModel

class PlaceSearchActivity : AppCompatActivity() {
    private lateinit var viewModel : SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search)

        val dbHelper = DbHelper(this,PlaceContract.DATABASE_NAME,null,1)
        dbHelper.onCreate(dbHelper.writableDatabase)
        val repository = PlaceRepository(dbHelper)
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[SearchViewModel::class.java]

        val searchResult = findViewById<RecyclerView>(R.id.searchResult)
        val res = viewModel.getAllPlace()

        searchResult.adapter = SearchResultAdapter(res, LayoutInflater.from(this))
        searchResult.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, VERTICAL)
        searchResult.addItemDecoration(decoration)
    }
}




















