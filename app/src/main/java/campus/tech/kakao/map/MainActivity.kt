package campus.tech.kakao.map

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var input: EditText
    private lateinit var researchCloseButton: ImageView
    private lateinit var tabRecyclerView: RecyclerView
    private lateinit var noResultTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeRepository: PlaceRepository
    private var placeList = mutableListOf<Place>()
    private lateinit var resultAdapter: RecyclerViewAdapter
    private lateinit var tapAdapter: TapViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)
        researchCloseButton = findViewById(R.id.close_button)
        tabRecyclerView = findViewById(R.id.tab_recyclerview)
        noResultTextView = findViewById(R.id.no_result_textview)
        recyclerView = findViewById(R.id.recyclerView)
        tabRecyclerView = findViewById(R.id.tab_recyclerview)

        placeRepository = PlaceRepository(this)
        placeRepository.reset()
        placeRepository.insertInitialData() // 검색결과를 보여주기 위한 전체 Place table에 데이터삽입
        placeList = placeRepository.returnPlaceList()
        Log.d("mylog", placeList.size.toString())

        resultAdapter = RecyclerViewAdapter(placeList, LayoutInflater.from(this), placeRepository)
        recyclerView.adapter = resultAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //val dbHelper = PlaceDbHelper(this)
        //val db = dbHelper.writableDatabase
        //createMyResearchTable(db)
        // tabAdapter = TabViewAdapter()
        // tabRecyclerView.adapter = tabAdapter
        // tabRecyclerView.layoutManger = LinearLayoutManger(this, LinearLayoutManager.HORIZONTAL)

        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())
            }
        })

        researchCloseButton.setOnClickListener {
            input.setText("")
        }
    }

    private fun filterList(query: String) {
        val filteredList = placeList.filter {
            it.category == query
        }

        if (filteredList.isEmpty()) {
            noResultTextView.isVisible = true
            recyclerView.isGone = true
        } else {
            noResultTextView.isGone = true
            recyclerView.isVisible = true
            resultAdapter.placeList = filteredList.toMutableList()
            resultAdapter.notifyDataSetChanged()
        }
    }

    private fun createMyResearchTable(db: SQLiteDatabase) {
        val SQL_CREATE_MYRESEARCH_TABLE =
            "CREATE TABLE ${MyPlaceContract.Research.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${MyPlaceContract.Research.COLUMN_IMG} INTEGER," +
                    "${MyPlaceContract.Research.COLUMN_NAME} TEXT," +
                    "${MyPlaceContract.Research.COLUMN_CATEGORY} TEXT," +
                    "${MyPlaceContract.Research.COLUMN_LOCATION} TEXT)"

        db.execSQL(SQL_CREATE_MYRESEARCH_TABLE)
    }

}