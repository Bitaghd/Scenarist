package com.example.myapplication.fragments.main.scenes

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSceneDetailsBinding
import com.example.myapplication.databinding.FragmentSceneUpdateBinding
import com.example.myapplication.databinding.FragmentUpdateBinding
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene
import com.example.myapplication.viewmodel.DataViewModel

class SceneUpdate : Fragment() {
    private lateinit var viewModel: DataViewModel
    private val args by navArgs<SceneUpdateArgs>()
    private var _binding: FragmentSceneUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentSceneUpdateBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        setDataBeforeUpdate(args.currentScene)
        return binding.root
    }

    private fun setDataBeforeUpdate(currentScene: Scene) {
        binding.sceneNameField.setText(currentScene.scene_name)
        binding.sceneLocationField.setText(currentScene.location)
        binding.sceneDescriptionField.setText(currentScene.desc)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost : MenuHost = requireActivity()

        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save_menu){
                    updateScene()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    private fun updateScene() {
        val sceneName = binding.sceneNameField.text.toString()
        val sceneLocation = binding.sceneLocationField.text.toString()
        val desc = binding.sceneDescriptionField.text.toString()
        if(inputCheck(sceneName,sceneLocation,desc)){
            val currScene = Scene(args.currentScene.scene_id, sceneName, sceneLocation, desc, args.currentScene.projectID)
            viewModel.updateScene(currScene)

            Toast.makeText(requireContext(), R.string.update_success, Toast.LENGTH_LONG).show()
            Navigation.findNavController(requireView()).navigateUp()
        }
        else
            Toast.makeText(requireContext(), R.string.set_fields, Toast.LENGTH_LONG).show()
    }

    private fun inputCheck(sceneName: String, sceneLocation: String, desc: String): Boolean {
        return !(TextUtils.isEmpty(sceneName) || TextUtils.isEmpty(sceneLocation) || TextUtils.isEmpty(desc))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}