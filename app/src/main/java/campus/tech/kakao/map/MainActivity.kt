package campus.tech.kakao.map

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbAccessor = LocationDbAccessor(context=this)
//        addLocationData(dbAccessor)
        val xButton: ImageView = findViewById(R.id.xButton)
        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val searchResultRecyclerView: RecyclerView = findViewById(R.id.searchResultRecyclerView)
        xButton.setOnClickListener{
            searchEditText.setText("")
        }

        val locationList: MutableList<Location> = readLocationData(dbAccessor)
        val adapter: LocationAdapter = LocationAdapter()
        searchResultRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        searchResultRecyclerView.adapter = adapter

        adapter.submitList(locationList)

    }

    private fun readLocationData(dbAccessor: LocationDbAccessor): MutableList<Location> {
        val result: MutableList<Location> = dbAccessor.getLocationAll()
        Log.d("jieun", "$result")
        return result
    }

    private fun addLocationData(dbAccessor: LocationDbAccessor) {
        for (i in 1..9) {
            dbAccessor.insertLocation("카페$i", "부산 부산진구 전포대로$i", "카페")
        }
        for (i in 1..9) {
            dbAccessor.insertLocation("음식점$i", "부산 부산진구 중앙대로$i", "음식점")
        }
    }

}
