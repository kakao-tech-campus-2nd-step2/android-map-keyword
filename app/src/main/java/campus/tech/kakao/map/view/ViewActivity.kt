package campus.tech.kakao.map.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
import campus.tech.kakao.map.view.adapter.LogAdapter

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var logAdapter: LogAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = LocationViewModel.getInstance(Repository(this))
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel = viewModel

        locationAdapter = LocationAdapter{location -> logAdapter.addLog(location)  }
        logAdapter = LogAdapter()

        binding.recyclerLocation.layoutManager = LinearLayoutManager(this)
        binding.recyclerLocation.adapter = locationAdapter

        binding.recyclerLog.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false )
        binding.recyclerLog.adapter = logAdapter
        test()


        viewModel.searchText.observe(this, Observer { searchText ->
            if (locationAdapter.locationList.isNotEmpty()) binding.tvHelpMessage.visibility = View.GONE
            locationAdapter.setLocations(viewModel.select(searchText))
        })

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
