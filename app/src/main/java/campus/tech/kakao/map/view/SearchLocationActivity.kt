package campus.tech.kakao.map.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import campus.tech.kakao.map.databinding.ActivitySearchLocationBinding
import campus.tech.kakao.map.viewmodel.SearchLocationViewModel

class SearchLocationActivity : AppCompatActivity() {
	private lateinit var viewModel: SearchLocationViewModel
	private lateinit var binding: ActivitySearchLocationBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel = ViewModelProvider(this)[SearchLocationViewModel::class.java]
		binding = ActivitySearchLocationBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.removeSearchInputButton.setOnClickListener {
			binding.searchInputEditText.text.clear()
		}
	}
}