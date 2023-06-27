package com.example.myapplication.fragments.main.locations

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_LOCAL_ONLY
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddLocationBinding
import com.example.myapplication.model.Location
import com.example.myapplication.viewmodel.DataViewModel

class AddLocationFragment : Fragment() {
    lateinit var viewModel: DataViewModel
    private var imageUri : Uri? = null
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
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(EXTRA_LOCAL_ONLY, true)
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
            //val newdata: Uri = result
//            if(result.data != null){
//                val bitmap = result.data?.extras?.get("data") as? Bitmap
//                binding.locationImage.setImageBitmap(bitmap)
//            }
            val data: Intent? = result.data
            imageUri = data?.data
            grantReadPermissionToUri(requireContext(), imageUri)

//            val source = ImageDecoder.createSource(requireActivity().contentResolver,data)
            //binding.locationImage.setImageBitmap(data?.data)
            binding.locationImage.setImageURI(imageUri)
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

        binding.include11.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include11.customTopBarLayout.title = getString(R.string.locations_header)
        binding.include11.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.include11.customTopBarLayout.addMenuProvider(object: MenuProvider {
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
        //val imageView: ImageView = binding.locationImage
        val image = imageUri
//        val image = BitmapFactory.decodeResource(resources,R.id.locationImage)
        //Log.d("Bitmap:", "${image.width} , ${image.height}, ${image.config}")
        if(inputCheck(locationName,locationScene,desc, image)){
            //val image = Uri.parse("android.resource://com.example.myapplication/" + R.dr)
            //val image : Bitmap = imageView.drawable.toBitmap()
            //val path = MediaStore.Images.Media.insertImage(in, image,)
            val currLocation = Location(0, locationName,locationScene,projectID, image, desc)
            //val currScene = Scene(0, sceneName, sceneLocation, desc, projectID)
            viewModel.addLocation(currLocation)
            Toast.makeText(requireContext(), R.string.add_success, Toast.LENGTH_LONG).show()
            Navigation.findNavController(requireView()).navigateUp()
        }
        else
            Toast.makeText(requireContext(), R.string.set_fields, Toast.LENGTH_LONG).show()
    }

    private fun inputCheck(sceneName: String, sceneLocation: String, desc: String, image: Uri?): Boolean {
        return !(TextUtils.isEmpty(sceneName) || TextUtils.isEmpty(sceneLocation) || TextUtils.isEmpty(desc) || Uri.EMPTY.equals(image) || image ==null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}