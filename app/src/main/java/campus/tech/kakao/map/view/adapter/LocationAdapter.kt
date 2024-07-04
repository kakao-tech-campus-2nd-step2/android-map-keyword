package campus.tech.kakao.map.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ListItemBinding
import campus.tech.kakao.map.model.Location


class LocationAdapter(
    var locationList: List<Location> = listOf()
): RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    inner class LocationViewHolder(private val binding: ListItemBinding )
        :RecyclerView.ViewHolder(binding.root){
            fun bind(location: Location){
                binding.location = location
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

    fun setLocations(locations: List<Location>){
        locationList = locations
        notifyDataSetChanged()
    }
}