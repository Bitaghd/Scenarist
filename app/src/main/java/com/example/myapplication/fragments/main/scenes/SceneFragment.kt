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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSceneBinding
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

        rv.apply {
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            addItemDecoration(divider)
        }

        binding.include6.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include6.customTopBarLayout.title = getString(R.string.scene_header)
        binding.include6.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        //Get the id of current project

        viewModel.currentProject.observe(viewLifecycleOwner, Observer { project->
            projectID = project.id
        })
        Log.d(TAG, "Id: $projectID")

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
        builder.setPositiveButton(R.string.delete_yes){_, _->
            viewModel.deleteScene(scene)
            Toast.makeText(
                requireContext(),
                "${scene.scene_name} " + getString(R.string.delete_success),
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.delete_no){_, _->

        }
        builder.setTitle(getString(R.string.delete_confirm) + " ${scene.scene_name}?")
        builder.setMessage(getString(R.string.delete_desc) + " ${scene.scene_name}?")
        builder.create().show()
    }

    }
