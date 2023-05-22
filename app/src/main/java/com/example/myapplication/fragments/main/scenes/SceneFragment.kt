package com.example.myapplication.fragments.main.scenes

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.databinding.FragmentSceneBinding
import com.example.myapplication.fragments.main.MainAdapter
import com.example.myapplication.model.Scene
import com.example.myapplication.viewmodel.DataViewModel

class SceneFragment : Fragment(), ScenesAdapter.RawClickListener {
    private lateinit var viewModel: DataViewModel
    private val adapter = ScenesAdapter(this@SceneFragment)
    private var _binding: FragmentSceneBinding? = null
    private val binding get() = _binding!!
    private var projectID: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSceneBinding.inflate(inflater, container, false)


        //RecyclerView
        //val adapter = ScenesAdapter(this@SceneFragment)
        val rv = binding.ScenesList
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        //Get the id of current project
        viewModel.currentProject.observe(viewLifecycleOwner, Observer { project->
            projectID = project.id
        })

        //Get the list of all scenes in the project
//        viewModel.getScenesInProject(projectID).observe(viewLifecycleOwner, Observer {scene->
//            adapter.setData(scene)
//
//        })

        //Add new scene
        binding.addSceneButton.setOnClickListener {
            val action = SceneFragmentDirections.actionSceneFragmentToAddSceneFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getScenesInProject(projectID).observe(viewLifecycleOwner, Observer {scene->
            adapter.setData(scene)

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun deleteScene(scene: Scene) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            viewModel.deleteScene(scene)
            Toast.makeText(
                requireContext(),
                "${scene.scene_name} successfully deleted!",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _->

        }
        builder.setTitle("Delete ${scene.scene_name}?")
        builder.setMessage("Do you want to delete ${scene.scene_name}?")
        builder.create().show()
    }

    }
