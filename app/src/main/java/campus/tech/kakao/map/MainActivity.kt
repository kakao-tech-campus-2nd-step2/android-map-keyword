package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
        val selectItemViewModel = SelectItemViewModel(this)

        val mapList = findViewById<RecyclerView>(R.id.mapList)
        val selectList = findViewById<RecyclerView>(R.id.selectList)
        val inputSpace = findViewById<EditText>(R.id.inputSpace)

        //val mapDB = MapItemDbHelper(context = this)
        //mapDB.onUpgrade(mapDB.writableDatabase, 1, 2)

        //mapItemViewModel.updateMapItemList()
        //selectItemViewModel.updateSelectItemList()

//        val mapItemList = mutableListOf<MapItem>()
//        for (i in 1..20) {
//            mapItemList.add(MapItem("약국" + i, "wnth", "약국"))
//        }
//
//        val selectItemList = mutableListOf<SelectItem>()
//        for (i in 1..20) {
//            selectItemList.add(SelectItem("약국" + i))
//        }

        val mapListAdapter = MapListAdapter(mapItemViewModel.getMapItemList(), LayoutInflater.from(this))
        val selectListAdapter = SelectListAdapter(selectItemViewModel.getSelectItemList(), LayoutInflater.from(this))

        mapList.adapter = mapListAdapter
        mapList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        selectList.adapter = selectListAdapter
        selectList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        inputSpace.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val mapItemList = mapItemViewModel.searchMapItem(s.toString())
                mapListAdapter.updateMapItemList(mapItemList)
                // 여기서 if문으로 사이즈 체크해서 중간 텍스트뷰도 gone처리해주기
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}


