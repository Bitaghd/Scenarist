package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
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
        viewModel.setProject(arg.currentProject)
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

        //val currentPr = viewModel.findProjectById(id)


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            Navigation.findNavController(requireView()).navigateUp()
            //findNavController().navigate(R.id.action_supportFragment_to_mainFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}