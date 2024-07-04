package campus.tech.kakao.map.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.model.Location
import campus.tech.kakao.map.model.Repository
import campus.tech.kakao.map.R
import campus.tech.kakao.map.viewmodel.LocationViewModel
import campus.tech.kakao.map.databinding.ActivityMainBinding
import campus.tech.kakao.map.view.adapter.LocationAdapter

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var locationAdapter: LocationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = LocationViewModel.getInstance(Repository(this))
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel = viewModel

        locationAdapter = LocationAdapter()
        binding.recyclerLocation.layoutManager = LinearLayoutManager(this)
        binding.recyclerLocation.adapter = locationAdapter

        test()


        viewModel.searchText.observe(this, Observer { searchText -> locationAdapter.setLocations(viewModel.select(searchText)) })

        viewModel.locations.observe(this,  Observer { locations ->
            Log.d("sqlite55","$locations")
        })
    }


    private fun test(){
        // 초기화
        viewModel.dropTable()

        // 입력
        for(i in 0..100){
            viewModel.insert(Location("cafe"+i,"부산시 금정구"+i, "카페"))
        }
    }

}
