package com.example.myapplication.fragments.main.project_details

import android.os.Bundle
import android.view.*
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


class ProjectDetailsFragment : Fragment() {
    //private val args by navArgs<ProjectDetailsFragmentArgs>()
    private var projectsList = emptyList<Projects>()
    private lateinit var viewModel: DataViewModel
    private var _binding: FragmentProjectDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProjectDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        viewModel.readAllProjects.observe(viewLifecycleOwner, Observer {projects ->
            setData(projects)
        })

        return binding.root
    }

    private fun setData(projects: List<Projects>?) {
        this.projectsList = projects
    }

    private fun setTextsFromDatabase() {
        binding.projectName.text = viewModel.
        binding.author.text = currentProject.author_name
        binding.date.text = args.currentProject.date.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setTextsFromDatabase()
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.edit_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.edit_menu){
                    val action =
                        ProjectDetailsFragmentDirections.actionProjectDetailsFragmentToUpdateFragment()
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