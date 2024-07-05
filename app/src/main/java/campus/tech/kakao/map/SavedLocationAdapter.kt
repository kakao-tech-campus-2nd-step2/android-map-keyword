package campus.tech.kakao.map

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.SavedLocationAdapter.SavedLocationHolder

import kotlin.Unit;

class SavedLocationAdapter(
        private val onXButtonSelected: (SavedLocation) -> Unit
) : ListAdapter<SavedLocation, SavedLocationHolder>(
    object: DiffUtil.ItemCallback<SavedLocation>() {
        override fun areItemsTheSame(oldItem: SavedLocation, newItem: SavedLocation): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: SavedLocation, newItem: SavedLocation): Boolean {
            return oldItem == newItem
        }
    }) {
    inner class SavedLocationHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val savedLocationXButton: ImageView = itemView.findViewById(R.id.savedLocationXButton)
        val savedLocationTextView: TextView = itemView.findViewById(R.id.savedLocationTextView)

        init {
            savedLocationXButton.setOnClickListener {
                val savedLocation = getItem(bindingAdapterPosition)
                onXButtonSelected(savedLocation)
            }
        }
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): SavedLocationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_location, parent, false)
        return SavedLocationHolder(view)
    }

    override fun onBindViewHolder(holder:SavedLocationHolder, position: Int) {
        val savedLocation = getItem(position)
        holder.savedLocationTextView.setText(savedLocation.title)
    }
}