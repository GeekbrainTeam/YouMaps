package com.amk.youmaps

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amk.core.MainViewModel
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("f1645046-8bde-47ad-b818-b302110af3ae")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.maps -> {
                if (navController.currentDestination?.id != R.id.mapsFragment) {
                    navController.navigate(R.id.action_listCoordinatesFragment_to_mapsFragment)
                } else {
                    val bundle = Bundle()
                    with(viewModel.selectedCoordinateWithName.value) {
                        bundle.putDouble("Latitude", latitude)
                        bundle.putDouble("Longitude", longitude)
                        navController.navigate(R.id.saveCoordinateDialogFragment, bundle)
                    }
                }
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