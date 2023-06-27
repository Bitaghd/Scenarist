package com.example.myapplication.fragments.main.locations

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLocationBinding
import com.example.myapplication.model.Location
import com.example.myapplication.viewmodel.DataViewModel

class LocationFragment : Fragment(), LocationsAdapter.RawClickListener {
    lateinit var viewModel: DataViewModel
    private val adapter = LocationsAdapter(this@LocationFragment)
    private var _binding: FragmentLocationBinding? = null
    private var projectID: Int = 0
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater, container, false)


        binding.include10.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include10.customTopBarLayout.title = getString(R.string.locations_header)
        binding.include10.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        //val adapter = LocationsAdapter(this@LocationFragment)
        val rv = binding.LocationsList
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        rv.apply {
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            addItemDecoration(divider)
        }

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
        builder.setPositiveButton(R.string.delete_yes){_, _->
            viewModel.deleteLocation(location)
            Toast.makeText(
                requireContext(),
                "${location.location_name} " + getString(R.string.delete_success),
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.delete_no){_, _->

        }
        builder.setTitle(getString(R.string.delete_confirm) + " ${location.location_name}?")
        builder.setMessage(getString(R.string.delete_desc) + " ${location.location_name}?")
        builder.create().show()
    }
    }



