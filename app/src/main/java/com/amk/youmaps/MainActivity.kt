package com.amk.youmaps

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private lateinit var mapView: MapView

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("f1645046-8bde-47ad-b818-b302110af3ae")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolBar)
//        mapView = findViewById(R.id.mapview)
//        mapView.map.move(CameraPosition(
//            Point(59.923960, 30.345504), 11.0f, 0.0f, 0.0f),
//            Animation(Animation.Type.SMOOTH, 300f),
//            null
//        )
    }

//    override fun onStart() {
//        mapView.onStart()
//        MapKitFactory.getInstance().onStart()
//        super.onStart()
//    }
//
//    override fun onStop() {
//        mapView.onStop()
//        MapKitFactory.getInstance().onStop()
//        super.onStop()
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.maps -> {
                navController.navigate(R.id.mapsFragment)
                true
            }

            R.id.list_coord -> {
                navController.navigate(R.id.listCoordinatesFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}