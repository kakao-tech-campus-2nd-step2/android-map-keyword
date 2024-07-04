package campus.tech.kakao.map.View

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.Model.Datasource.Local.SqliteDB
import campus.tech.kakao.map.Model.Place
import campus.tech.kakao.map.R
import campus.tech.kakao.map.Repository.PlaceRepository
import campus.tech.kakao.map.Util.PlaceContract
import campus.tech.kakao.map.Util.ViewModelFactory
import campus.tech.kakao.map.View.Adapter.FavoriteAdapter
import campus.tech.kakao.map.View.Adapter.SearchResultAdapter
import campus.tech.kakao.map.View.Observer.EmptyPlaceObserver
import campus.tech.kakao.map.ViewModel.SearchViewModel

class PlaceSearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchResult: RecyclerView
    private lateinit var noItem: TextView
    private lateinit var etSearchPlace: EditText
    private lateinit var deleteSearch: ImageView
    private lateinit var favorite: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search)

        val sqliteDB = SqliteDB(this, PlaceContract.DATABASE_NAME, null, 1)
        val repository = PlaceRepository(sqliteDB)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[SearchViewModel::class.java]

        searchResult = findViewById<RecyclerView>(R.id.searchResult)
        etSearchPlace = findViewById<EditText>(R.id.etSearchPlace)
        noItem = findViewById<TextView>(R.id.noItem)
        deleteSearch = findViewById<ImageView>(R.id.deleteSearch)
        favorite = findViewById<RecyclerView>(R.id.favorite)

        settingSearchRecyclerView()
        settingFavoriteRecyclerView()
        setDeleteSearchListener()
        setEditTextListener()
    }

    private fun settingSearchRecyclerView() {
        setSearchAdapter()
        searchResult.layoutManager = LinearLayoutManager(this)
        searchResult.addItemDecoration(
            DividerItemDecoration(this, VERTICAL)
        )
    }

    private fun settingFavoriteRecyclerView() {
        setFavoriteAdapter()
        favorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
    }

    private fun setFavoriteAdapter() {
        val adapter = FavoriteAdapter(
            viewModel.favoritePlace.value?: listOf<Place>(),
            LayoutInflater.from(this),
            onClickDelete = {
                viewModel.deleteFromFavorite(it)
            })

        favorite.adapter = adapter
        viewModel.favoritePlace.observe(this) {
            adapter.updateData(it)
        }
    }


    private fun setDeleteSearchListener() {
        deleteSearch.setOnClickListener {
            etSearchPlace.setText("")
        }
    }

    private fun setEditTextListener() {
        etSearchPlace.addTextChangedListener {
            viewModel.searchPlace(etSearchPlace.text.toString())
        }
    }

    private fun setSearchAdapter() {
        val adapter = SearchResultAdapter(viewModel.currentResult.value ?: listOf<Place>(),
            LayoutInflater.from(this),
            onClickAdd = {
                viewModel.addFavorite(it)
                favorite.scrollToPosition((viewModel.favoritePlace.value!!.size - 1))
            })
        viewModel.currentResult.observe(this) {
            adapter.updateData(it)
        }
        adapter.registerAdapterDataObserver(EmptyPlaceObserver(searchResult, noItem))
        searchResult.adapter = adapter
    }

}




















