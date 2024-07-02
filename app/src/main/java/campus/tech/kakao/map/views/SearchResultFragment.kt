package campus.tech.kakao.map.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.models.SearchResult
import campus.tech.kakao.map.view_models.SearchActivityViewModel

class SearchResultFragment : Fragment() {
    lateinit var searchResultRecyclerView: RecyclerView
    lateinit var noResultHelpText: View
    private val viewModel by activityViewModels<SearchActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchResultRecyclerView = view.findViewById(R.id.list_search_result)
        noResultHelpText = view.findViewById(R.id.text_no_result)

        searchResultRecyclerView.adapter =
            SearchResultAdapter(LayoutInflater.from(activity))
        searchResultRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        viewModel.searchResult.observe(viewLifecycleOwner){
            (searchResultRecyclerView.adapter as? SearchResultAdapter)?.updateResult(it)
        }
    }
}