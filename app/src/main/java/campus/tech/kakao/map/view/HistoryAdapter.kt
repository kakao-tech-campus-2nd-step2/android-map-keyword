package campus.tech.kakao.map.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ItemHistoryBinding

class HistoryAdapter(
	private val dataList: List<String>,
	private val context: Context
) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

	inner class MyViewHolder(private val binding: ItemHistoryBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun binding(historyData: String) {
			binding.locationHistoryNameTextView.text = historyData
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		return MyViewHolder(
			ItemHistoryBinding.inflate(
				LayoutInflater.from(context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
		holder.binding(dataList[position])
	}

	override fun getItemCount(): Int = dataList.size
}