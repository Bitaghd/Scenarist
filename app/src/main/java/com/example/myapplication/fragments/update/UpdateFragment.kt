package com.example.myapplication.fragments.update


import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUpdateBinding
import com.example.myapplication.model.Projects
import com.example.myapplication.viewmodel.DataViewModel


class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val args by navArgs<UpdateFragmentArgs>()
    //var currentProject: Projects? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DataViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        setDataBefore(args.currentProject)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setDataBefore(currentProject: Projects) {
        binding.updProjectName.setText(currentProject.pr_name)
        binding.updProjectAuthor.setText(currentProject.author_name)
        binding.updProjectDate.setText(currentProject.date.toString())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.include16.customTopBarLayout.setNavigationIcon(R.drawable.back)
        binding.include16.customTopBarLayout.title = getString(R.string.project_header)
        binding.include16.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        binding.include16.customTopBarLayout.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save_menu){
                    updateItem()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateItem(){
        val projectName = binding.updProjectName.text.toString()
        val authorName = binding.updProjectAuthor.text.toString()
        val date = Integer.parseInt(binding.updProjectDate.text.toString())

        if (inputCheck(projectName, authorName, binding.updProjectDate.text.toString())){
            //Create project object
            val updatedProject = Projects(args.currentProject.id, projectName,authorName,date)
            //Update current project
            viewModel.updateProject(updatedProject)
            Toast.makeText(requireContext(), R.string.update_success, Toast.LENGTH_LONG).show()
            //Navigate back
//            val action = UpdateFragmentDirections.actionUpdateFragmentToProjectDetailsFragment(args.currentProject)
//            findNavController().navigate(action)
            Navigation.findNavController(requireView()).navigateUp()
        }
        else
        {
            Toast.makeText(requireContext(), R.string.set_fields, Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(projectName: String, authorName: String, date: String) : Boolean{
        return !(TextUtils.isEmpty(projectName) || TextUtils.isEmpty(authorName) || TextUtils.isEmpty(date))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}