package campus.tech.kakao.map.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ItemLocationBinding
import campus.tech.kakao.map.model.Location

class SearchLocationAdapter(
	private val dataList: List<Location>,
	private val context: Context
) : RecyclerView.Adapter<SearchLocationAdapter.MyViewHolder>() {

	inner class MyViewHolder(private val binding: ItemLocationBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(locationData: Location) {
			binding.locationNameTextView.text = locationData.name
			binding.locationAddressTextView.text = locationData.address
			binding.locationTypeTextView.text = locationData.category
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		return MyViewHolder(
			ItemLocationBinding.inflate(
				LayoutInflater.from(context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
		holder.bind(dataList[position])
	}

	override fun getItemCount(): Int = dataList.size
}