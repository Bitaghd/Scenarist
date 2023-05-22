package com.example.myapplication.fragments.main.locations

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLocationBinding
import com.example.myapplication.databinding.FragmentProjectDetailsBinding
import com.example.myapplication.fragments.main.scenes.ScenesAdapter
import com.example.myapplication.model.Location
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene
import com.example.myapplication.viewmodel.DataViewModel

class LocationFragment : Fragment(), LocationsAdapter.RawClickListener {
    lateinit var viewModel: DataViewModel
    private val adapter = LocationsAdapter(this@LocationFragment)
    private var _binding: FragmentLocationBinding? = null
    private lateinit var currentProject: Projects
    private var projectID: Int = 0
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater, container, false)



        //val adapter = LocationsAdapter(this@LocationFragment)
        val rv = binding.LocationsList
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        //Get the id of current project
        viewModel.currentProject.observe(viewLifecycleOwner, Observer { project->
            projectID = project.id
        })



        binding.addLocationButton.setOnClickListener {
            val action = LocationFragmentDirections.actionLocationFragmentToAddLocationFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocationsInProject(projectID).observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun deleteLocation(location: Location) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            viewModel.deleteLocation(location)
            Toast.makeText(
                requireContext(),
                "${location.location_name} successfully deleted!",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _->

        }
        builder.setTitle("Delete ${location.location_name}?")
        builder.setMessage("Do you want to delete ${location.location_name}?")
        builder.create().show()
    }
    }



