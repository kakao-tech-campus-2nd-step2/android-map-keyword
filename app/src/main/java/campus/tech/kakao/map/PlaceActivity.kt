package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaceActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnErase: ImageButton
    private lateinit var tvNoData: TextView
    private lateinit var rvPlaceList: RecyclerView
    private lateinit var rvSearchList: RecyclerView
    private lateinit var placeAdapter: PlaceRecyclerViewAdapter
    private lateinit var searchAdapter: SearchRecyclerViewAdapter
    private val placeDatabaseAccess = PlaceDatabaseAccess(this, "Place.db")
    private val searchDatabaseAccess = PlaceDatabaseAccess(this, "Search.db")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)

        etSearch = findViewById<EditText>(R.id.etSearch)
        btnErase = findViewById<ImageButton>(R.id.btnErase)
        tvNoData = findViewById<TextView>(R.id.tvNoData)
        rvPlaceList = findViewById<RecyclerView>(R.id.rvPlaceList)
        rvSearchList = findViewById<RecyclerView>(R.id.rvSearchList)

        val placeList: MutableList<PlaceDataModel> = placeDatabaseAccess.getAllPlace()
        val searchList: MutableList<PlaceDataModel> = searchDatabaseAccess.getAllPlace()
        var keywordList: MutableList<PlaceDataModel> = mutableListOf()

        // 임의 데이터 넣기
        for (index in 1..10) {
            placeDatabaseAccess.insertPlace(PlaceDataModel("카페$index", "서울 성동구 성수동 $index", "카페"))
            placeDatabaseAccess.insertPlace(PlaceDataModel("약국$index", "서울 강남구 대치동 $index", "약국"))
        }

        // Search 어댑터
        searchAdapter = searchRecyclerViewAdapter(searchList)
        rvSearchList.adapter = searchAdapter
        rvSearchList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Place 어댑터
        placeAdapter = placeRecyclerViewAdapter(keywordList, searchList)
        rvPlaceList.adapter = placeAdapter
        rvPlaceList.layoutManager = LinearLayoutManager(this)

        controlPlaceVisibility(keywordList)
        controlSearchVisibility(searchList)

        btnErase.setOnClickListener {
            etSearch.setText("")
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                keywordList = includeKeywordList(s, placeList)
                controlPlaceVisibility(keywordList)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun includeKeywordList(s: CharSequence?, placeList: MutableList<PlaceDataModel>): MutableList<PlaceDataModel> {
        var keywordList = placeList
        val keyword = s.toString()
        keywordList = placeDatabaseAccess.searchPlace(keyword)
        placeAdapter.updateData(keywordList)
        return keywordList
    }

    private fun placeRecyclerViewAdapter(placeList: MutableList<PlaceDataModel>, searchList: MutableList<PlaceDataModel>) =
        PlaceRecyclerViewAdapter(placeList, onItemClick = { place ->
            if (place in searchList) {
                removePlace(searchList, place)
            }
            addPlace(searchList, place)
            controlSearchVisibility(searchList)
        })

    private fun searchRecyclerViewAdapter(searchList: MutableList<PlaceDataModel>) =
        SearchRecyclerViewAdapter(searchList, onItemClick = { place ->
            removePlace(searchList, place)
            controlSearchVisibility(searchList)
        })

    private fun addPlace(searchList: MutableList<PlaceDataModel>, place: PlaceDataModel) {
        searchList.add(place)
        searchDatabaseAccess.insertPlace(place)
        searchAdapter.notifyItemInserted(searchList.size - 1)
        searchAdapter.notifyDataSetChanged()
    }

    private fun removePlace(searchList: MutableList<PlaceDataModel>, place: PlaceDataModel) {
        val index = searchList.indexOf(place)
        searchList.removeAt(index)
        searchDatabaseAccess.deletePlace(place.name)
        searchAdapter.notifyItemRemoved(index)
        searchAdapter.notifyDataSetChanged()
    }


    private fun controlPlaceVisibility(placeList: List<PlaceDataModel>) {
        if (placeList.isEmpty()) {
            rvPlaceList.visibility = View.INVISIBLE
            tvNoData.visibility = View.VISIBLE
        }
        else {
            rvPlaceList.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }
    }

    private fun controlSearchVisibility(searchList: List<PlaceDataModel>) {
        if (searchList.isEmpty()) {
            rvSearchList.visibility = View.GONE
        }
        else {
            rvSearchList.visibility = View.VISIBLE
        }
    }

}