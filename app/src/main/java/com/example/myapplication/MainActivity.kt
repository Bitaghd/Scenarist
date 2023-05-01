package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.main.MainAdapter
import com.example.myapplication.model.Projects
import com.example.myapplication.viewmodel.DataViewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
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

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    public fun replaceFragment(){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when(item.itemID){item ->

        }
    }
}