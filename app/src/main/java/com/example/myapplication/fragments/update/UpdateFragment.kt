package com.example.myapplication.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.text.isDigitsOnly
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddProjectBinding
import com.example.myapplication.databinding.FragmentUpdateBinding
import com.example.myapplication.model.Projects
import com.example.myapplication.viewmodel.DataViewModel


class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mProjectViewModel: DataViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mProjectViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.updProjectName.setText(args.currentProject.pr_name)
        binding.updProjectAuthor.setText(args.currentProject.author_name)
        binding.updProjectDate.setText(args.currentProject.date.toString())

        binding.updButton.setOnClickListener{
            updateItem()
        }
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost : MenuHost = requireActivity()

        menuHost.addMenuProvider(object:MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.delete_menu){
                    deleteProject()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteProject() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            mProjectViewModel.deleteProject(args.currentProject)
            Toast.makeText(
                requireContext(),
                "${args.currentProject.pr_name} successfully deleted!",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.mainFragment)
        }
        builder.setNegativeButton("No"){_, _->

        }
        builder.setTitle("Delete ${args.currentProject.pr_name}?")
        builder.setMessage("Do you want to delete ${args.currentProject.pr_name}?")
        builder.create().show()
    }

    private fun updateItem(){
        val projectName = binding.updProjectName.text.toString()
        val authorName = binding.updProjectAuthor.text.toString()
        val date = Integer.parseInt(binding.updProjectDate.text.toString())

        if (inputCheck(projectName, authorName, binding.updProjectDate.text)){
            //Create project object
            val updatedProject = Projects(args.currentProject.id, projectName,authorName,date)
            //Update current project
            mProjectViewModel.updateProject(updatedProject)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            //Navigate back
//            val action = UpdateFragmentDirections.actionUpdateFragmentToProjectDetailsFragment(args.currentProject)
//            findNavController().navigate(action)
            Navigation.findNavController(requireView()).navigateUp()
        }
        else
        {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(projectName: String, authorName: String, date: Editable) : Boolean{
        return !(TextUtils.isEmpty(projectName) || TextUtils.isEmpty(authorName) || date.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}