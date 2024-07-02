package campus.tech.kakao.map.View.Observer

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmptyPlaceObserver(rv: RecyclerView, tv: TextView) : RecyclerView.AdapterDataObserver() {
    private var emptyView : TextView? = null
    private var recyclerView : RecyclerView? = null

    init{
        recyclerView = rv
        emptyView = tv
        checkIfEmpty()
    }

    private fun checkIfEmpty(){
        if(emptyView != null && recyclerView!!.adapter != null){
            val isRecyclerHasData = recyclerView!!.adapter!!.itemCount != 0

            if(isRecyclerHasData){
                recyclerView!!.visibility = VISIBLE
                emptyView!!.visibility = GONE
            } else {
                recyclerView!!.visibility = GONE
                emptyView!!.visibility = VISIBLE
            }
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }
}