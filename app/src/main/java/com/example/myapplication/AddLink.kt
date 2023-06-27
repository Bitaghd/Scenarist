package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
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
import com.example.myapplication.databinding.FragmentAddLinkBinding
import com.example.myapplication.model.Details
import com.example.myapplication.viewmodel.DataViewModel

class AddLink : Fragment() {
    private lateinit var viewModel : DataViewModel
    private var _binding: FragmentAddLinkBinding? = null
    private val binding get() = _binding!!
    private var projectID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddLinkBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[DataViewModel::class.java]
        viewModel.currentProject.observe(viewLifecycleOwner, Observer {
            projectID = it.id
        })
        Log.d(TAG, "Id after observing: $projectID")
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.include18.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include18.customTopBarLayout.title = getString(R.string.project_header)
        binding.include18.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.saveLinkButton.setOnClickListener {
            insertDetailsIntoDB()
        }
    }

    private fun insertDetailsIntoDB() {
        val linkName = binding.addLinkEdit.text.toString()
        Log.d(TAG, "Id and here: $projectID")
        if(inputCheck(linkName)){

            val currDetail = Details(0, linkName, projectID)
            viewModel.addDetails(currDetail)
            Toast.makeText(requireContext(), R.string.add_success, Toast.LENGTH_LONG).show()
            Navigation.findNavController(requireView()).navigateUp()
        }
        else
            Toast.makeText(requireContext(), R.string.set_fields, Toast.LENGTH_LONG).show()
    }

    private fun inputCheck(link: String): Boolean {
        return !(TextUtils.isEmpty(link))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}