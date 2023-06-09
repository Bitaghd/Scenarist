package com.example.myapplication.fragments.main.scenes

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddSceneBinding
import com.example.myapplication.model.Scene
import com.example.myapplication.viewmodel.DataViewModel


class AddSceneFragment : Fragment() {
    private lateinit var viewModel : DataViewModel
    private var _binding: FragmentAddSceneBinding? = null
    private val binding get() = _binding!!
    private var projectID: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        //
        _binding = FragmentAddSceneBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[DataViewModel::class.java]
        viewModel.currentProject.observe(viewLifecycleOwner, Observer {
            projectID = it.id
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.include8.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include8.customTopBarLayout.title = getString(R.string.scene_header)
        binding.include8.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.include8.customTopBarLayout.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save_menu){
                    insertScenesIntoDB()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun insertScenesIntoDB() {
        val sceneName = binding.sceneNameField.text.toString()
        val sceneLocation = binding.sceneLocationField.text.toString()
        val desc = binding.sceneDescriptionField.text.toString()
        if(inputCheck(sceneName,sceneLocation,desc)){
            val currScene = Scene(0, sceneName, sceneLocation, desc, projectID)
            viewModel.addScene(currScene)
            Toast.makeText(requireContext(), R.string.add_success, Toast.LENGTH_LONG).show()
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