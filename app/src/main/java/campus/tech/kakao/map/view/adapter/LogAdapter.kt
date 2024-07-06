package campus.tech.kakao.map.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.ListUtil
import campus.tech.kakao.map.databinding.ListItemBinding
import campus.tech.kakao.map.databinding.LogItemBinding
import campus.tech.kakao.map.model.Location

class LogAdapter(var logList: MutableList<Location>)
    : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {
    inner class LogViewHolder(private val binding: LogItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(location: Location){
            binding.location = location
            binding.btnLogDel.setOnClickListener {
                removeLog(bindingAdapterPosition)
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
        val newLogList = logList.toMutableList()
        val existingIndex = newLogList.indexOfFirst { it.name == location.name }
        if (existingIndex != -1) {
            newLogList.removeAt(existingIndex)
        }
        newLogList.add(0, location)
        notifyListUpdate(newLogList)
    }
    fun removeLog(position: Int){
        val newLogList = logList.toMutableList()
        newLogList.removeAt(position)
        notifyListUpdate(newLogList)
    }

    private fun notifyListUpdate(newList: MutableList<Location>){
        ListUtil.notifyListchanged(this, logList, newList)
        logList = newList
    }
}