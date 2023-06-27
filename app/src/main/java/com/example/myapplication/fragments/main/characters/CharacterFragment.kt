package com.example.myapplication.fragments.main.characters

import android.app.AlertDialog
import android.os.Bundle
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
import com.example.myapplication.databinding.FragmentCharacterBinding
import com.example.myapplication.model.Characters
import com.example.myapplication.viewmodel.DataViewModel


class CharacterFragment : Fragment(), CharactersAdapter.RawClickListener {
    lateinit var viewModel: DataViewModel
    private val Cadapter = CharactersAdapter(this@CharacterFragment)
    private var _binding: FragmentCharacterBinding? = null
    private var projectID: Int = 0
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)

        val rv = binding.CharacterList
        rv.adapter = Cadapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        rv.apply {
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            addItemDecoration(divider)
        }

        binding.include2.customTopBarLayout.title = getString(R.string.characters_header)
        binding.include2.customTopBarLayout.setNavigationIcon(R.drawable.back)

        binding.include2.customTopBarLayout.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        //Get the id of current project
        viewModel.currentProject.observe(viewLifecycleOwner, Observer { project->
            projectID = project.id
        })



        binding.addCharacterButton.setOnClickListener {
            val action = CharacterFragmentDirections.actionCharacterFragmentToAddCharacterFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        viewModel.getCharactersInProject(projectID).observe(viewLifecycleOwner, Observer {
            Cadapter.setData(it)
        })
    }
    override fun deleteCharacter(characters: Characters) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.delete_yes){_, _->
            viewModel.deleteCharacter(characters)
            Toast.makeText(
                requireContext(),
                "${characters.char_name} " + getString(R.string.delete_success),
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.delete_no){_, _->

        }
        builder.setTitle(getString(R.string.delete_confirm) + " ${characters.char_name}?")
        builder.setMessage(getString(R.string.delete_desc) + " ${characters.char_name}?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}