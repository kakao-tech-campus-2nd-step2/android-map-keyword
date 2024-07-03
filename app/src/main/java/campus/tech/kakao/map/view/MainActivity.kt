package campus.tech.kakao.map.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import campus.tech.kakao.map.db.PlaceDBHelper
import campus.tech.kakao.map.R
import campus.tech.kakao.map.repository.Repository
import campus.tech.kakao.map.viewmodel.MainActivityViewModel
import campus.tech.kakao.map.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = PlaceDBHelper(this)
        val repository = Repository(dbHelper)
        val viewModel = ViewModelProvider(this, ViewModelFactory(repository))[MainActivityViewModel::class.java]
        viewModel.getPlace()
    }
}
