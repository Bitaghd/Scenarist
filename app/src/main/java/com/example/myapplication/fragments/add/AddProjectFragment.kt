package com.example.myapplication.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.DataViewModel
import com.example.myapplication.model.Projects
import com.example.myapplication.databinding.FragmentAddProjectBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        val navbar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        navbar.visibility = View.GONE
        mDataViewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.include15.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include15.customTopBarLayout.title = getString(R.string.project_header)
        binding.include15.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.include15.customTopBarLayout.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save_menu){
                    insertDataToDatabase()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun insertDataToDatabase() {
        val projectName = binding.edProjectName.text.toString()
        val authorName = binding.edProjectAuthor.text.toString()
        val date = binding.edProjectDate.text.toString()

        if (inputCheck(projectName, authorName, date)){
            //Create user object
            val project = Projects(0,projectName,authorName,Integer.parseInt(date))
            //Add data to database
            mDataViewModel.addProjects(project)
            Toast.makeText(requireContext(), R.string.add_success, Toast.LENGTH_LONG).show()
            //Navigate back
            //findNavController().navigate(R.id.action_addProjectFragment_to_mainFragment)
            Navigation.findNavController(requireView()).navigateUp()
        }
        else{
            Toast.makeText(requireContext(), R.string.set_fields, Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(projectName: String, authorName: String, date: String) : Boolean{
        //Also check for an integer value of a date
        return !(TextUtils.isEmpty(projectName) || TextUtils.isEmpty(authorName) || TextUtils.isEmpty(date))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}