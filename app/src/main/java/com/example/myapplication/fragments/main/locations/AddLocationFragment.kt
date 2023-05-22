package com.example.myapplication.fragments.main.locations

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddLocationBinding
import com.example.myapplication.databinding.FragmentProjectDetailsBinding
import com.example.myapplication.model.Location
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene
import com.example.myapplication.viewmodel.DataViewModel

class AddLocationFragment : Fragment() {
    lateinit var viewModel: DataViewModel
    private var _binding: FragmentAddLocationBinding? = null
    //private lateinit var currentProject: Projects
    private var projectID: Int = 0
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        _binding = FragmentAddLocationBinding.inflate(inflater, container, false)


        viewModel.currentProject.observe(viewLifecycleOwner, Observer {
            projectID = it.id
        })



        return binding.root
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startForResult.launch(intent)
        //startActivityForResult()

        //startActivityForResult(intent, IMAGE_CHOOSE)
    }
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
//            if(result.data != null){
//                val bitmap = result.data?.extras?.get("data") as? Bitmap
//                binding.locationImage.setImageBitmap(bitmap)
//            }
            val data: Intent? = result.data
//            val source = ImageDecoder.createSource(requireActivity().contentResolver,data)
            //binding.locationImage.setImageBitmap(data?.data)
            binding.locationImage.setImageURI(data?.data)
        }

    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted)
            chooseImageGallery()
        else
            Toast.makeText(requireContext(),"Permission denied", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.locationImage.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                if(checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }
                else
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }
        }

        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save_menu){
                    insertLocationsIntoDB()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun insertLocationsIntoDB() {
        val locationName = binding.locationNameField.text.toString()
        val locationScene = binding.location.text.toString()
        val desc = binding.sceneDescriptionField.text.toString()
        val imageView: ImageView = binding.locationImage
//        val image = BitmapFactory.decodeResource(resources,R.id.locationImage)
        //Log.d("Bitmap:", "${image.width} , ${image.height}, ${image.config}")
        if(inputCheck(locationName,locationScene,desc, imageView)){
            val image : Bitmap = imageView.drawable.toBitmap()
            val currLocation = Location(0, locationName,locationScene,projectID,image)
            //val currScene = Scene(0, sceneName, sceneLocation, desc, projectID)
            viewModel.addLocation(currLocation)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            Navigation.findNavController(requireView()).navigateUp()
        }
        else
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
    }

    private fun inputCheck(sceneName: String, sceneLocation: String, desc: String, imageView: ImageView): Boolean {
        return !(TextUtils.isEmpty(sceneName) || TextUtils.isEmpty(sceneLocation) || TextUtils.isEmpty(desc) || imageView.drawable == null)
    }

    companion object{
        val IMAGE_CHOOSE = 1000
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}