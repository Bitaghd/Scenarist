package com.example.myapplication.fragments.main.characters

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCharacterDetailsBinding
import com.example.myapplication.databinding.FragmentLocationDetailsBinding
import com.example.myapplication.fragments.main.locations.LocationDetailsDirections
import com.example.myapplication.model.Characters
import com.example.myapplication.viewmodel.DataViewModel


class CharacterDetails : Fragment() {
    private lateinit var viewModel : DataViewModel
    private val arg by navArgs<CharacterDetailsArgs>()
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        setLocationFromDatabase(arg.currentChar)
        return binding.root
    }

    private fun setLocationFromDatabase(currentChar: Characters) {
        binding.characterNameView.text = currentChar.char_name
        binding.characterAliasView.text = currentChar.char_alias
        binding.characterSceneView.text = currentChar.char_scene
        binding.characterBioView.text = currentChar.bio
        binding.characterBioView.movementMethod = ScrollingMovementMethod()
        binding.characterProfPic.setImageURI(currentChar.profile_image)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.edit_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.edit_menu){
                    val action = CharacterDetailsDirections.actionCharacterDetailsToUpdateCharacter(arg.currentChar)
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