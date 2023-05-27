package com.example.myapplication.fragments.main.locations

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLocationDetailsBinding
import com.example.myapplication.databinding.FragmentSceneDetailsBinding
import com.example.myapplication.fragments.main.scenes.SceneDetailsArgs
import com.example.myapplication.fragments.main.scenes.SceneDetailsDirections
import com.example.myapplication.model.Location
import com.example.myapplication.viewmodel.DataViewModel


class LocationDetails : Fragment() {
    private lateinit var viewModel: DataViewModel
    private val args by navArgs<LocationDetailsArgs>()
    private var _binding: FragmentLocationDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        // Inflate the layout for this fragment
        _binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        setLocationFromDatabase(args.currentLocation)
        return binding.root
    }

    private fun setLocationFromDatabase(location: Location) {
        binding.locationNameView.text = location.location_name
        binding.locationSceneView.text = location.location_scene
        binding.locationDescriptionView.text = location.desc
        binding.locationDescriptionView.movementMethod = ScrollingMovementMethod()
        binding.sceneImageView.setImageURI(location.image)

    }
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        //oldInstance = outState
//        outState.clear()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.edit_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.edit_menu){
                    val action = LocationDetailsDirections.actionLocationDetailsToLocationUpdate(args.currentLocation)
                    findNavController().navigate(action)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}