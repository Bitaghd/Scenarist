package com.example.myapplication

import android.app.LocaleManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

import androidx.appcompat.widget.Toolbar
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.main.MainAdapter
import com.example.myapplication.model.Projects
import com.example.myapplication.viewmodel.DataViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            if(destination.id == R.id.activity_main || destination.id == R.id.main_fragment) {

                binding.bottomNav2.visibility = View.GONE
            } else {

                binding.bottomNav2.visibility = View.VISIBLE
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