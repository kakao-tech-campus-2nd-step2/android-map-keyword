package campus.tech.kakao.map.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import campus.tech.kakao.map.R
import campus.tech.kakao.map.db.PlaceDBHelper
import campus.tech.kakao.map.model.Place
import campus.tech.kakao.map.model.SavedPlace
import campus.tech.kakao.map.repository.PlaceRepository
import campus.tech.kakao.map.repository.SavedPlaceRepository
import campus.tech.kakao.map.viewmodel.MainActivityViewModel
import campus.tech.kakao.map.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity(), OnClickPlaceListener {
    lateinit var noResultText: TextView
    lateinit var inputSearchField: EditText
    lateinit var viewModel : MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        noResultText = findViewById<TextView>(R.id.no_search_result)
        val recyclerView = findViewById<RecyclerView>(R.id.search_result_recyclerView)
        inputSearchField = findViewById<EditText>(R.id.input_search_field)
        val dbHelper = PlaceDBHelper(this)
        val placeRepository = PlaceRepository(dbHelper)
        val savedPlaceRepository = SavedPlaceRepository(dbHelper)
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(placeRepository, savedPlaceRepository)
            )[MainActivityViewModel::class.java]
        val searchDeleteButton = findViewById<ImageView>(R.id.button_X)
        searchDeleteButton.setOnClickListener {
            inputSearchField.setText("")
            inputSearchField.clearFocus()
        }

        Log.d("testt", viewModel.place.toString())
        viewModel.place.observe(this, Observer { newData ->
            Log.d("readData", "데이터 변경 감지")
            val place = viewModel.place
            Log.d("testt", "place")
            place?.let {
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = PlaceViewAdapter(place, LayoutInflater.from(this), this)
                noResultText.visibility = View.INVISIBLE
            }
            if (place.value?.isEmpty() == true) noResultText.visibility = View.VISIBLE
        })

        inputSearchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(searchText: Editable?) {
                val text = searchText.toString()
                viewModel.getPlaceWithCategory(text)
            }
        })


    }

    override fun savePlace(place: Place) {
        Log.d("testt", "콜백함수 처리")
        viewModel.savePlace(place)
    }
}

interface OnClickPlaceListener {
    fun savePlace(place: Place)
}

class PlaceViewAdapter(
    val placeList: LiveData<MutableList<Place>>,
    val inflater: LayoutInflater,
    val listener: OnClickPlaceListener
) : RecyclerView.Adapter<PlaceViewAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.place_name)
        val location = itemView.findViewById<TextView>(R.id.place_location)
        val category = itemView.findViewById<TextView>(R.id.place_category)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                Log.d("testt", "콜백함수 호출")
                placeList.value?.get(position)?.let { listener.savePlace(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = inflater.inflate(R.layout.place_item, parent, false)
        Log.d("testt", "onCreateViewHolder")
        // onCreateViewHolder에서 리스너?

        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.name.text = placeList.value?.get(position)?.name ?: ""
        holder.location.text = placeList.value?.get(position)?.location ?: ""
        holder.category.text = placeList.value?.get(position)?.category ?: ""
    }

    override fun getItemCount(): Int {
        return placeList.value?.size ?: 0
    }
}

