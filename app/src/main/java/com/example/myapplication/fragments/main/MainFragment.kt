package com.example.myapplication.fragments.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.viewmodel.DataViewModel
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.model.Projects
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainFragment : Fragment(), MainAdapter.RawClickListener {
    private lateinit var mProjectViewModel: DataViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val navbar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        navbar.visibility = View.GONE
        //Recycler View
        val adapter = MainAdapter(this@MainFragment)
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.apply {
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            addItemDecoration(divider)
        }

        // Project view model
        mProjectViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        mProjectViewModel.readAllProjects.observe(viewLifecycleOwner, Observer {projects ->
            adapter.setData(projects)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addProjectFragment)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.include.customTopBarLayout.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.settings_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.settings_menu){
                    val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
                    findNavController().navigate(action)
                    //deleteAllProjects()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteAllProjects() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            mProjectViewModel.deleteAllProjects()
            Toast.makeText(
                requireContext(),
                "Projects successfully deleted!",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _->

        }
        builder.setTitle("Delete all projects?")
        builder.setMessage("Do you want to delete all projects?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun deleteProject(projects: Projects) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.delete_yes){_, _->
            mProjectViewModel.deleteProject(projects)
            Toast.makeText(
                requireContext(),
                "${projects.pr_name} " + getString(R.string.delete_success),
                Toast.LENGTH_SHORT).show()
            Navigation.findNavController(requireView()).navigateUp()
        }
        builder.setNegativeButton(R.string.delete_no){_, _->

        }
        builder.setTitle(getString(R.string.delete_confirm) + " ${projects.pr_name}?")
        builder.setMessage(getString(R.string.delete_desc) + " ${projects.pr_name}?")
        builder.create().show()
    }
}