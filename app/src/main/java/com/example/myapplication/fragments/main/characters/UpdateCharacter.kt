package com.example.myapplication.fragments.main.characters

import android.Manifest
import android.app.Activity
import android.content.Context
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUpdateCharacterBinding
import com.example.myapplication.model.Characters
import com.example.myapplication.viewmodel.DataViewModel


class UpdateCharacter : Fragment() {
    private lateinit var viewModel: DataViewModel
    private var imageUri: Uri? = null
    private val args by navArgs<UpdateCharacterArgs>()
    private var _binding: FragmentUpdateCharacterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        _binding = FragmentUpdateCharacterBinding.inflate(inflater, container, false)

        setDataBeforeUpdate(args.currentChar)

        return binding.root
    }

    private fun setDataBeforeUpdate(currentChar: Characters) {
        binding.updCharacterNameField.setText(currentChar.char_name)
        binding.updCharacterAliasField.setText(currentChar.char_alias)
        binding.updCharacterSceneField.setText(currentChar.char_scene)
        binding.updCharacterBioField.setText(currentChar.bio)
        binding.updCharacterProfilePic.setImageURI(currentChar.profile_image)
        imageUri = currentChar.profile_image

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updCharacterProfilePic.setOnClickListener {
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


        binding.include5.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include5.customTopBarLayout.title = getString(R.string.characters_header)
        binding.include5.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.include5.customTopBarLayout.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save_menu){
                    updateCharacter()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateCharacter() {
        val characterName = binding.updCharacterNameField.text.toString()
        val characterAlias = binding.updCharacterAliasField.text.toString()
        val characterScene = binding.updCharacterSceneField.text.toString()
        val bio = binding.updCharacterBioField.text.toString()
        val image = imageUri
        //val imageView: ImageView = binding.updateLocationImage
//        val image = BitmapFactory.decodeResource(resources,R.id.locationImage)
        //Log.d("Bitmap:", "${image.width} , ${image.height}, ${image.config}")
        if(inputCheck(characterName,characterAlias,characterScene,bio, image)){
            //val image : Bitmap = imageView.drawable.toBitmap()
            val currCharacter = Characters(args.currentChar.char_id, characterName,characterAlias,
                characterScene, bio, args.currentChar.projectID, image)
            //val currScene = Scene(0, sceneName, sceneLocation, desc, projectID)
            viewModel.updateCharacter(currCharacter)
            Toast.makeText(requireContext(), R.string.update_success, Toast.LENGTH_LONG).show()
            Navigation.findNavController(requireView()).navigateUp()
        }
        else
            Toast.makeText(requireContext(), R.string.set_fields, Toast.LENGTH_LONG).show()
    }

    private fun inputCheck(characterName: String, characterAlias: String, characterScene: String, bio: String, image: Uri?): Boolean {
        return !(TextUtils.isEmpty(characterName) || TextUtils.isEmpty(characterAlias) || TextUtils.isEmpty(characterScene)
                || Uri.EMPTY.equals(image) || image ==null || TextUtils.isEmpty(bio)
                )

    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
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
            binding.updCharacterProfilePic.setImageURI(imageUri)
        }

    }

    private fun grantReadPermissionToUri(requireContext: Context, imageUri: Uri?) {
        requireContext.grantUriPermission(
            context?.packageName,imageUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        if (imageUri != null) {
            requireContext.contentResolver.takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}