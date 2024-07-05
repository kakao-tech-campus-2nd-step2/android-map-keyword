package campus.tech.kakao.map.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ListItemBinding
import campus.tech.kakao.map.databinding.LogItemBinding
import campus.tech.kakao.map.model.Location

class LogAdapter: RecyclerView.Adapter<LogAdapter.LogViewHolder>() {
    var logList: MutableList<Location> = mutableListOf()
    inner class LogViewHolder(private val binding: LogItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(location: Location){
            binding.location = location
            binding.btnLogDel.setOnClickListener {
                (bindingAdapter as LogAdapter).removeLog(bindingAdapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val binding = LogItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val location = logList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int = logList.size

    fun addLog(location:Location){
        val existingIndex=logList.indexOfFirst { it.name == location.name }
        if (existingIndex != -1){
            logList.removeAt(existingIndex)
        }
        logList.add(0,location)
        notifyDataSetChanged()
    }
    fun removeLog(position: Int){
        logList.removeAt(position)
        notifyDataSetChanged()
    }
}