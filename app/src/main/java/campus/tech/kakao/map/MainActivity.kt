package campus.tech.kakao.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ActivityMainBinding
import campus.tech.kakao.map.databinding.ItemPlaceBinding
import campus.tech.kakao.map.model.Place
import campus.tech.kakao.map.viewmodel.PlaceViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var placeViewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        placeViewModel = ViewModelProvider(this)[PlaceViewModel::class.java]
        binding.viewModel = placeViewModel
        binding.lifecycleOwner = this

        setSearchResultRecyclerView()
        placeViewModel.clearAllPlaces()
        testDataInsert()

        setClearImageViewClickListener()
        setSearchEditText()
        observeSearchResults()
    }

    /**
     * 테스트용 더미 데이터(카페, 약국)를 삽입하는 함수
     */
    private fun testDataInsert() {
        insertPlaces("서울 성동구 성수동", "카페")
        insertPlaces("서울 강남구 대치동", "약국")
    }

    /**
     * place 데이터를 db에 삽입하는 함수.
     *
     * @param address 저장할 주소값
     * @param category 저장할 카테고리값
     */
    private fun insertPlaces(
        address: String,
        category: String,
    ) {
        repeat(20) { idx ->
            placeViewModel.insertPlace(
                Place(
                    category + (idx + 1),
                    "$address ${idx + 1}",
                    category,
                ),
            )
        }
    }

    /**
     * 검색 결과를 표시하는 RecyclerView를 설정하는 함수.
     */
    private fun setSearchResultRecyclerView() {
        binding.searchResultRecyclerView.adapter = ResultRecyclerViewAdapter()
        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    class ResultRecyclerViewAdapter : RecyclerView.Adapter<ResultRecyclerViewAdapter.PlaceViewHolder>() {
        private var searchResultList: List<Place> = emptyList()

        /**
         * RecyclerView에 표시할 장소 리스트를 설정하는 함수.
         *
         * @param places 장소 리스트
         */
        @SuppressLint("NotifyDataSetChanged")
        fun setPlaces(places: List<Place>) {
            searchResultList = places
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): PlaceViewHolder {
            val binding =
                ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PlaceViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return searchResultList.size
        }

        override fun onBindViewHolder(
            holder: PlaceViewHolder,
            position: Int,
        ) {
            holder.bind(searchResultList[position])
        }

        class PlaceViewHolder(private val binding: ItemPlaceBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(place: Place) {
                binding.place = place
            }
        }
    }

    /**
     * 검색 EditText가 변경되면 placeViewModel을 통해 검색을 수행하도록 하는 함수.
     */
    private fun setSearchEditText() {
        binding.searchEditText.addTextChangedListener { editable ->
            val searchText = editable.toString().trim()
            if (searchText.isNotEmpty()) {
                placeViewModel.searchPlaces(searchText)
            } else {
                placeViewModel.searchPlaces("")
            }
        }
    }

    /**
     * 검색 결과를 관찰하고, RecyclerView에 결과를 반영하는 함수.
     */
    private fun observeSearchResults() {
        placeViewModel.searchResults.observe(
            this,
        ) { places ->
            if (places.isNotEmpty()) {
                (binding.searchResultRecyclerView.adapter as ResultRecyclerViewAdapter).setPlaces(
                    places
                )
            }
        }
    }

    /**
     * clearImageView의 클릭 리스너를 설정하는 함수.
     *
     * searchEditText의 text를 null로 변경.
     */
    private fun setClearImageViewClickListener() {
        binding.clearImageView.setOnClickListener {
            binding.searchEditText.text = null
        }
    }
}
