package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
        _binding =  FragmentSupportBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.support) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        //Setting the current item value
        viewModel.setCurrentItem(arg.currentProject)

//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
//            Navigation.findNavController(requireView()).navigateUp()
//            //findNavController().navigate(R.id.action_supportFragment_to_mainFragment)
//        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}