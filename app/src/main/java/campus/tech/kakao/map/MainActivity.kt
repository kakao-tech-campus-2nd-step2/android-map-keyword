package campus.tech.kakao.map

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapItemViewModel = MapItemViewModel(this)

        val mapList = findViewById<RecyclerView>(R.id.mapList)
        val selectList = findViewById<RecyclerView>(R.id.selectList)
        val inputSpace = findViewById<EditText>(R.id.inputSpace)

        //val mapDB = MapItemDbHelper(context = this)
        //mapDB.onUpgrade(mapDB.writableDatabase, 1, 2)
        //val mapItemList = mapDB.makeMapList()

        val selectItemList = mutableListOf<SelectItem>()
        for (i in 1..20) {
            selectItemList.add(SelectItem("약국" + i))
        }

        val mapListAdapter = MapListAdapter(mapItemViewModel.getMapItemList(), LayoutInflater.from(this))

        mapList.adapter = mapListAdapter
        mapList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        selectList.adapter = SelectListAdapter(selectItemList, LayoutInflater.from(this))
        selectList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}


