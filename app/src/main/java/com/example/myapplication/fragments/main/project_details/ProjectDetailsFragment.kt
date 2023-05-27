package com.example.myapplication.fragments.main.project_details

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProjectDetailsBinding
import com.example.myapplication.model.Projects
import com.example.myapplication.viewmodel.DataViewModel
import java.lang.Exception


class ProjectDetailsFragment : Fragment() {
    lateinit var viewModel: DataViewModel
    private val args by navArgs<ProjectDetailsFragmentArgs>()
    private var _binding: FragmentProjectDetailsBinding? = null
//    private lateinit var currentProject: Projects
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProjectDetailsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        return binding.root
    }


    private fun setTextsFromDatabase(currentProject: Projects) {

        binding.projectName.text = currentProject.pr_name
        binding.author.text = currentProject.author_name
        binding.date.text = currentProject.date.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextsFromDatabase(args.currentProject)

//        val setDataObserver = Observer<Projects>{project->
//            setTextsFromDatabase(project)
//            currentProject = project
//        }
//        //Observing currentProject
//        viewModel.currentProject.observe(viewLifecycleOwner, setDataObserver)
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.edit_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.edit_menu){
                    val action =
                        ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToUpdateFragment(args.currentProject)
                    findNavController().navigate(action)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}