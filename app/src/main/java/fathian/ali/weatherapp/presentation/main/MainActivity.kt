package fathian.ali.weatherapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fathian.ali.weatherapp.R
import fathian.ali.weatherapp.util.safeNavigate

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_menu_units) {
            findNavController(R.id.main_nav_host).navigate(R.id.action_mainFragment_to_unitsDialog)
            return true
        }
        return false
    }
}