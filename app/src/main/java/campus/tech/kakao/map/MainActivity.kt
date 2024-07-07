package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search = findViewById<EditText>(R.id.search)
        val closeIcon = findViewById<ImageView>(R.id.close_icon)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val dataGeneration = findViewById<TextView>(R.id.data_generation)
//        val searchBotton = findViewById<TextView>(R.id.searchBotton)
        val delete = findViewById<TextView>(R.id.data_delete)
        val savedSearch = findViewById<RecyclerView>(R.id.saved_search)

        val dbManager = DatabaseManager(context = this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        savedSearch.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)    //마지막 인자는 역순으로 배치 여부

        closeIcon.setOnClickListener {
            search.text.clear()
        }

        dataGeneration.setOnClickListener{
            with(dbManager){    //데이터 베이스 확인하기 편하게 for문 3번
                for(i in 1..10){
                    insertPlace("카페$i","카페 주소$i","카페")
                }
                for(i in 1..10){
                    insertPlace("약국$i","약국 주소$i","약국")
                }
                for(i in 1..10){
                    insertPlace("마트$i","마트 주소$i","마트")
                }
            }
            Toast.makeText(this, "데이터 생성 완료", Toast.LENGTH_SHORT).show()

        }

//        searchBotton.setOnClickListener {   //삭제 예정
//            val query = search.text.toString()
//            if (query.isEmpty()) {
//                Toast.makeText(this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            val dummy = dbManager.searchPlacesKind(query)
//            recyclerView.adapter = PlaceAdapter(dummy){ place ->
//                dbManager.insertSavedPlace(place.name)  // 클릭 시 db에 이름 추가
//                updateSavedSearch(dbManager, savedSearch)
//            }
//            Log.d("testt", dummy.toString())
//        }

        //검색창에 텍스트가 바뀔 때마다 감지해서 검색
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 텍스트 변경 후 호출
                val query = s.toString()
                val dummy = dbManager.searchPlacesKind(query) //리턴값이 List<Place>
                recyclerView.adapter = PlaceAdapter(dummy){ place ->
                    dbManager.insertSavedPlace(place.id,place.name)
                    updateSavedSearch(dbManager, savedSearch)
                }
                Log.d("testt", "텍스트 변경")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트 변경 전 호출
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때마다 호출
            }
        })

        //테이블 삭제
        delete.setOnClickListener{
            dbManager.dropTable()
            dbManager.createTable()
        }

        updateSavedSearch(dbManager, savedSearch)


    }

    //저장된 검색어 업데이트
    private fun updateSavedSearch(dbManager: DatabaseManager, recyclerView: RecyclerView) {
        val savedSearch = dbManager.getSavedSearches()
        recyclerView.adapter = SavedSearchAdapter(savedSearch){SavedSearch ->
                dbManager.deleteSavedPlace(SavedSearch.id)   //클릭된 아이템의 이름으로 SavedSearch에서 제거
                updateSavedSearch(dbManager, recyclerView)  //리스트 업데이트
        }
    }
}

