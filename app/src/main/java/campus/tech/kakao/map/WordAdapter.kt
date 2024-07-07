package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(
	val wordList: List<SearchWord>,
	val onItemClicked: (SearchWord) -> Unit
): RecyclerView.Adapter<WordAdapter.ViewHolder>() {
	inner class ViewHolder(
		itemView: View
	): RecyclerView.ViewHolder(itemView) {
		val searchWord: TextView = itemView.findViewById(R.id.search_word)
		val delete: ImageView = itemView.findViewById(R.id.x)
		init {
			delete.setOnClickListener {
				val position = bindingAdapterPosition
				val word:SearchWord = wordList[position]
				onItemClicked(word)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false))
	}

	override fun getItemCount(): Int {
		return wordList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.searchWord.text = wordList[position].name
	}
}