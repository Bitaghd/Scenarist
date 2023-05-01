package com.example.myapplication.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication.viewmodel.DataViewModel
import com.example.myapplication.model.Projects
import com.example.myapplication.databinding.FragmentAddProjectBinding

class AddProjectFragment : Fragment() {
    private var _binding: FragmentAddProjectBinding? = null
    private val binding get() = _binding!!
    private lateinit var mDataViewModel: DataViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddProjectBinding.inflate(inflater, container, false)

        mDataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        binding.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase() {
        val projectName = binding.edProjectName.text.toString()
        val authorName = binding.edProjectAuthor.text.toString()
        val date = binding.edProjectDate.text

        if (inputCheck(projectName, authorName, date)){
            //Create user object
            val project = Projects(0,projectName,authorName,Integer.parseInt(date.toString()))
            //Add data to database
            mDataViewModel.addProjects(project)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            //Navigate back
            //findNavController().navigate(R.id.action_addProjectFragment_to_mainFragment)
            Navigation.findNavController(requireView()).navigateUp()
        }
        else{
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(projectName: String, authorName: String, date: Editable) : Boolean{
        //Also check for an integer value of a date
        return !(TextUtils.isEmpty(projectName) || TextUtils.isEmpty(authorName) || date.isEmpty())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}