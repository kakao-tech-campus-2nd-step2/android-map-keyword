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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import campus.tech.kakao.map.R
import campus.tech.kakao.map.db.PlaceDBHelper
import campus.tech.kakao.map.model.Place
import campus.tech.kakao.map.repository.Repository
import campus.tech.kakao.map.viewmodel.MainActivityViewModel
import campus.tech.kakao.map.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var noResultText : TextView
    lateinit var inputSearchField: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        noResultText = findViewById<TextView>(R.id.no_search_result)
        val recyclerView = findViewById<RecyclerView>(R.id.search_result_recyclerView)
        inputSearchField = findViewById<EditText>(R.id.input_search_field)
        val dbHelper = PlaceDBHelper(this)
        val repository = Repository(dbHelper)
        val viewModel = ViewModelProvider(this, ViewModelFactory(repository))[MainActivityViewModel::class.java]
        val searchDeleteButton = findViewById<ImageView>(R.id.button_X)
        searchDeleteButton.setOnClickListener{
            inputSearchField.setText("")
            inputSearchField.clearFocus()
        }

        Log.d("testt", viewModel.place.toString())
        viewModel.place.observe(this, Observer { newData ->
            Log.d("readData", "데이터 변경 감지")
            val place = viewModel.place
            Log.d("testt", "place")
            place?.let{
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = RecyclerViewAdapter(place,LayoutInflater.from(this), this)
                noResultText.visibility = View.INVISIBLE
            }
            if(place.value?.isEmpty() == true) noResultText.visibility = View.VISIBLE
        })

        inputSearchField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val text = inputSearchField.text.toString()
                viewModel.getPlaceWithCategory(text)
            }
        })


    }
}

class RecyclerViewAdapter(
    var placeList: LiveData<MutableList<Place>>,
    var inflater: LayoutInflater,
    var context: Context
): RecyclerView.Adapter<RecyclerViewAdapter.PlaceViewHolder>(){

    inner class PlaceViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.place_name)
        val location = itemView.findViewById<TextView>(R.id.place_location)
        val category = itemView.findViewById<TextView>(R.id.place_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = inflater.inflate(R.layout.place_item, parent, false)
        Log.d("testt", "onCreateViewHolder")
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