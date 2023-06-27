package com.example.myapplication.fragments.main.scenes

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSceneDetailsBinding
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
        binding.include7.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include7.customTopBarLayout.title = getString(R.string.scene_header)
        binding.include7.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.include7.customTopBarLayout.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.edit_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.edit_menu){
                    val action = SceneDetailsDirections.actionSceneDetailsToSceneUpdate(args.currentScene)
                    findNavController().navigate(action)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    @SuppressLint("SetTextI18n")
    private fun setSceneFromDatabase(currentScene: Scene) {
        binding.sceneNameView.text = currentScene.scene_name
        binding.sceneLocationView.text = currentScene.location
        binding.sceneDescView.text = currentScene.desc
        binding.sceneDescView.movementMethod = ScrollingMovementMethod()

        val text = currentScene.desc.length/3000
        if(text < 1){
            binding.timingView.text = "< 1 minute"
        }
        else
            binding.timingView.text = "$text minutes"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}