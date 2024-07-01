package campus.tech.kakao.map

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listLocation)

        val dummy: List<Location> = listOf(
            Location("카페 a", "부산시 해운대구", Type.카페 ),
            Location("카페 b", "부산시 해운대구", Type.카페 ),
            Location("카페 c", "부산시 해운대구", Type.카페 )
        )

        listView.adapter = LocationListviewAdapter(dummy)
    }
}
