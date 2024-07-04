package campus.tech.kakao.map.view

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import campus.tech.kakao.map.R
import campus.tech.kakao.map.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var editText: EditText
    private lateinit var cancelBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        editText = findViewById<EditText>(R.id.searchText)
        cancelBtn = findViewById<ImageView>(R.id.cancelBtn)

        setSearchTextObserver()
        setCancelBtnClickListener()

        viewModel.insertPlaceDummyData(this, "카페", "대전 유성구 궁동", "카페")
        viewModel.insertPlaceDummyData(this, "약국", "대전 유성구 봉명동", "약국")
        viewModel.getAllPlaces(this)
    }

    private fun setSearchTextObserver() {
        viewModel.searchText.observe(this) {
            editText.setText(it)
        }
    }

    private fun setCancelBtnClickListener() {
        cancelBtn.setOnClickListener {
            viewModel.clearSearchText()
        }
    }
}