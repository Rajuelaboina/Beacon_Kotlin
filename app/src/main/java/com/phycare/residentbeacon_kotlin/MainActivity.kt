package com.phycare.residentbeacon_kotlin

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.phycare.residentbeacon_kotlin.databinding.ActivityMainBinding
import com.phycare.residentbeacon_kotlin.utils.SharedPrefManager

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_residents, R.id.nav_programs,
                R.id.nav_manageproviders, R.id.nav_communication /*,R.id.nav_residentSearch,
                R.id.nav_residentSearchDetailsFragment,
                R.id.nav_programSearchFragment,
                R.id.nav_programSearchDetailsFragment*/
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout){
            val alertDialog = MaterialAlertDialogBuilder(this,R.style.RoundShapeTheme)
                .setTitle("Do You Want Exit App!")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, which -> //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    //finish();
                    // one time Login  ----------  //
                    SharedPrefManager.getInstance(applicationContext)!!.isLoggedOut
                }
                .setNegativeButton("Cancel") {
                   dialog, which -> dialog?.dismiss()
                }
                .show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}