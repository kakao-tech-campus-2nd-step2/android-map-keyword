package campus.tech.kakao.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapList = findViewById<RecyclerView>(R.id.mapList)
        val mapItemList = mutableListOf<MapItem>()
        for (i in 0..20) {
            mapItemList.add(MapItem("카페" + i, "서울 성동구 성수동" + i, "카페"))
        }

        val selectList = findViewById<RecyclerView>(R.id.selectList)
        val selectItemList = mutableListOf<SelectItem>()
        for (i in 0..20) {
            selectItemList.add(SelectItem("카페" + i))
        }

        mapList.adapter = MapListAdapter(mapItemList, LayoutInflater.from(this))
        mapList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        selectList.adapter = SelectListAdapter(selectItemList, LayoutInflater.from(this))
        selectList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}


