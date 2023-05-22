package com.example.myapplication.fragments.main.scenes

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSceneBinding
import com.example.myapplication.databinding.FragmentSceneDetailsBinding
import com.example.myapplication.fragments.main.project_details.ProjectDetailsFragmentDirections
import com.example.myapplication.model.Scene
import com.example.myapplication.viewmodel.DataViewModel


class SceneDetails : Fragment() {
    private lateinit var viewModel: DataViewModel
    private val args by navArgs<SceneDetailsArgs>()
    private var _binding: FragmentSceneDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        _binding = FragmentSceneDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        setSceneFromDatabase(args.currentScene)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
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
                    val action =SceneDetailsDirections.actionSceneDetailsToSceneUpdate(args.currentScene)
                    findNavController().navigate(action)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setSceneFromDatabase(currentScene: Scene) {
        binding.sceneNameView.text = currentScene.scene_name
        binding.sceneLocationView.text = currentScene.location
        binding.sceneDescView.text = currentScene.desc
        binding.sceneDescView.movementMethod = ScrollingMovementMethod()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}