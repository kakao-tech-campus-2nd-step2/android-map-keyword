package campus.tech.kakao.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import campus.tech.kakao.map.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider //viewmodel 초기화
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //private 필드 변수화
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var viewModel: MapViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 연결 유지
        sqLiteHelper = SQLiteHelper(this)
        sqLiteHelper.writableDatabase

        //ViewModel 초기화
        val viewModelInit = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelInit).get(MapViewModel::class.java)

        setupRecyclerViews()
        setupSearchEditText()
        setupClearTextButton()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        //검색 결과 데이터 목록 RecyclerView 설정
        binding.searchResultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = searchAdapter
        }
        //선택된 항목 어댑터 초기화
        selectedAdapter = SelectedAdapter { item -> viewModel.removeSelectedItem(item) }
        //선택 데이터 RecyclerView 가로
        binding.selectedItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            adapter = selectedAdapter
        }
    }

    //검색 시 Edit
    private fun setupSearchEditText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //차있는 경우
                if (s.toString().isNotEmpty()) {
                    binding.clearTextButton.visibility = View.VISIBLE
                } else { //비어있는 경우
                    binding.clearTextButton.visibility = View.GONE
                }
                viewModel.searchQuery.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    //text 지우는 버튼
    private fun setupClearTextButton() {
        binding.clearTextButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }
    }
}