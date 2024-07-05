package campus.tech.kakao.map.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SearchViewModel::class.java]
        editText = findViewById<EditText>(R.id.searchText)
        cancelBtn = findViewById<ImageView>(R.id.cancelBtn)

        setCancelBtnClickListener()
        editTextWatcher()
        viewModel.insertDummyData("카페", "대전 유성구 궁동", "카페")
        viewModel.insertDummyData("약국", "대전 유성구 봉명동", "약국")
        viewModel.getPlaces()
    }


    private fun editTextWatcher() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setCancelBtnClickListener() {
        cancelBtn.setOnClickListener {
            editText.setText("")
        }
    }
}