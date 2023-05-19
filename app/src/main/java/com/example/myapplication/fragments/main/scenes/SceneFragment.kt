package com.example.myapplication.fragments.main.scenes

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.databinding.FragmentSceneBinding
import com.example.myapplication.fragments.main.MainAdapter
import com.example.myapplication.viewmodel.DataViewModel

class SceneFragment : Fragment() {
    private lateinit var viewModel: DataViewModel
    private var _binding: FragmentSceneBinding? = null
    private val binding get() = _binding!!
    private var projectID: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSceneBinding.inflate(inflater, container, false)


        //RecyclerView
        val adapter = ScenesAdapter()
        val rv = binding.ScenesList
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        viewModel.currentProject.observe(viewLifecycleOwner, Observer { project->
            projectID = project.id
        })

        viewModel.getScenesInProject(projectID).observe(viewLifecycleOwner, Observer {scene->
            adapter.setData(scene)

        })

        binding.addSceneButton.setOnClickListener {
            findNavController().navigate(R.id.action_sceneFragment_to_addSceneFragment)
        }
//        var projectID = 0
//        projectID = viewModel.getProjectId()
//        if(projectID == 0)
//            Log.d(TAG, "WORKING")
//        else
//            Log.d(TAG,"YOURE DUM")


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}