package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.FragmentProjectDetailsBinding
import com.example.myapplication.databinding.FragmentSupportBinding
import com.example.myapplication.fragments.main.project_details.ProjectDetailsFragment
import com.example.myapplication.viewmodel.DataViewModel


class SupportFragment : Fragment() {
    private val arg by navArgs<SupportFragmentArgs>()
    private var _binding: FragmentSupportBinding? = null
    lateinit var viewModel : DataViewModel
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         //Inflate the layout for this fragment
        _binding =  FragmentSupportBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        //replaceFragment()
//        viewModel.readAllProjects.observe(viewLifecycleOwner, {projects ->
//            findNavController().navigate(R.id.projectDetailsFragment)
//
//        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.support) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)
//        binding.bottomNav.setOnItemSelectedListener {item->
//            when(item.itemId){
//                R.id.project_home ->{
//                    findNavController().navigate(R.id.projectDetailsFragment)
//                    return@setOnItemSelectedListener true
//                }
//                R.id.scenes ->{
//                    findNavController().navigate(R.id.sceneFragment)
//                    //findNavController().navigate(R.id.action_projectDetailsFragment_to_sceneFragment)
//                    return@setOnItemSelectedListener true
//                }
//
//                R.id.locations->{
//                    findNavController().navigate(R.id.locationFragment)
//                    //findNavController().navigate(R.id.action_projectDetailsFragment_to_locationFragment)
//                    return@setOnItemSelectedListener true
//                }
//                R.id.characters->{
//                    findNavController().navigate(R.id.characterFragment)
//                    //findNavController().navigate(R.id.action_projectDetailsFragment_to_characterFragment)
//                    return@setOnItemSelectedListener true
//                }
//            }
//            false
//        }
    }
//    private fun replaceFragment(){
//        var fragment: Fragment? = null
//          //val fragmentTransaction = fragmentManager.beginTransaction()
//        (MainActivity)
//        binding.bottomNav.setOnItemSelectedListener {item ->
//            when(item.itemId){
//                R.id.projectDetailsFragment -> {
//                    fragment = ProjectDetailsFragment()
//                }
//                R.id.locationFragment
//            }
//
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}