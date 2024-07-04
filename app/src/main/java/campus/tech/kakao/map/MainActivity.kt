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
import campus.tech.kakao.map.databinding.ItemSavedSearchWordBinding
import campus.tech.kakao.map.model.Place
import campus.tech.kakao.map.model.SavedSearchWord
import campus.tech.kakao.map.viewmodel.PlaceViewModel
import campus.tech.kakao.map.viewmodel.SavedSearchWordViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var savedSearchWordViewModel: SavedSearchWordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        placeViewModel = ViewModelProvider(this)[PlaceViewModel::class.java]
        savedSearchWordViewModel = ViewModelProvider(this)[SavedSearchWordViewModel::class.java]
        binding.placeViewModel = placeViewModel
        binding.savedSearchWordViewModel = savedSearchWordViewModel
        binding.lifecycleOwner = this

        setRecyclerViews()
        setSearchResultRecyclerView()
        placeViewModel.clearAllPlaces()
        testDataInsert()

        setClearImageViewClickListener()
        setSearchEditText()

        observeViewModels()
        observeSearchResults()
        observeSavedSearchWords()
    }

    /**
     * 테스트용 더미 데이터(카페, 약국)를 삽입하는 함수
     */
    private fun testDataInsert() {
        insertPlaces("서울 성동구 성수동", "카페")
        insertPlaces("서울 강남구 대치동", "약국")
        insertPlaces("서울 강남구 수서동", "약국")
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
                    name = category + (idx + 1),
                    address = "$address ${idx + 1}",
                    category = category,
                ),
            )
        }
    }

    /**
     * RecyclerView들을 설정하는 함수.
     */
    private fun setRecyclerViews() {
        setSearchResultRecyclerView()
        setSavedSearchWordRecyclerView()
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
     * clearImageView의 클릭 리스너를 설정하는 함수.
     *
     * searchEditText의 text를 null로 변경.
     */
    private fun setClearImageViewClickListener() {
        binding.searchClearImageView.setOnClickListener {
            binding.searchEditText.text = null
        }
    }

    /**
     * viewModel을 관찰하도록 하는 함수.
     */
    private fun observeViewModels() {
        observeSearchResults()
        observeSavedSearchWords()
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
                    places,
                )
            }
        }
    }

    /**
     * 저장된 검색어를 관찰하고, RecyclerView에 결과를 반영하는 함수.
     */
    private fun observeSavedSearchWords() {
        savedSearchWordViewModel.savedSearchWords.observe(
            this,
        ) { savedSearchWords ->
            if (savedSearchWords.isNotEmpty()) {
                (binding.savedSearchWordRecyclerView.adapter as SavedSearchWordRecyclerViewAdapter).setSavedSearchWords(
                    savedSearchWords,
                )
            }
        }
    }

    interface OnPlaceItemClickListener {
        fun onPlaceItemClicked(place: Place)
    }

    /**
     * 검색 결과를 표시하는 RecyclerView를 설정하는 함수.
     *
     * - `placeItemClickListener` : placeItem을 누르면 검색어가 저장되도록 하는 클릭 리스너 interface 구현 객체
     */
    private fun setSearchResultRecyclerView() {
        val placeItemClickListener =
            object : OnPlaceItemClickListener {
                override fun onPlaceItemClicked(place: Place) {
                    savedSearchWordViewModel.insertSearchWord(
                        SavedSearchWord(
                            name = place.name,
                            placeId = place.id,
                        ),
                    )
                }
            }
        binding.searchResultRecyclerView.adapter = ResultRecyclerViewAdapter(placeItemClickListener)
        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    class ResultRecyclerViewAdapter(private val clickListener: OnPlaceItemClickListener) :
        RecyclerView.Adapter<ResultRecyclerViewAdapter.PlaceViewHolder>() {
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
            val place = searchResultList[position]
            holder.bind(place)
            holder.itemView.setOnClickListener {
                clickListener.onPlaceItemClicked(place)
            }
        }

        class PlaceViewHolder(private val binding: ItemPlaceBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(place: Place) {
                binding.place = place
            }
        }
    }

    interface OnSavedSearchWordClearImageViewClickListener {
        fun onSavedSearchWordClearImageViewClicked(savedSearchWord: SavedSearchWord)
    }

    /**
     * SavedSearchWordRecyclerView를 설정하는 함수.
     *
     * - `savedSearchWordClearImageViewClickListener` : clear 버튼을 누르면 해당 저장된 검색어가 사라지도록 하는 클릭리스너 interface 구현 객체
     */
    private fun setSavedSearchWordRecyclerView() {
        val savedSearchWordClearImageViewClickListener =
            object : OnSavedSearchWordClearImageViewClickListener {
                override fun onSavedSearchWordClearImageViewClicked(savedSearchWord: SavedSearchWord) {
                    savedSearchWordViewModel.deleteSearchWord(savedSearchWord)
                }
            }
        binding.savedSearchWordRecyclerView.adapter =
            SavedSearchWordRecyclerViewAdapter(savedSearchWordClearImageViewClickListener)
        binding.savedSearchWordRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    class SavedSearchWordRecyclerViewAdapter(private val clickListener: OnSavedSearchWordClearImageViewClickListener) :
        RecyclerView.Adapter<SavedSearchWordRecyclerViewAdapter.SavedSearchWordViewHolder>() {
        private var savedSearchWordList: List<SavedSearchWord> = emptyList()

        /**
         * RecyclerView에 표시할 저장된 검색어 리스트를 설정하는 함수.
         *
         * @param savedSearchWords 저장된 검색어 리스트
         */
        @SuppressLint("NotifyDataSetChanged")
        fun setSavedSearchWords(savedSearchWords: List<SavedSearchWord>) {
            savedSearchWordList = savedSearchWords
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): SavedSearchWordViewHolder {
            val binding =
                ItemSavedSearchWordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            return SavedSearchWordViewHolder(binding, clickListener)
        }

        override fun getItemCount(): Int {
            return savedSearchWordList.size
        }

        override fun onBindViewHolder(
            holder: SavedSearchWordViewHolder,
            position: Int,
        ) {
            val savedSearchWord = savedSearchWordList[position]
            holder.bind(savedSearchWord)
        }

        class SavedSearchWordViewHolder(
            private val binding: ItemSavedSearchWordBinding,
            private val clickListener: OnSavedSearchWordClearImageViewClickListener,
        ) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(savedSearchWord: SavedSearchWord) {
                binding.savedSearchWord = savedSearchWord

                binding.savedSearchWordClearImageView.setOnClickListener {
                    clickListener.onSavedSearchWordClearImageViewClicked(savedSearchWord)
                }
            }
        }
    }
}
