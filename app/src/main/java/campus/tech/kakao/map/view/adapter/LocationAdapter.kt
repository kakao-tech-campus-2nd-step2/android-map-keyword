package campus.tech.kakao.map.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.DiffUtilCallback
import campus.tech.kakao.map.ListUtil
import campus.tech.kakao.map.databinding.ListItemBinding
import campus.tech.kakao.map.model.Location


class LocationAdapter(
    private var onItemClicked: (Location) -> Unit
): RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    var locationList : List<Location> = listOf()
    inner class LocationViewHolder(private val binding: ListItemBinding )
        :RecyclerView.ViewHolder(binding.root){
            fun bind(location: Location){
                binding.location = location
                binding.root.setOnClickListener {
                    onItemClicked(location)
                }
            }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int = locationList.size

    fun updateAdapterList(locations: List<Location>){

        notifyListUpdate(locations)
        locationList = locations
    }

    fun notifyListUpdate(newList: List<Location>){
        ListUtil.notifyListchanged(this, locationList, newList)
    }
}