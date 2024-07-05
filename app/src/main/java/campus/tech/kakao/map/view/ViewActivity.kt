package campus.tech.kakao.map.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.databinding.ActivityMainBinding
import campus.tech.kakao.map.model.Location
import campus.tech.kakao.map.model.Repository
import campus.tech.kakao.map.view.adapter.LocationAdapter
import campus.tech.kakao.map.view.adapter.LogAdapter
import campus.tech.kakao.map.viewmodel.LocationViewModel

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var logAdapter: LogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onStop() {
        super.onStop()
        clearAndSaveLog()
    }

    private fun clearAndSaveLog(){
        viewModel.dropLogTable()
        viewModel.saveLog(logAdapter.logList)
    }

    private fun init(){
        initBinding()
        initViewModel()
        setupRecyclerViews()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    private fun initViewModel() {
        viewModel = LocationViewModel.getInstance(Repository(this))
        binding.viewModel = viewModel
    }

    private fun setupRecyclerViews() {
        initLocationAdapter()
        initLogAdapter()
    }

    private fun initLocationAdapter() {
        locationAdapter = LocationAdapter { location -> logAdapter.addLog(location) }
        binding.recyclerLocation.apply {
            layoutManager = LinearLayoutManager(this@ViewActivity)
            adapter = locationAdapter
        }
    }

    private fun initLogAdapter() {
        logAdapter = LogAdapter(viewModel.getLog())
        binding.recyclerLog.apply {
            layoutManager = LinearLayoutManager(this@ViewActivity, RecyclerView.HORIZONTAL, false)
            adapter = logAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.searchText.observe(this, Observer { searchText ->
            if (locationAdapter.locationList.isNotEmpty()) binding.tvHelpMessage.visibility = View.GONE
            locationAdapter.setLocations(viewModel.select(searchText))
        })
    }

}
