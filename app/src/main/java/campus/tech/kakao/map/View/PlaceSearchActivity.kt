package campus.tech.kakao.map.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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

    }
}




















