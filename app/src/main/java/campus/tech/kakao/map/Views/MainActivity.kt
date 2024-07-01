package campus.tech.kakao.map.Views

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import campus.tech.kakao.map.R

class MainActivity : AppCompatActivity() {
    lateinit var searchResultFragmentContainer: FragmentContainerView
    lateinit var searchInput: EditText
    lateinit var savedWordBar: View

    private fun initiateViews(){
        searchResultFragmentContainer = findViewById(R.id.fragment_container_search_result)
        searchInput = findViewById(R.id.input_search)
        savedWordBar = findViewById(R.id.saved_search_bar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateViews()
    }
}
