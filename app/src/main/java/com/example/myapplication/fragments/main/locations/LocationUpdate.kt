package com.example.myapplication.fragments.main.locations

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs

import android.Manifest
import android.content.Context
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLocationUpdateBinding
import com.example.myapplication.model.Location
import com.example.myapplication.viewmodel.DataViewModel


class LocationUpdate : Fragment() {
    private lateinit var viewModel: DataViewModel
    private var imageUri: Uri? = null
    private val args by navArgs<LocationUpdateArgs>()
    private var _binding: FragmentLocationUpdateBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        _binding = FragmentLocationUpdateBinding.inflate(inflater, container, false)

        setDataBeforeUpdate(args.currentLocation)
        return binding.root
    }

    private fun setDataBeforeUpdate(currentLocation: Location) {
        binding.updateLocationName.setText(currentLocation.location_name)
        binding.updateLocationScene.setText(currentLocation.location_scene)
        binding.updateLocationDescription.setText(currentLocation.desc)
        binding.updateLocationImage.setImageURI(currentLocation.image)
        imageUri = currentLocation.image

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateLocationImage.setOnClickListener {
            //binding.updateLocationImage.setImageDrawable(null)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                if(ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        //android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }
                else
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }
            //chooseImageGallery()
        }

        binding.include13.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include13.customTopBarLayout.title = getString(R.string.locations_header)
        binding.include13.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        binding.include13.customTopBarLayout.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save_menu){
                    updateLocation()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        intent.type = "image/*"
        startForResult.launch(intent)
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            imageUri = data?.data
            grantReadPermissionToUri(requireContext(), imageUri)
//            val data: Intent? = result.data
//            imageUri = data?.data
//            val source = ImageDecoder.createSource(requireActivity().contentResolver,data)
            //binding.locationImage.setImageBitmap(data?.data)
            binding.updateLocationImage.setImageURI(imageUri)
        }

    }

    private fun grantReadPermissionToUri(requireContext: Context, imageUri: Uri?) {
        requireContext.grantUriPermission(
            context?.packageName,imageUri,
            FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        if (imageUri != null) {
            requireContext.contentResolver.takePersistableUriPermission(imageUri, FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted)
            chooseImageGallery()
        else
            Toast.makeText(requireContext(),R.string.perms_denied, Toast.LENGTH_SHORT).show()
    }

    private fun updateLocation() {
        val locationName = binding.updateLocationName.text.toString()
        val locationScene = binding.updateLocationScene.text.toString()
        val desc = binding.updateLocationDescription.text.toString()
        val image = imageUri
        //val imageView: ImageView = binding.updateLocationImage
//        val image = BitmapFactory.decodeResource(resources,R.id.locationImage)
        //Log.d("Bitmap:", "${image.width} , ${image.height}, ${image.config}")
        if(inputCheck(locationName,locationScene,desc, image)){
            //val image : Bitmap = imageView.drawable.toBitmap()
            val currLocation = Location(args.currentLocation.location_id, locationName,locationScene,args.currentLocation.projectID, image, desc)
            //val currScene = Scene(0, sceneName, sceneLocation, desc, projectID)
            viewModel.updateLocation(currLocation)
            Toast.makeText(requireContext(), R.string.update_success, Toast.LENGTH_LONG).show()
            Navigation.findNavController(requireView()).navigateUp()
        }
        else
            Toast.makeText(requireContext(), R.string.set_fields, Toast.LENGTH_LONG).show()
    }

    private fun inputCheck(sceneName: String, sceneLocation: String, desc: String, image: Uri?): Boolean {
        return !(TextUtils.isEmpty(sceneName) || TextUtils.isEmpty(sceneLocation) || TextUtils.isEmpty(desc)
                || Uri.EMPTY.equals(image) || image ==null
                )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}