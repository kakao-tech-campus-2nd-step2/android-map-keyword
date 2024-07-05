package campus.tech.kakao.map

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.model.Location

object ListUtil {
    fun notifyListchanged(
        adapter: RecyclerView.Adapter<*>,
        oldList: List<Location>,
        newList: List<Location>
    ){
        val diffUtil = DiffUtilCallback(oldList,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(adapter)
    }
}