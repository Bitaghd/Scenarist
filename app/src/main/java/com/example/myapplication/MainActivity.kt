package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navController = navHostFragment.navController
//        supportActionBar?.setCustomView(R.layout.custom_action_bar)
//        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        supportActionBar?.setDisplayShowCustomEnabled(true)
//        val view = supportActionBar?.customView
//        val back = view?.findViewById(R.id.action_bar_back)
        //setupActionBarWithNavController(navController)
        navView.setupWithNavController(navController)
        //navView.setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            if(destination.id == R.id.activity_main || destination.id == R.id.main_fragment) {

                binding.bottomNavigation.visibility = View.GONE
            } else {

                binding.bottomNavigation.visibility = View.VISIBLE
            }

        }

        mySettings()
//        val db = MainDB.getDb(this)
//        db.getDao().getAllItems().asLiveData().observe(this){list ->
//            binding.textView.text = ""
//            list.forEach {
//                val text = "Id: ${it.id} Name : ${it.name} Author: ${it.author}\n"
//                binding.textView.append(text)
//            }
//        }
//        binding.saveButton.setOnClickListener {
//            val item = Item(null,
//                binding.edName.text.toString(),
//                binding.edAuthor.text.toString()
//            )
//            Thread{
//                db.getDao().insertItem(item)
//            }.start()
//
//
//        }
//        myToolbar = findViewById(R.id.ToolBar)
//        myToolbar.title = "Главнаяяя"
//        setSupportActionBar(myToolbar)

    }
    private fun mySettings(){
        var appLocale : LocaleListCompat = LocaleListCompat.forLanguageTags("en")

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val language = prefs.getString("lang","english")
        appLocale = if(language=="english"){
            LocaleListCompat.forLanguageTags("en")
        } else{
            LocaleListCompat.forLanguageTags("ru")
        }
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        //oldInstance = outState
//        outState.clear()
//    }

//    public fun replaceFragment(){
//        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        when(item.itemID){item ->
//
//        }
//    }
}